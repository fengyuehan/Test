package com.example.processkeeplive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * author : zhangzf
 * date   : 2021/1/21
 * desc   :
 */
public class KeyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("zzf","action="+action);
        if (TextUtils.equals(action,Intent.ACTION_SCREEN_OFF)) {
            //当屏幕关闭时
            KeepManager.getManager().startKeepActivity(context);
        } else if (TextUtils.equals(action,Intent.ACTION_SCREEN_ON)) {
            //当屏幕开启
            KeepManager.getManager().finishActivity();
        }
    }
}
