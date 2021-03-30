package com.example.androidplugin;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author : zhangzf
 * date   : 2021/3/16
 * desc   :
 */
public class HookPMS {
    public static Object getPackageManager(){
        Object sPackageManage = null;
        try {
            //得到ActivityThread类
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            /**
             * public static ActivityThread currentActivityThread() {
             *     return sCurrentActivityThread;
             * }
             */
            Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //得到sCurrentActivityThread
            Object activityThreadObj = currentActivityThreadMethod.invoke(null);
            //2.获取ActivityThread里面原始的 sPackageManager
            //static IPackageManager sPackageManager;
            Field sPackageManagerField = activityThreadClazz.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            sPackageManage = sPackageManagerField.get(activityThreadObj);

        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return sPackageManage;
    }

    public static void resetPackageManager(Object packageManagerObj){
        Object sPackageManager;
        try {
            //1.获取ActivityThread的值
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");

            //public static ActivityThread currentActivityThread() {return sCurrentActivityThread;}
            Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThreadObj = currentActivityThreadMethod.invoke(null);

            //2.获取ActivityThread里面原始的 sPackageManager
            //static IPackageManager sPackageManager;
            Field sPackageManagerField = activityThreadClazz.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            sPackageManager = sPackageManagerField.get(activityThreadObj);
            sPackageManagerField.set(sPackageManager, packageManagerObj);

            /**
             * 先getPackageManager得到的是宿主的Packmanager.然后调用调用了getPackageInfo方法获取包的信息；
             * 我们宿主包是装上的，所以这样就能欺骗PMS
             */
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

    public static Object getApplicationPackageManager(Context context){
        Object mPM = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            Field mPMField = packageManager.getClass().getDeclaredField("mPM");
            mPMField.setAccessible(true);
            mPM = mPMField.get(packageManager);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }
        return mPM;
    }

    public static void resetApplicatonPackageManager(Context context,Object packageManagerObj){
        try {
            //1.获取 ApplicationPackageManager里面的 mPM对象
            PackageManager packageManager = context.getPackageManager();
            //private final IPackageManager mPM;
            Field mPMField = packageManager.getClass().getDeclaredField("mPM");
            mPMField.setAccessible(true);
            /**
             * 反射中的get()和set()的理解
             *
             * Method setPriceMethod = clz.getMethod("setPrice", int.class);
             * 通过getMethod()获取某个具体的方法。其第一个参数为  方法名，第二个参数为参数的类型  例如：
             * public void setPrice(int price) {
             *      this.price = price;
             * }
             * 然后调用invoke()，setPriceMethod.invoke(appleObj, 14);
             * 其中appleObj为这个方法的实例，而14则是我们需要重新设置的值。
             *
             * public int getPrice() {
             *     return price;
             * }
             *
             * Method getPriceMethod = clz.getMethod("getPrice");
             * getPriceMethod.invoke(appleObj)；
             *
             * getPrice没有参数，所以直接传入方法名即可
             * appleObj为这个方法的实例
             *
             * getXxx()与getDeclareXxx()的区别
             * getXxx()无法获取私有属性。
             * getDeclareXxx()可以获取包括私有属性在内的所有属性。
             */
            mPMField.set(packageManager,packageManagerObj);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }
    }

}
