package com.example.wanandroid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.wanandroid.base.activity.BaseActivity;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    public NetEvent eventActivity = BaseActivity.eventActivity;
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public interface NetEvent {
        void onNetChange(int netMobile);
    }
}
