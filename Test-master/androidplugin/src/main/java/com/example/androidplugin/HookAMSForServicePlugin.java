package com.example.androidplugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author : zhangzf
 * date   : 2021/3/18
 * desc   :
 */
public class HookAMSForServicePlugin {
    public static final String EXTRA_TARGET_INTENT = "service_extra_target_intent";
    public static void hookActivityManager() {
        try {
            Field iActivityManagerSingletonFiled;
            if (Build.VERSION.SDK_INT >= 26) {
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
                iActivityManagerSingletonFiled = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
                iActivityManagerSingletonFiled = activityManagerNativeClazz.getDeclaredField("gDefault");
            }
            iActivityManagerSingletonFiled.setAccessible(true);
            /*if (Build.VERSION.SDK_INT >= 29) {
                //1.获取ActivityTaskManager的Class对象
                //package android.app;
                //public class ActivityTaskManager
                Class<?> activityTaskManagerClazz = Class.forName("android.app.ActivityTaskManager");

                //2.获取ActivityTaskManager的私有静态成员变量IActivityTaskManagerSingleton Field
                // private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton
                iActivityManagerSingletonFiled = activityTaskManagerClazz.getDeclaredField("IActivityTaskManagerSingleton");

            } else if (Build.VERSION.SDK_INT >= 26) {
                //1.获取ActivityManager的Class对象
                //package android.app
                //public class ActivityManager
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");

                //2.获取ActivityManager中IActivityManagerSingleton成员变量的IActivityManagerSingleton Field
                //private static final Singleton<IActivityManager> IActivityManagerSingleton
                iActivityManagerSingletonFiled = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
                iActivityManagerSingletonFiled = activityManagerNativeClazz.getDeclaredField("gDefault");
            }
            iActivityManagerSingletonFiled.setAccessible(true);*/
            Object iActivityManagerSingletonObj = iActivityManagerSingletonFiled.get(null);
            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object rawIActivityManager = mInstanceField.get(iActivityManagerSingletonObj);
            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");
            Object iActivityManagerProxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerClazz},
                    new IActivityManagerHandler(rawIActivityManager));
            mInstanceField.set(iActivityManagerSingletonObj, iActivityManagerProxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static class IActivityManagerHandler implements InvocationHandler {
        private static final String TAG = "IActivityManagerHandler";

        private final Object mIActivityManagerBase;
        public IActivityManagerHandler(Object base) {
            this.mIActivityManagerBase = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("startService".equals(method.getName())){
                // public ComponentName startService(IApplicationThread caller, Intent service,String resolvedType, int userId) throws RemoteException
                Pair<Integer, Intent> pair = findIntentArgs(args);
                Intent proxyIntent = new Intent();
                String proxyPackage = MyApplication.getInstance().getPackageName();
                //可以启动其他应用的Activity、Service.
                //第一个参数为所在的包名，第二个参数为包名+类名
                ComponentName componentName = new ComponentName(proxyPackage,PluginProxyService.class.getName());
                proxyIntent.setComponent(componentName);
                proxyIntent.putExtra(EXTRA_TARGET_INTENT,pair.second);
                args[pair.first] = proxyIntent;
                return method.invoke(mIActivityManagerBase,args);
            }
            // public int stopService(IApplicationThread caller, Intent service,String resolvedType, int userId) throws RemoteException
            if ("stopService".equals(method.getName())) {
                Intent raw = findIntentArgs(args).second;
                if (raw != null && raw.getComponent() != null &&
                        !TextUtils.equals(MyApplication.getInstance().getPackageName(), raw.getComponent().getPackageName())) {
                    // 插件的intent才做hook
                    Log.d(TAG, "hook method stopService success");
                    return ServiceManager.getInstance().stopService(raw);
                }
            }
            return method.invoke(mIActivityManagerBase, args);
        }

        private Pair<Integer, Intent> findIntentArgs(Object[] args) {
            int index = 0;
            for (int i = 0; i < args.length; i++){
                if (args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }
            return Pair.create(index,(Intent)args[index]);
        }
    }
}
