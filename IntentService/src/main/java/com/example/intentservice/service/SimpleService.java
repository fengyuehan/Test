package com.example.intentservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SimpleService extends Service {
    private final String TAG = "SimpleService";
    private MyBinder myBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"-----------onBind--------------");
        //不能再这个方法中做延时操作，非要做延时操作，使用IntentService。
        return myBinder;
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"-----------onCreate--------------");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"-----------onStartCommand--------------");
        //不能再这个方法中做延时操作，非要做延时操作，使用IntentService。
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"-----------onDestroy--------------");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"-----------onUnbind--------------");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public String getInfo(){
            return "调用了服务中的方法";
        }
    }
}

