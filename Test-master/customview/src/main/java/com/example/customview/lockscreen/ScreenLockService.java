package com.example.customview.lockscreen;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.customview.R;

public class ScreenLockService extends Service {

    private KeyguardManager.KeyguardLock keyguardLock;
    private BroadcastReceiver mScreenLockBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            switch (intent.getAction()) {
                //解锁
                case Intent.ACTION_SCREEN_ON:
                    break;
                //锁屏
                case Intent.ACTION_SCREEN_OFF:
                    Intent jumpIntent = new Intent(context, ScreenLockActivity.class);
                    jumpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(jumpIntent);
                    break;
                //用户解锁
                case Intent.ACTION_USER_PRESENT:
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenLockBroadcastReceiver, intentFilter);
        startForeground(100, createNotification());
        if (keyguardLock != null) {
            keyguardLock.disableKeyguard();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keyguardLock != null) {
            keyguardLock.reenableKeyguard();
        }
        if (mScreenLockBroadcastReceiver != null) {
            unregisterReceiver(mScreenLockBroadcastReceiver);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //取消系统锁屏
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        keyguardLock = keyguardManager.newKeyguardLock("");
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification createNotification() {
        String channelId = "10086";
        String channelName = "screen_lock_notification_channel";
        //8.0以后必须添加通知渠道，否则不会被发送
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
        /**
         * 这个intent表示点击通知的时候，跳转到哪个activity
         */
        Intent intent = new Intent(getApplicationContext(), ScreenLockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setTicker("New")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("屏幕锁")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText("正在运行")
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        return notification;
    }
}
