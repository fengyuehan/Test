package com.example.wanandroid.base.app;

import android.app.Application;

import com.example.wanandroid.di.component.ApplicationComponent;
import com.example.wanandroid.di.component.DaggerApplicationComponent;
import com.example.wanandroid.di.module.ApplicationModule;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
        myApplication = this;
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
