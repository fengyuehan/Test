package com.example.lettersidebar;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 常用工具类
 */
public class CommonUtil {
    private static int mScreenWidth = -1;
    private static int mScreenHeight = -1;
    private static int mScreenDpi = -1;
    private static float mScreenDensity = -1;
    private static int mStatus_bar_height = -1;

    private static String mVersionName;
    private static int mVersionCode = -1;
    private static String mDeviceId;
    private static int mNavigationBarHeight = -1;


    /**
     * 将字符串转成MD5值
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        if (mScreenWidth == -1) {
            DisplayMetrics displayMetrics = AppApplication.getInstance().getResources().getDisplayMetrics();
            mScreenWidth = displayMetrics.widthPixels;
            mScreenHeight = displayMetrics.heightPixels;
            mScreenDensity = displayMetrics.density;
            mScreenDpi = displayMetrics.densityDpi;
        }
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        if (mScreenWidth == -1) {
            DisplayMetrics displayMetrics = AppApplication.getInstance().getResources().getDisplayMetrics();
            mScreenWidth = displayMetrics.widthPixels;
            mScreenHeight = displayMetrics.heightPixels;
            mScreenDensity = displayMetrics.density;
            mScreenDpi = displayMetrics.densityDpi;
        }
        return mScreenHeight;
    }

    /**
     * 获取屏幕的density
     */
    public static float getScreenDensity() {
        if (mScreenWidth == -1) {
            DisplayMetrics displayMetrics = AppApplication.getInstance().getResources().getDisplayMetrics();
            mScreenWidth = displayMetrics.widthPixels;
            mScreenHeight = displayMetrics.heightPixels;
            mScreenDensity = displayMetrics.density;
            mScreenDpi = displayMetrics.densityDpi;
        }
        return mScreenDensity;
    }

    /**
     * 获取屏幕的densityDpi
     */
    public static int getScreenDpi() {
        if (mScreenWidth == -1) {
            DisplayMetrics displayMetrics = AppApplication.getInstance().getResources().getDisplayMetrics();
            mScreenWidth = displayMetrics.widthPixels;
            mScreenHeight = displayMetrics.heightPixels;
            mScreenDensity = displayMetrics.density;
            mScreenDpi = displayMetrics.densityDpi;
        }
        return mScreenDpi;
    }

