package com.example.androidplugin;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2021/3/16
 * desc   :
 */
public class HookUtil {
    private static final String EXTRA_ORIGIN_INTENT = "EXTRA_ORIGIN_INTENT";
    @SuppressLint("StaticFieldLeak")
    private static IActivityInvocationHandler mIActivityInvocationHandler;
    @SuppressLint("StaticFieldLeak")
    private static IActivityInvocationHandler mIActivityInvocationHandlerL;
    @SuppressLint("StaticFieldLeak")
    private static PackageManagerProxyHandler mPackageManagerProxyHandler;

    /**
     * 1.处理未注册的Activity为AppCompatActivity类或者子类的情况
     * 2.hook IPackageManager,处理android 4.3以下(<= 18)启动Activity,在ApplicationPackageManager.getActivityInfo方法中未找到注册的Activity的异常
     * @param context
     * @param subActivityClazz
     */
    public static void hookPackageManager(Context context,Class<?> subActivityClazz){
        try {
            //1.获取ActivityThread的值
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThread = currentActivityThreadMethod.invoke(null);
            //2.获取ActivityThread里面原始的 sPackageManager
            //static IPackageManager sPackageManager;
            Field sPackageManagerField = activityThreadClazz.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(activityThread);
            if (mPackageManagerProxyHandler == null){
                mPackageManagerProxyHandler = new PackageManagerProxyHandler(sPackageManager,getAppPackageName(context),subActivityClazz.getName());
            }
            Class<?> iPackageManagerClazz = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{iPackageManagerClazz},mPackageManagerProxyHandler);
            //4.替换掉ActivityThread里面的 sPackageManager 字段
            sPackageManagerField.set(activityThread,proxy);
            //5.替换 ApplicationPackageManager里面的 mPM对象
            PackageManager packageManager = context.getPackageManager();
            Field mPmField = packageManager.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(packageManager,proxy);
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取包名
     * @param context
     * @return
     */
    private static String getAppPackageName(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext.getPackageName();
    }

    /**
     * 对IActivityManager接口中的startActivity方法进行动态代理,发生在app的进程中
     * {@link android.app.Activity#startActivity(Intent)}
     * {@link android.app.Activity#startActivityForResult(Intent, int, Bundle)}
     * android.app.Instrumentation#execStartActivity()
     * Activity#startActivityForResult-->Instrumentation#execStartActivity-->ActivityManager.getService().startActivity()-->
     * IActivityManager public int startActivity(android.app.IApplicationThread caller, java.lang.String callingPackage, android.content.Intent intent, java.lang.String resolvedType, android.os.IBinder resultTo, java.lang.String resultWho, int requestCode, int flags, android.app.ProfilerInfo profilerInfo, android.os.Bundle options) throws android.os.RemoteException;
     *
     * @param context          context
     * @param subActivityClass 在AndroidManifest.xml中注册了的Activity
     */
    public static void hookStartActivity(Context context, Class<?> subActivityClass) {
        try {
            if (Build.VERSION.SDK_INT >= 29){
                //1.获取ActivityTaskManager的Class对象
                //package android.app;
                //public class ActivityTaskManager
                @SuppressLint("PrivateApi") Class<?> activityTaskManagerClazz = Class.forName("android.app.ActivityTaskManager");
                //2.获取ActivityTaskManager的私有静态成员变量IActivityTaskManagerSingleton
                // private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton
                Field iActivityTaskManagerSingletonField = activityTaskManagerClazz.getDeclaredField("IActivityTaskManagerSingleton");
                iActivityTaskManagerSingletonField.setAccessible(true);
                //获取实例
                Object IActivityTaskManagerSingletonObj = iActivityTaskManagerSingletonField.get(null);
                handleIActivityTaskManager(context,subActivityClass,IActivityTaskManagerSingletonObj);
            }else if (Build.VERSION.SDK_INT > 26){
                //1.获取ActivityManager的Class对象
                //package android.app
                //public class ActivityManager
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
                //2.获取ActivityManager的私有静态属性IActivityManagerSingleton
                //private static final Singleton<IActivityManager> IActivityManagerSingleton
                Field iActivityManagerSingletonField = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
                //3.取消Java的权限检查
                iActivityManagerSingletonField.setAccessible(true);

                //4.获取IActivityManagerSingleton的实例对象
                //private static final Singleton<IActivityManager> IActivityManagerSingleton
                //所有静态对象的反射可以通过传null获取,如果是非静态必须传实例
                handleIActivityManager(context, subActivityClass, iActivityManagerSingletonField.get(null));
            }else {
                //1.获取ActivityManagerNative的Class对象
                //package android.app
                //public abstract class ActivityManagerNative
                @SuppressLint("PrivateApi") Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");

                //2.获取 ActivityManagerNative的 私有属性gDefault
                // private static final Singleton<IActivityManager> gDefault
                Field singletonField = activityManagerNativeClazz.getDeclaredField("gDefault");

                //3.对私有属性gDefault,解除私有限定
                singletonField.setAccessible(true);

                //4.获得gDefaultField中对应的属性值(被static修饰了),既得到Singleton<IActivityManager>对象的实例
                //所有静态对象的反射可以通过传null获取
                //private static final Singleton<IActivityManager> gDefault
                handleIActivityManager(context, subActivityClass, singletonField.get(null));
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void handleIActivityManager(Context context, Class<?> subActivityClass, Object IActivityManagerSingletonObj) {
        try {
            //5.获取private static final Singleton<IActivityManager> IActivityManagerSingleton对象中的属性private T mInstance的值
            //既,为了获取一个IActivityManager的实例对象
            //private static final Singleton<IActivityManager> IActivityManagerSingleton =new Singleton<IActivityManager>(){...}


            //6.获取Singleton类对象
            //package android.util
            //public abstract class Singleton<T>
            Class<?> singletonClazz = Class.forName("android.util.Singleton");

            //7.获取mInstance属性
            //private T mInstance;
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");

            //8.取消Java的权限检查
            mInstanceField.setAccessible(true);

            //9.获取mInstance属性的值,既IActivityManager的实例
            //从private static final Singleton<IActivityManager> IActivityManagerSingleton实例对象中获取mInstance属性对应的值,既IActivityManager
            Object iActivityManager = mInstanceField.get(IActivityManagerSingletonObj);


            //10.获取IActivityManager接口的类对象
            //package android.app
            //public interface IActivityManager
            Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");

            if (mIActivityInvocationHandlerL == null) {
                mIActivityInvocationHandlerL = new IActivityInvocationHandler(iActivityManager, context, subActivityClass);
            } else {
                mIActivityInvocationHandlerL.updateStubActivity(subActivityClass);
            }

            //11.创建一个IActivityManager接口的代理对象
            Object iActivityManagerProxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClazz},
                    mIActivityInvocationHandlerL
            );

            //11.重新赋值
            //给mInstance属性,赋新值
            //给Singleton<IActivityManager> IActivityManagerSingleton实例对象的属性private T mInstance赋新值
            mInstanceField.set(IActivityManagerSingletonObj, iActivityManagerProxy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void handleIActivityTaskManager(Context context, Class<?> subActivityClass, Object iActivityTaskManagerSingletonObj) {
        //5.获取private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton对象中的属性private T mInstance的值
        //既,为了获取一个IActivityTaskManager的实例对象
        //private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton = new Singleton<IActivityTaskManager>() {...}

        try {
            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            //获取SingleTon类的实例
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
            //8.取消Java的权限检查
            mInstanceField.setAccessible(true);
            //9.获取mInstance属性的值,既IActivityTaskManager的实例
            //从private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton实例对象中获取mInstance属性对应的值,既IActivityTaskManager
            Object IActivityTaskManager = mInstanceField.get(iActivityTaskManagerSingletonObj);

            //10.获取IActivityTaskManager接口的类对象
            //package android.app
            //public interface IActivityTaskManager
            Class<?> IActivityTaskManagerClazz = Class.forName("android.app.IActivityTaskManager");
            if (mIActivityInvocationHandler == null){
                mIActivityInvocationHandler = new IActivityInvocationHandler(IActivityTaskManager,context,subActivityClass);
            }else {
                mIActivityInvocationHandler.updateStubActivity(subActivityClass);
            }
            //11.创建一个IActivityTaskManager接口的代理对象
            Object IActivityTaskManagerProxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{IActivityTaskManagerClazz},
                    mIActivityInvocationHandler
            );
            //11.重新赋值
            //给mInstance属性,赋新值
            //给Singleton<IActivityManager> IActivityManagerSingleton实例对象的属性private T mInstance赋新值
            mInstanceField.set(iActivityTaskManagerSingletonObj, IActivityTaskManagerProxy);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动未注册的Activity,将之前替换了的Intent,换回去.我们的目标是要启动未注册的Activity
     *
     * @param context          context
     * @param subActivityClass 注册了的Activity的Class对象
     * @param isAppCompat      是否是AppCompatActivity的子类
     */
    public static void hookLauncherActivity(Context context, Class<?> subActivityClass, boolean isAppCompat) {
        try {
            //1.获取ActivityThread的Class对象
            //package android.app
            //public final class ActivityThread{}
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            //2.获取currentActivityThread()静态方法;为了保证在多个版本中兼容性,使用该静态方法获取ActivityThread的实例
            //public static ActivityThread currentActivityThread(){return sCurrentActivityThread;}
            Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //3.获取ActivityThread的对象实例
            //public static ActivityThread currentActivityThread(){return sCurrentActivityThread;}
            Object activityThreadObj = currentActivityThreadMethod.invoke(null);
            //4.获取ActivityThread 的属性mH
            //final H mH = new H();
            Field mHField = activityThreadClazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            //5.获取mH的值,既获取ActivityThread类中H类的实例对象
            //从ActivityThread实例中获取mH属性对应的值,既mH的值
            Object mHObj = mHField.get(activityThreadObj);
            //6.获取Handler的Class对象
            //package android.os
            //public class Handler
            Class<?> handlerClazz = Class.forName("android.os.Handler");
            //7.获取mCallback属性
            //final Callback mCallback;
            //Callback是Handler类内部的一个接口
            Field mCallbackField = handlerClazz.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            //8.给mH增加mCallback
            //给mH,既Handler的子类设置mCallback属性,提前对消息进行处理.
            if (Build.VERSION.SDK_INT >= 28) {
                //android 9.0
                mCallbackField.set(mHObj, new HandlerCallbackP(context, subActivityClass, isAppCompat));
            } else {
                mCallbackField.set(mHObj, new HandlerCallback(context, subActivityClass, isAppCompat));
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class PackageManagerProxyHandler implements InvocationHandler{
        private final String mSubActivityClazzName;
        private final Object mIPackageManagerObj;
        private final String mAppPackageName;

        public PackageManagerProxyHandler(Object iPackageManagerObj, String appPackageName, String subActivityClazzName) {
            this.mIPackageManagerObj = iPackageManagerObj;
            this.mSubActivityClazzName = subActivityClazzName;
            this.mAppPackageName = appPackageName;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName className, int flags, int userId)
            if ("getActivityInfo".equals(method.getName())) {
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof ComponentName) {
                        index = i;
                        break;
                    }
                }
                ComponentName componentName = new ComponentName(mAppPackageName, mSubActivityClazzName);
                args[index] = componentName;
            }
            if ("getPackageInfo".equals(method.getName())) {
                Log.d("getPackageInfo", "getPackageInfo:call");
                return new PackageInfo();
            }
            return method.invoke(mIPackageManagerObj, args);
        }
    }

    private static class HandlerCallback implements Handler.Callback{
        private final Context context;
        private final Class<?> subActivityClazz;
        private final boolean isAppCompat;

        public HandlerCallback(Context context, Class<?> subActivityClazz, boolean isAppCompat) {
            this.context = context;
            this.subActivityClazz = subActivityClazz;
            this.isAppCompat = isAppCompat;
        }
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            handleLaunchActivity(msg, context, subActivityClazz, isAppCompat);
            return false;
        }

        private void handleLaunchActivity(Message msg, Context context, Class<?> subActivityClazz, boolean isAppCompat) {
            int LAUNCH_ACTIVITY = 100;
            try {
                //1.获取ActivityThread的内部类H的Class对象
                //package android.app
                //public final class ActivityThread{
                //       private class H extends Handler {}
                //}
                Class<?> hClazz = Class.forName("android.app.ActivityThread$H");

                //2.获取LAUNCH_ACTIVITY属性的Field
                // public static final int LAUNCH_ACTIVITY = 100;
                Field launch_activity_field = hClazz.getField("LAUNCH_ACTIVITY");

                //3.获取LAUNCH_ACTIVITY的值
                Object object = launch_activity_field.get(null);
                if (object instanceof Integer) {
                    LAUNCH_ACTIVITY = (int) object;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (msg.what != LAUNCH_ACTIVITY) return;
            // private class H extends Handler {
            // public void handleMessage(Message msg) {
            //            switch (msg.what) {
            //                case LAUNCH_ACTIVITY: {
            //                    final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
            //                    r.packageInfo = getPackageInfoNoCheck(r.activityInfo.applicationInfo, r.compatInfo);
            //                    handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");
            //                    break;
            //                }
            //            }
            //    }
            //1.从msg中获取ActivityClientRecord对象
            //android.app.ActivityThread$ActivityClientRecord
            //static final class ActivityClientRecord {}
            Object activityClientRecordObj = msg.obj;

            try {
                //2.获取ActivityClientRecord的intent属性
                // Intent intent;
                Field safeIntentField = activityClientRecordObj.getClass().getDeclaredField("intent");
                safeIntentField.setAccessible(true);

                //3.获取ActivityClientRecord的intent属性的值,既安全的Intent
                Intent safeIntent = (Intent) safeIntentField.get(activityClientRecordObj);
                if (safeIntent == null) return;

                //4.获取原始的Intent
                Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTENT);

                if (originIntent == null) return;

                //5.将安全的Intent,替换为原始的Intent,以启动我们要启动的未注册的Activity
                safeIntent.setComponent(originIntent.getComponent());

                //6.处理启动的Activity为AppCompatActivity类或者子类的情况
                if (!isAppCompat) {
                    return;
                }
                hookPackageManager(context, subActivityClazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static class HandlerCallbackP implements Handler.Callback{

        private final Context context;
        private final Class<?> subActivityClazz;
        private final boolean isAppCompat;

        public HandlerCallbackP(Context context, Class<?> subActivityClazz, boolean isAppCompat) {
            this.context = context;
            this.subActivityClazz = subActivityClazz;
            this.isAppCompat = isAppCompat;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            //android.app.ActivityThread$H.EXECUTE_TRANSACTION = 159
            //android 9.0反射,Accessing hidden field Landroid/app/ActivityThread$H;->EXECUTE_TRANSACTION:I (dark greylist, reflection)
            //android9.0 深灰名单（dark greylist）则debug版本在会弹出dialog提示框，在release版本会有Toast提示，均提示为"Detected problems with API compatibility"
            if (msg.what == 159){
                handleActivity(msg);
            }
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void handleActivity(Message msg) {
            /**
             * final ClientTransaction transaction = (ClientTransaction) msg.obj;
             * mTransactionExecutor.execute(transaction);
             *
             * 在API 30 startActivity在H里面进行了改变
             * ClientTransaction-->ClientTransaction中的List<ClientTransactionItem> mActivityCallbacks-->集合中的第一个值LaunchActivityItem-->LaunchActivityItem的mIntent
             */

            try {
                //1.获取ClientTransaction对象
                Object clientTransactionObj = msg.obj;
                if (clientTransactionObj == null){
                    return;
                }
                //2.获取ClientTransaction类中属性mActivityCallbacks的Field
                //private List<ClientTransactionItem> mActivityCallbacks;
                Field mActivityCallbackField = clientTransactionObj.getClass().getDeclaredField("mActivityCallbacks");
                mActivityCallbackField.setAccessible(true);
                List<?> mActivityCallbacks = (List<?>) mActivityCallbackField.get(clientTransactionObj);

                if (mActivityCallbacks == null || mActivityCallbacks.size() <=0){
                    return;
                }
                if (mActivityCallbacks.get(0) == null){
                    return;
                }
                //5.ClientTransactionItem的Class对象
                //package android.app.servertransaction;
                //public class LaunchActivityItem extends ClientTransactionItem
                @SuppressLint("PrivateApi") Class<?> launchActivityItemClazz = Class.forName("android.app.servertransaction.LaunchActivityItem");
                //6.判断集合中第一个元素的值是LaunchActivityItem类型的
                if (!launchActivityItemClazz.isInstance(mActivityCallbacks.get(0))){
                    return;
                }
                Object launchActivityItem = mActivityCallbacks.get(0);
                //8.ClientTransactionItem的mIntent属性的mIntent的Field
                //private Intent mIntent;
                Field mIntentField = launchActivityItemClazz.getDeclaredField("mIntent");
                mIntentField.setAccessible(true);
                //10.获取mIntent属性的值,既桩Intent(安全的Intent)
                //从LaunchActivityItem中获取属性mIntent的值
                Intent safeIntent = (Intent) mIntentField.get(launchActivityItem);
                if (safeIntent == null){
                    return;
                }
                Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTENT);
                if (originIntent == null){
                    return;
                }
                safeIntent.setComponent(originIntent.getComponent());
                if (!isAppCompat){
                    return;
                }
                hookPackageManager(context,subActivityClazz);

            } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static class IActivityInvocationHandler implements InvocationHandler{

        private final Object mIActivityManager;
        private Class<?> mSubActivityClazz;
        private final Context mContext;

        public IActivityInvocationHandler(Object iActivityManager, Context context, Class<?> subActivityClazz) {
            this.mIActivityManager = iActivityManager;
            this.mSubActivityClazz = subActivityClazz;
            this.mContext = context;
        }

        public void updateStubActivity(Class<?> subActivityClazz) {
            this.mSubActivityClazz = subActivityClazz;
        }

        /**
         * Activity#startActivityForResult-->Instrumentation#execStartActivity-->ActivityManager.getService().startActivity()-->
         * IActivityManager public int startActivity(android.app.IApplicationThread caller, java.lang.String callingPackage, android.content.Intent intent, java.lang.String resolvedType, android.os.IBinder resultTo, java.lang.String resultWho, int requestCode, int flags, android.app.ProfilerInfo profilerInfo, android.os.Bundle options) throws android.os.RemoteException;
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("startActivity")){
                int intentIndex = 0;
                for (int i = 0; i< args.length;i++){
                    if (args[i] instanceof Intent){
                        intentIndex = i;
                        break;
                    }
                }
                //将启动的未注册的Activity对应的Intent,替换为安全的注册了的桩Activity的Intent
                //1.将未注册的Activity对应的Intent,改为安全的Intent,既在AndroidManifest.xml中配置了的Activity的Intent
                Intent originIntent = (Intent) args[intentIndex];
                Intent safeIntent = new Intent(mContext,mSubActivityClazz);
                safeIntent.putExtra(EXTRA_ORIGIN_INTENT,originIntent);
                //2、替换为原来的Intent
                args[intentIndex] = safeIntent;
                //3.之后,再换回来,启动我们未在AndroidManifest.xml中配置的Activity
                //final H mH = new H();
                //hook Handler消息的处理,给Handler增加mCallback
            }

            return method.invoke(mIActivityManager, args);
        }
    }
}
