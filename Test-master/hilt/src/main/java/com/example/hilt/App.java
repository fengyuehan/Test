package com.example.hilt;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
