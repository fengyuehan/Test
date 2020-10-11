package com.example.transition;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context sContext;

    public static Context getInstance() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
