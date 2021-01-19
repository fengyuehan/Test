package com.example.service;

import android.app.Application;
import android.content.Context;

/**
 * author : zhangzf
 * date   : 2021/1/18
 * desc   :
 */
public class MyApplication  extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
