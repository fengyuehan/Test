package com.example.zgg.dragger.module;

import android.app.Activity;

import dagger.Module;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public Activity provideActivity(){
        return mActivity;
    }
}
