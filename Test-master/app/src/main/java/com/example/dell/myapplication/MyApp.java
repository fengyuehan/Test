package com.example.dell.myapplication;

import android.app.Application;
import android.os.Build;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initArouter();
    }

    private void initArouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
