package com.example.floatingwindow;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ExampleFloatingService extends Service {
    private FloatingWindowHelper mFloatingWindowHelper;
    private View mExampleViewA,mExampleViewB;
    public static final String ACTION_CLICK = "ACTION_CLICK";
    public static boolean isStart;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isStart = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(ACTION_CLICK));
        mFloatingWindowHelper = new FloatingWindowHelper(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        mExampleViewA = inflater.inflate(R.layout.widget_test_view, null, false);
        mExampleViewB = inflater.inflate(R.layout.widget_test_view_b, null, false);
        onClick();
    }

    private void onClick() {
        if (!mFloatingWindowHelper.contains(mExampleViewA)){
            mFloatingWindowHelper.addView(mExampleViewA);
        }else if (!mFloatingWindowHelper.contains(mExampleViewB)){
            mFloatingWindowHelper.addView(mExampleViewB,200,200,true);
        }else {
            mFloatingWindowHelper.clear();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        mFloatingWindowHelper.destroy();
        isStart = false;
        super.onDestroy();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_CLICK.equals(intent.getAction())){
                onClick();
            }
        }
    };
}
