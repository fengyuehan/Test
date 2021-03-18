package com.example.androidplugin;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * author : zhangzf
 * date   : 2021/3/16
 * desc   :
 */
public class HookAMS {
    public static void hookStartActivity(Context context, Class<?> subActivityClass, boolean isAppCompat) {
        if (Build.VERSION.SDK_INT <= 18) {
            HookUtil.hookPackageManager(context, subActivityClass);
        }
        HookUtil.hookStartActivity(context, subActivityClass);
        HookUtil.hookLauncherActivity(context, subActivityClass, isAppCompat);
    }

    public static void resetIActivityManager(Object iActivityManagerObj){
        try {
            Field iActivityManagerSingletonField;
            if (Build.VERSION.SDK_INT >= 29) {
                //1.获取ActivityTaskManager的Class对象
                //package android.app;
                //public class ActivityTaskManager
                Class<?> activityTaskManagerClazz = Class.forName("android.app.ActivityTaskManager");

                //2.获取ActivityTaskManager的私有静态成员变量IActivityTaskManagerSingleton
                // private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton
                iActivityManagerSingletonField = activityTaskManagerClazz.getDeclaredField("IActivityTaskManagerSingleton");

            } else if (Build.VERSION.SDK_INT >= 26) {
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
                iActivityManagerSingletonField = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
                iActivityManagerSingletonField = activityManagerNativeClazz.getDeclaredField("gDefault");
            }
            iActivityManagerSingletonField.setAccessible(true);
            Object iActivityManager = iActivityManagerSingletonField.get(null);
            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            mInstanceField.set(iActivityManager, iActivityManagerObj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取IActivityManager/IActivityTaskManager实例
     *
     * @return IActivityManager/IActivityTaskManager实例
     */
    public static Object getIActivityManager() {
        try {
            Field iActivityManagerSingletonField;
            if (Build.VERSION.SDK_INT >= 29) {
                //1.获取ActivityTaskManager的Class对象
                //package android.app;
                //public class ActivityTaskManager
                Class<?> activityTaskManagerClazz = Class.forName("android.app.ActivityTaskManager");

                //2.获取ActivityTaskManager的私有静态成员变量IActivityTaskManagerSingleton Field
                // private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton
                iActivityManagerSingletonField = activityTaskManagerClazz.getDeclaredField("IActivityTaskManagerSingleton");

            } else if (Build.VERSION.SDK_INT >= 26) {
                //1.获取ActivityManager的Class对象
                //package android.app
                //public class ActivityManager
                Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");

                //2.获取ActivityManager中IActivityManagerSingleton成员变量的IActivityManagerSingleton Field
                //private static final Singleton<IActivityManager> IActivityManagerSingleton
                iActivityManagerSingletonField = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
                iActivityManagerSingletonField = activityManagerNativeClazz.getDeclaredField("gDefault");
            }

            //3.禁止Java语言访问检查
            iActivityManagerSingletonField.setAccessible(true);

            //4.获取IActivityTaskManagerSingleton/IActivityManagerSingleton属性的值
            // 所有静态对象的反射可以通过传null获取。如果是实列必须传实例
            // private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton
            // private static final Singleton<IActivityManager> IActivityManagerSingleton
            Object iActivityManagerSingletonObj = iActivityManagerSingletonField.get(null);

            //5.获取Singleton类的对象
            //package android.util;
            //public abstract class Singleton<T>
            Class<?> singletonClazz = Class.forName("android.util.Singleton");

            //6.获取Singleton中mInstance属性的Field
            // private T mInstance;既 IActivityTaskManager mInstance /IActivityManager mInstance
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");

            //7.禁止Java语言访问检查
            mInstanceField.setAccessible(true);

            //8.获取mInstance属性的的值,既IActivityTaskManager/IActivityManager
            return mInstanceField.get(iActivityManagerSingletonObj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
