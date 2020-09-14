package com.example.dell.myapplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

public class Utils {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 对抗签名重打包
     */

    public static int getSignature(String packageName,Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        int sign = 0;
        try{
            packageInfo = packageManager.getPackageInfo(packageName,PackageManager.GET_SIGNATURES);
            Signature[] s = packageInfo.signatures;
            sign = s[0].hashCode();
        }catch (Exception e){
            sign = 0;
            e.printStackTrace();
        }
        return sign;
    }
}
