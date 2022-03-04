package com.example.processkeeplive.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * author : zhangzf
 * date   : 2021/1/25
 * desc   :
 */
public class ForegroundService extends Service {
    private static int NOTIFICATION_ID = 1000;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
            startForeground(NOTIFICATION_ID, new Notification());
        }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            //API 18-26，发送Notification并将其置为前台后，启动InnerService
            startForeground(NOTIFICATION_ID, new Notification());
            startService(new Intent(this, InnerService.class));
        }else{
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                //进行渠道设置
                //第三个参数值越小，该通知的重要性越低，因为我们只需要开启前台服务，不需要让用户知道
                NotificationChannel channel = new NotificationChannel("channel","keep",NotificationManager.IMPORTANCE_MIN);
                manager.createNotificationChannel(channel);
                Notification notification = new NotificationCompat.Builder(this,"channel").build();
                startForeground(NOTIFICATION_ID,notification);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static class InnerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(NOTIFICATION_ID,new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
