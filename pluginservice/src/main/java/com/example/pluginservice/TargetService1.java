package com.example.pluginservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TargetService1 extends Service {

    private static final String TAG = "TargetService1";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called with ");
        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "插件Service==>TargetService1#onStart()", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart() called with intent = [" + intent + "], startId = [" + startId + "]");
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "插件Service==>TargetService1#onDestroy()", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy() called with ");
        super.onDestroy();
    }
}
