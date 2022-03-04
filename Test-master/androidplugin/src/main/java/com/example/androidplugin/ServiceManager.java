package com.example.androidplugin;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : zhangzf
 * date   : 2021/3/18
 * desc   :
 */
public class ServiceManager {
    private static final String TAG = "ServiceManager";

    /**
     * 动态创建的Service信息,调用ActivityThread#handleCreateService(CreateServiceData data){}方法,创建Service对象
     */
    private final Map<String, Service> mServiceMap = new HashMap<>();

    /**
     * 存储插件的Service信息
     */
    private final Map<ComponentName, ServiceInfo> mServiceInfoMap = new HashMap<>();

    private File mPluginFile;



    private static class Holder {
        private static final ServiceManager instance = new ServiceManager();
    }
    public static ServiceManager getInstance() {
        return Holder.instance;
    }

    public int stopService(Intent targetIntent) {
        ServiceInfo serviceInfo = selectPluginService(targetIntent);
        if (serviceInfo == null) {
            Log.e(TAG, "can not found service: " + targetIntent.getComponent());
            return 0;
        }
        Service service = mServiceMap.get(serviceInfo.name);
        if (service == null) {
            Log.w(TAG, "can not running, are you stopped it multi-times?");
            return 0;
        }
        service.onDestroy();
        mServiceMap.remove(serviceInfo.name);
        if (mServiceMap.isEmpty()) {
            // 没有Service了, 这个没有必要存在了
            Log.d(TAG, "service all stopped, stop proxy");
            Context appContext = MyApplication.getInstance();
            appContext.stopService(new Intent().setComponent(new ComponentName(appContext.getPackageName(), PluginProxyService.class.getName())));
        }
        return 1;
    }

    private ServiceInfo selectPluginService(Intent targetIntent) {
        for (ComponentName componentName : mServiceInfoMap.keySet()) {
            if (componentName.equals(targetIntent.getComponent())) {
                return mServiceInfoMap.get(componentName);
            }
        }
        return null;
    }

