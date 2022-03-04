package com.example.processkeeplive.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.processkeeplive.R;

/**
 * author : zhangzf
 * date   : 2021/1/22
 * desc   :
 */
public class NotificationWorker extends Worker {
    private Context context;
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    /**
     * doWork()方法是在WorkManager管理的后台线程中执行的，更新UI操作只能在主线程中进行。
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        String content = getInputData().getString("content");
        String title = getInputData().getString("title");
        showNotification(title, content);
        Data data = new Data.Builder()
                .putString("output","Work finished!")
                .build();
        return Result.success(data);
    }

    private void showNotification(String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("a1","worker manager",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"a1")
                .setContentText(content)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        manager.notify(1,notification);

    }
}
