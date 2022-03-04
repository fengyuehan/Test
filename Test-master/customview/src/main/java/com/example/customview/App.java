package com.example.customview;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.example.customview.lockscreen.ScreenLockService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //启动服务，监听锁屏
        Intent intent = new Intent(this.getApplicationContext(), ScreenLockService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }
}
