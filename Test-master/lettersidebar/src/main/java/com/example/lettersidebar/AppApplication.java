package com.example.lettersidebar;

import android.app.Application;
import android.content.Context;

/**
 * @author zzf
 * @date 2019/7/25/025
 * 描述：
 */
public class AppApplication extends Application {
    private static AppApplication mAppApplication;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
        mContext = getApplicationContext();
    }

    public static AppApplication getInstance(){
        return mAppApplication;
    }

    public static Context getContext(){
        return mContext;
    }
}
