package com.example.wanandroid.di.module;

import android.content.Context;

import com.example.wanandroid.base.app.MyApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MyApplication mMyApplication;

    public ApplicationModule(MyApplication mMyApplication) {
        this.mMyApplication = mMyApplication;
    }
    @Provides
    Context provideApplicationContext() {
        return mMyApplication.getApplicationContext();
    }
}
