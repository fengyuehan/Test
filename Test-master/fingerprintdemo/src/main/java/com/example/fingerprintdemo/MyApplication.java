package com.example.fingerprintdemo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        if (instance == null) {
            instance = this;
        }

    }

    public static Context getContext() {
        return context;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
