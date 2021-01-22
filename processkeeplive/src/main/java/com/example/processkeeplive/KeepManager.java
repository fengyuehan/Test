package com.example.processkeeplive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * author : zhangzf
 * date   : 2021/1/21
 * desc   :
 */
public class KeepManager {

    private static class Instance {
        private static final KeepManager INSTANCE = new KeepManager();
    }

    private KeepManager(){}
    public static KeepManager getManager(){
        return Instance.INSTANCE;
    }

    private KeyReceiver mKeyReceiver;
    private WeakReference<Activity> mKeepAct;

    /**
     * 注册开关屏广播
     * @param context
     */
    public void registerReceiver(Context context){
        if (mKeyReceiver != null) return;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mKeyReceiver = new KeyReceiver();
        context.registerReceiver(mKeyReceiver,filter);
    }

    /**
     * 注销广播
     * @param context
     */
    public void unRegisterReceiver(Context context){
        if (mKeyReceiver != null) {
            context.unregisterReceiver(mKeyReceiver);
        }
    }

    public void setKeepActivity(Activity activity){
        mKeepAct = new WeakReference<Activity>(activity);
    }

    /**
     * 关屏时打开1px的Activity
     * @param context
     */
    public void startKeepActivity(Context context){
        Log.e("zzf","---------打开了activity-----------");
        Intent intent = new Intent(context,OnePxActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 开屏时把Activity销毁
     */
    public void finishActivity(){
        Log.e("zzf","---------关闭了activity-----------");
        if (mKeepAct != null) {
            Activity activity = mKeepAct.get();
            if (activity != null) {
                activity.finish();
            }
            mKeepAct = null;
        }
    }
}
