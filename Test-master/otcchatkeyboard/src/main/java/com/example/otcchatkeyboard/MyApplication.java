package com.example.otcchatkeyboard;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