    public void onStart(Intent intent, int startId) {
        if (intent == null){
            return;
        }
        Intent targetIntent = intent.getParcelableExtra(HookAMSForServicePlugin.EXTRA_TARGET_INTENT);
        if (targetIntent == null){
            return;
        }
        ServiceInfo serviceInfo = selectPluginService(targetIntent);
        if (serviceInfo == null){
            Log.e(TAG, "can not found service : " + targetIntent.getComponent());
            return;
        }
        try {
            if (!mServiceMap.containsKey(serviceInfo.name)) {
                // service还不存在, 先创建
                proxyCreateService(serviceInfo);
            }
            Service service = mServiceMap.get(serviceInfo.name);
            if (service == null) {
                Log.e(TAG, "service == null, mServiceMap not has");
                return;
            }
            service.onStart(targetIntent, startId);
        }catch (Throwable e){
            Log.e(TAG, "Throwable:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void proxyCreateService(ServiceInfo serviceInfo) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        IBinder token = new Binder();

        //1.创建CreateServiceData对象, 用来传递给ActivityThread的handleCreateService方法当作参数
        //ActivityThread的内部类CreateServiceData static final class CreateServiceData {}

        Class<?> createServiceDataClazz = Class.forName("android.app.ActivityThread$CreateServiceData");
        Constructor<?> createServiceDataConstructor = createServiceDataClazz.getDeclaredConstructor();
        createServiceDataConstructor.setAccessible(true);
        Object createServiceData = createServiceDataConstructor.newInstance();

        /**
         * static final class CreateServiceData {
         *         @UnsupportedAppUsage
         *         IBinder token;
         *         @UnsupportedAppUsage
         *         ServiceInfo info;
         *         @UnsupportedAppUsage
         *         CompatibilityInfo compatInfo;
         *         @UnsupportedAppUsage
         *         Intent intent;
         *         public String toString() {
         *             return "CreateServiceData{token=" + token + " className="
         *             + info.name + " packageName=" + info.packageName
         *             + " intent=" + intent + "}";
         *         }
         *     }
         *
         *     public final void scheduleCreateService(IBinder token,
         *                 ServiceInfo info, CompatibilityInfo compatInfo, int processState) {
         *             updateProcessState(processState, false);
         *             CreateServiceData s = new CreateServiceData();
         *             s.token = token;
         *             s.info = info;
         *             s.compatInfo = compatInfo;
         *
         *             sendMessage(H.CREATE_SERVICE, s);
         *         }
         *
         *     创建Serviced的时候需要CreateServiceData
         */
        //2.给我们创建的createServiceData对象的token字段赋值, ActivityThread的handleCreateService用这个作为key存储Service
        Field tokenField = createServiceDataClazz.getDeclaredField("token");
        tokenField.setAccessible(true);
        tokenField.set(createServiceData, token);

        //3.给serviceInfo.applicationInfo增加必须的属性.
        // 之前android.content.pm.PackageParser#generateServiceInfo(){}方法,得到的ServiceInfo中 serviceInfo.applicationInfo属性没有值;
        //这个修改是为了loadClass的时候, LoadedApk会是主程序的ClassLoader, 我们选择Hook BaseDexClassLoader的方式加载插件

        serviceInfo.applicationInfo.packageName = MyApplication.getInstance().getPackageName();

        //android10出现的异常解决办法,通过异常,寻找出错的地方
        //插件APK的路径
        String path = mPluginFile != null ? mPluginFile.getPath() : "";
        serviceInfo.applicationInfo.sourceDir = path;
        serviceInfo.applicationInfo.publicSourceDir = path;
        serviceInfo.applicationInfo.nativeLibraryDir = MyApplication.getInstance().getApplicationInfo().nativeLibraryDir;

        //4.给我们创建的createServiceData对象的info字段赋值
        Field infoField = createServiceDataClazz.getDeclaredField("info");
        infoField.setAccessible(true);
        infoField.set(createServiceData, serviceInfo);

        //5.给我们创建的createServiceData对象的compatInfo字段赋值
        //5.1获取默认的compatibility配置
        //public class CompatibilityInfo implements Parcelable {
        //    /** default compatibility info object for compatible applications */
        //    public static final CompatibilityInfo DEFAULT_COMPATIBILITY_INFO = new CompatibilityInfo() {
        //    };
        //}
        Class<?> compatibilityClazz = Class.forName("android.content.res.CompatibilityInfo");
        Field defaultCompatibilityField = compatibilityClazz.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultCompatibilityField.setAccessible(true);
        Object defaultCompatibility = defaultCompatibilityField.get(null);

        //5.2赋值
        Field compatInfoField = createServiceDataClazz.getDeclaredField("compatInfo");
        compatInfoField.setAccessible(true);
        compatInfoField.set(createServiceData, defaultCompatibility);

        //6.调用ActivityThread#handleCreateService(CreateServiceData data){}方法
        //6.1 get currentActivityThread
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        //6.2 invoke handleCreateService(CreateServiceData data) {}方法
        // private void handleCreateService(CreateServiceData data) {}
        Method handleCreateServiceMethod = activityThreadClazz.getDeclaredMethod("handleCreateService", createServiceDataClazz);
        handleCreateServiceMethod.setAccessible(true);
        handleCreateServiceMethod.invoke(currentActivityThread, createServiceData);

        //7.获取存储在ActivityThread的mServices字段里的值
        // handleCreateService方法创建出来的Service对象并没有返回, 而是存储在ActivityThread的mServices字段里面, 这里我们手动把它取出来
        //final ArrayMap<IBinder, Service> mServices = new ArrayMap<>();
        Field mServicesField = activityThreadClazz.getDeclaredField("mServices");
        mServicesField.setAccessible(true);
        Map<?, ?> mServices = (Map<?, ?>) mServicesField.get(currentActivityThread);
        if (mServices == null) throw new NullPointerException("mServices==null");

        //8.获取我们新创建出来的Service对象
        Service service = (Service) mServices.get(token);

        //9.获取到之后, 移除这个service, 我们只是借花献佛。
        //使用系统的类,来解析.用完后,清除使用过程中产生的额外产物;清理干净;
        mServices.remove(token);

        //10.将此Service存储起来
        mServiceMap.put(serviceInfo.name, service);
    }

    /**
     * 解析Apk文件中的 <service>, 并存储起来
     * 主要是调用PackageParser类的generateServiceInfo方法
     *
     * @param apkFile 插件对应的apk文件
     * @throws Throwable 解析出错或者反射调用出错, 均会抛出异常
     */
    public void preLoadServices(File apkFile) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, InvocationTargetException, NoSuchFieldException {
        mPluginFile = apkFile;
        int version = Build.VERSION.SDK_INT;
        if (version < 15) return;
        //1.获取PackageParser的Class对象
        //package android.content.pm
        //public class PackageParser
        Class<?> packageParserClazz = Class.forName("android.content.pm.PackageParser");

        //2.获取parsePackage()方法的Method
        Method parsePackageMethod;
        if (Build.VERSION.SDK_INT >= 20) {
            //public Package parsePackage(File packageFile, int flags) throws PackageParserException {}//api-29
            parsePackageMethod = packageParserClazz.getDeclaredMethod("parsePackage", File.class, int.class);
        } else {
            // 15<=Build.VERSION.SDK_INT <=19
            //public Package parsePackage(File sourceFile, String destCodePath, DisplayMetrics metrics, int flags) {}//api-19
            parsePackageMethod = packageParserClazz.getDeclaredMethod("parsePackage", File.class, String.class, DisplayMetrics.class, int.class);
        }
        parsePackageMethod.setAccessible(true);

        //3.生成PackageParser对象实例
        Object packageParser;
        if (Build.VERSION.SDK_INT >= 20) {
            packageParser = packageParserClazz.newInstance();
        } else {
            // 15<=Build.VERSION.SDK_INT <=19
            Constructor<?> packageParserConstructor = packageParserClazz.getConstructor(String.class);
            packageParserConstructor.setAccessible(true);
            String archiveSourcePath = apkFile.getCanonicalPath();
            packageParser = packageParserConstructor.newInstance(archiveSourcePath);
        }

        //4.调用parsePackage获取到apk对象对应的Package对象(return information about intent receivers in the package)
        //Package为PackageParser的内部类;public final static class Package implements Parcelable {}
        Object packageObj;
        if (Build.VERSION.SDK_INT >= 20) {
            //public Package parsePackage(File packageFile, int flags) throws PackageParserException {}//api-29
            //public Package parsePackage(File packageFile, int flags) throws PackageParserException {}//api-21
            packageObj = parsePackageMethod.invoke(packageParser, apkFile, PackageManager.GET_SERVICES);
        } else {
            // 15<=Build.VERSION.SDK_INT <=19
            String destCodePath = apkFile.getCanonicalPath();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();

            //public Package parsePackage(File sourceFile, String destCodePath, DisplayMetrics metrics, int flags) {}//api-19
            //public Package parsePackage(File sourceFile, String destCodePath, DisplayMetrics metrics, int flags) {}//api-15
            packageObj = parsePackageMethod.invoke(packageParser, apkFile, destCodePath, displayMetrics, PackageManager.GET_SERVICES);
        }

        if (packageObj == null) return;

        //5.读取Package对象里面的services字段
        //public final ArrayList<Service> services = new ArrayList<Service>(0);
        Class<?> packageParser$PackageClazz = Class.forName("android.content.pm.PackageParser$Package");
        Field servicesField = packageParser$PackageClazz.getDeclaredField("services");
        servicesField.setAccessible(true);

        //6.get services
        List<?> services = (List<?>) servicesField.get(packageObj);
        if (services == null) {
            Log.e(TAG, "services == null");
            return;
        }

        //7.接下来要做的就是根据这个List<Service> 获取到Service对应的ServiceInfo
        // 调用generateServiceInfo 方法, 把PackageParser$Service转换成ServiceInfo
        //public final static class Service extends Component<ServiceIntentInfo> {}
        Class<?> packageParser$ServiceClazz = Class.forName("android.content.pm.PackageParser$Service");

        Method generateServiceInfo;
        if (Build.VERSION.SDK_INT >= 17) {
            // get defaultUserState
            Class<?> packageUserStateClazz = Class.forName("android.content.pm.PackageUserState");
            Object defaultUserState = packageUserStateClazz.newInstance();

            // get userId
            Class<?> userHandlerClazz = Class.forName("android.os.UserHandle");
            //public static final int getCallingUserId() {}
            Method getCallingUserIdMethod = userHandlerClazz.getDeclaredMethod("getCallingUserId");
            getCallingUserIdMethod.setAccessible(true);
            Object userIdObj = getCallingUserIdMethod.invoke(null);
            if (!(userIdObj instanceof Integer)) return;
            int userId = (Integer) userIdObj;

            //8. call generateServiceInfo方法
            // public static final ServiceInfo generateServiceInfo(android.content.pm.PackageParser.Service s, int flags, android.content.pm.PackageUserState state, int userId) {
            generateServiceInfo = packageParserClazz.getDeclaredMethod("generateServiceInfo", packageParser$ServiceClazz, int.class, packageUserStateClazz, int.class);
            generateServiceInfo.setAccessible(true);

            //9. 解析出intent对应的Service组件
            for (Object service : services) {
                ServiceInfo info = (ServiceInfo) generateServiceInfo.invoke(packageParser, service, 0, defaultUserState, userId);
                if (info == null) continue;
                mServiceInfoMap.put(new ComponentName(info.packageName, info.name), info);
            }
        } else if (Build.VERSION.SDK_INT == 16) {
            Class<?> userIdClazz = Class.forName("android.os.UserId");
            // public static final int getCallingUserId(){}
            Method getCallingUserIdMethod = userIdClazz.getDeclaredMethod("getCallingUserId");
            getCallingUserIdMethod.setAccessible(true);

            Object userIdObj = getCallingUserIdMethod.invoke(null);
            if (!(userIdObj instanceof Integer)) return;
            int userId = (Integer) userIdObj;

            //public static final ServiceInfo generateServiceInfo(Service s, int flags, boolean stopped,int enabledState, int userId) {}//api=16
            generateServiceInfo = packageParserClazz.getDeclaredMethod("generateServiceInfo", packageParser$ServiceClazz, int.class, boolean.class, int.class, int.class);
            generateServiceInfo.setAccessible(true);

            for (Object service : services) {
                int enabledState = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
                ServiceInfo info = (ServiceInfo) generateServiceInfo.invoke(packageParser, service, 0, false, enabledState, userId);
                if (info == null) continue;
                mServiceInfoMap.put(new ComponentName(info.packageName, info.name), info);
            }
        } else {
            //public static final ServiceInfo generateServiceInfo(Service s, int flags){}//api=15
            generateServiceInfo = packageParserClazz.getDeclaredMethod("generateServiceInfo", packageParser$ServiceClazz, int.class);
            generateServiceInfo.setAccessible(true);

            for (Object service : services) {
                ServiceInfo info = (ServiceInfo) generateServiceInfo.invoke(packageParser, service, 0);
                if (info == null) continue;
                mServiceInfoMap.put(new ComponentName(info.packageName, info.name), info);
            }
        }
    }
}