    /**
     * 获取应用的版本名称
     */
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(mVersionName)) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo e = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
                mVersionName = e.versionName;
                mVersionCode = e.versionCode;
            } catch (PackageManager.NameNotFoundException var5) {
                var5.printStackTrace();
            }
        }
        return mVersionName;
    }

    /**
     * 获取应用的版本号
     */
    public static int getVersionCode(Context context) {
        if (mVersionCode <= 0) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo e = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
                mVersionName = e.versionName;
                mVersionCode = e.versionCode;
            } catch (PackageManager.NameNotFoundException var5) {
                var5.printStackTrace();
            }
        }

        return mVersionCode;
    }


    /**
     * 获取sdk version信息
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            //Logger.t(Constants.USER_TAG).e(e.getMessage());
        }
        return version;
    }

    /**
     * 获取手机UUID
     */
    public static String getUUID(Context context) {
        if (TextUtils.isEmpty(mDeviceId)) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                mDeviceId = getUuid(context);
            } else {
                mDeviceId = getAppId();
            }
        }
        return mDeviceId;
    }


    private static String getAppId() {
        if (TextUtils.isEmpty(mDeviceId)) {
            mDeviceId = UUID.randomUUID().toString();
        }
        return mDeviceId;
    }


    /**
     * 获取机器唯一标志码 ,需要权限 READPHONESTATE
     */
    public static String getUuid(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return mDeviceId = "" + tm.getDeviceId();
    }

    /**
     * 使用java中的ProcessBuilder执行本地命令或脚本等工作
     */
    public static String exec(String[] args) {
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inIs != null) {
                try {
                    inIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    /**
     * 获取memory信息
     */
    public static String getMemoryInfo() {
        StringBuffer sb = new StringBuffer();
        Runtime rt = Runtime.getRuntime();
        sb.append("Max:").append(rt.maxMemory());
        sb.append("|Free:").append(rt.freeMemory());
        sb.append("|Total:").append(rt.totalMemory());

        return sb.toString();
    }

    /**
     * int类型转换成byte[]
     */
    public static byte[] intToBytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    /**
     * byte[]转换成int数
     *
     * @param data   包括int的byte[]
     * @param offset 偏移量
     * @return int数
     */
    public static int bytesToInt(byte[] data, int offset) {
        int num = 0;
        for (int i = offset; i < offset + 4; i++) {
            num <<= 8;
            num |= (data[i] & 0xff);
        }
        return num;
    }

    /**
     * 将字符串类型ip地址转换成int
     */
    public static int ipToInt(String ipAddress) {
        if (ipAddress == null) {
            return 0;
        }
        try {
            long[] ip = new long[4];
            // 先找到IP地址字符串中.的位置
            int position1 = ipAddress.indexOf(".");
            int position2 = ipAddress.indexOf(".", position1 + 1);
            int position3 = ipAddress.indexOf(".", position2 + 1);
            // 将每个.之间的字符串转换成整型
            ip[0] = Long.parseLong(ipAddress.substring(0, position1));
            ip[1] = Long.parseLong(ipAddress.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(ipAddress.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(ipAddress.substring(position3 + 1));
            return (int) ((ip[3] << 24) + (ip[2] << 16) + (ip[1] << 8) + ip[0]);
        } catch (Throwable e) {
            return 0;
        }
    }


    private static DecimalFormat percentFormat = new DecimalFormat("##0%");

    /**
     * 格式化成百分比
     *
     * @param value
     */
    public static String getPercentFormater(double value) {
        String returnStr = percentFormat.format(value);
        return returnStr;
    }

    /**
     * 打开一个app
     */
    public static boolean launchApp(String packageName, Bundle data) {
        try {
            Intent resolveIntent = AppApplication.getInstance().getPackageManager().getLaunchIntentForPackage(packageName);
            if (resolveIntent == null) {
                PackageInfo p = AppApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                if (p != null) {
                    resolveIntent = new Intent(packageName);
                }
            }
            if (resolveIntent != null) {
                if (data != null) {
                    resolveIntent.putExtras(data);
                }
                resolveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppApplication.getInstance().startActivity(resolveIntent);
                return true;
            }
        } catch (Exception e) {
            //Logger.t(Constants.USER_TAG).e(e.getMessage());
        }
        return false;
    }

    /**
     * 打开一个app
     */
    public static void launchApp(Context context, String packageName, String activityName, Bundle data) {
        ComponentName componentName = new ComponentName(packageName, activityName);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(data);
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "未找到App", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 重启app
     */
    public static void restartApp() {
        Intent intent = AppApplication.getInstance().getPackageManager().getLaunchIntentForPackage(AppApplication.getInstance().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(AppApplication.getInstance(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) AppApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 退出并清除全部任务栈
     * 只适合5.0以上
     */
    public static void finishAndRemoveAllTasks(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return;
        }
        try {
            List<ActivityManager.AppTask> appTasks = am.getAppTasks();
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        } catch (SecurityException e) {
            //Logger.t(Constants.USER_TAG).e("退出并清除全部任务栈出错");
        }
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static float dp2pxFloat(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int pxTodp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 打电话
     */
    public static void dial(Activity activity, String phone) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(dialIntent);
    }

    public static void call(Activity activity, String phone) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        activity.startActivity(dialIntent);

    }

    public static void copyAnchor(String contentStr) {
        ClipboardManager clipBoard = (ClipboardManager) AppApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        clipBoard.setText(contentStr);
    }


    /**
     * 获取手机屏幕底部导航栏高度
     */
    public static int getNavigationBarHeight() {
        if (mNavigationBarHeight == -1) {
            Resources resources = AppApplication.getInstance().getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            mNavigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return mNavigationBarHeight;
    }

    /**
     * 反射获得状态栏的高度
     */
    public static int getStatusHeight() {
        if (mStatus_bar_height != -1) {
            return mStatus_bar_height;
        }
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("mStatus_bar_height").get(object).toString());
            statusHeight = AppApplication.getInstance().getResources().getDimensionPixelSize(height);
            mStatus_bar_height = CommonUtil.pxTodp(AppApplication.getInstance(), statusHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
     *
     * @return 返回全部为全角字符的字符串
     */
    public static String toDBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 动态替换手机桌面图标相关
     */
    public static boolean isComponentEnable(String componentName) {
        int isEnabled = AppApplication.getInstance().getPackageManager().getComponentEnabledSetting(new ComponentName(AppApplication.getInstance().getPackageName(), componentName));
        return isEnabled == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    /**
     * 动态替换手机桌面图标相关
     */
    public static int getComponentState(String componentName) {
        return AppApplication.getInstance().getPackageManager().getComponentEnabledSetting(new ComponentName(AppApplication.getInstance().getPackageName(), componentName));
    }

    /**
     * 动态替换手机桌面图标相关
     */
    public static void enableComponent(String componentName) {
        //此方法用以启用和禁用组件，会覆盖Androidmanifest文件下定义的属性
        AppApplication.getInstance().getPackageManager().setComponentEnabledSetting(new ComponentName(AppApplication.getInstance().getPackageName(), componentName),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 动态替换手机桌面图标相关
     * 禁用组件
     */
    public static void disableComponent(String componentName) {
        AppApplication.getInstance().getPackageManager().setComponentEnabledSetting(new ComponentName(AppApplication.getInstance().getPackageName(), componentName),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 根据包名判断其他App是否在运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一应用是否是后台运行
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            //适配5.0以上的系统
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

   /* public static ArrayList<AccountInfoBean> getAccountList(Gson gson) {
        return gson.fromJson(AppApplication.userBean.getAccount_info(), new TypeToken<ArrayList<AccountInfoBean>>() {
        }.getType());
    }*/

    /*public static int getAccountAvatar(int position) {
        int resId = 0;
        switch (position % 6) {
            case 0:
                resId = R.drawable.icon_avatar1;
                break;
            case 1:
                resId = R.drawable.icon_avatar2;
                break;
            case 2:
                resId = R.drawable.icon_avatar3;
                break;
            case 3:
                resId = R.drawable.icon_avatar4;
                break;
            case 4:
                resId = R.drawable.icon_avatar5;
                break;
            case 5:
                resId = R.drawable.icon_avatar6;
                break;
            default:
                break;
        }
        return resId;
    }*/
}