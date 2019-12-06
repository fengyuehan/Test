package com.example.notificaitondemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

/**
 * @ClassName Notificaitons
 * @Description TODO
 * @Author user
 * @Date 2019/9/16
 * @Version 1.0
 */
public class Notificaitons {
    public final static int NOTIFICATION_SAMPLE = 0;
    public final static int NOTIFICATION_ACTION = 1;
    public final static int NOTIFICATION_REMOTE_INPUT = 2;
    public final static int NOTIFICATION_BIG_PICTURE_STYLE = 3;
    public final static int NOTIFICATION_BIG_TEXT_STYLE = 4;
    public final static int NOTIFICATION_INBOX_STYLE = 5;
    public final static int NOTIFICATION_MEDIA_STYLE = 6;
    public final static int NOTIFICATION_MESSAGING_STYLE = 7;
    public final static int NOTIFICATION_PROGRESS = 8;
    public final static int NOTIFICATION_CUSTOM_HEADS_UP = 9;
    public final static int NOTIFICATION_CUSTOM = 10;

    public final static String ACTION_SIMPLE = "com.android.peter.notificationdemo.ACTION_SIMPLE";
    public final static String ACTION_ACTION = "com.android.peter.notificationdemo.ACTION_ACTION";
    public final static String ACTION_REMOTE_INPUT = "com.android.peter.notificationdemo.ACTION_REMOTE_INPUT";
    public final static String ACTION_BIG_PICTURE_STYLE = "com.android.peter.notificationdemo.ACTION_BIG_PICTURE_STYLE";
    public final static String ACTION_BIG_TEXT_STYLE = "com.android.peter.notificationdemo.ACTION_BIG_TEXT_STYLE";
    public final static String ACTION_INBOX_STYLE = "com.android.peter.notificationdemo.ACTION_INBOX_STYLE";
    public final static String ACTION_MEDIA_STYLE = "com.android.peter.notificationdemo.ACTION_MEDIA_STYLE";
    public final static String ACTION_MESSAGING_STYLE = "com.android.peter.notificationdemo.ACTION_MESSAGING_STYLE";
    public final static String ACTION_PROGRESS = "com.android.peter.notificationdemo.ACTION_PROGRESS";
    public final static String ACTION_CUSTOM_HEADS_UP_VIEW = "com.android.peter.notificationdemo.ACTION_CUSTOM_HEADS_UP_VIEW";
    public final static String ACTION_CUSTOM_VIEW = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_LOVE = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_LOVE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_PRE = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_PRE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_NEXT = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_NEXT";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_LYRICS = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_CANCEL = "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL";

    public final static String ACTION_YES = "com.android.peter.notificationdemo.ACTION_YES";
    public final static String ACTION_NO = "com.android.peter.notificationdemo.ACTION_NO";
    public final static String ACTION_DELETE = "com.android.peter.notificationdemo.ACTION_DELETE";
    public final static String ACTION_REPLY = "com.android.peter.notificationdemo.ACTION_REPLY";
    public final static String REMOTE_INPUT_RESULT_KEY = "remote_input_content";

    public final static String EXTRA_OPTIONS = "options";
    public final static String MEDIA_STYLE_ACTION_DELETE = "action_delete";
    public final static String MEDIA_STYLE_ACTION_PLAY = "action_play";
    public final static String MEDIA_STYLE_ACTION_PAUSE = "action_pause";
    public final static String MEDIA_STYLE_ACTION_NEXT = "action_next";
    public final static String ACTION_ANSWER = "action_answer";
    public final static String ACTION_REJECT = "action_reject";

    private static volatile Notificaitons mInstance;

    private Notificaitons(){

    }

    public static Notificaitons getInstance(){
        if (mInstance == null){
            synchronized (Notificaitons.class){
                if (mInstance == null){
                    mInstance = new Notificaitons();
                }
            }
        }
        return mInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void sendSimpleNotification(Context context, NotificationManager nm){
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,NotificationService.class);
        intent.setAction(ACTION_SIMPLE);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
        //创建删除通知时发送的广播
        Intent deleteIntent = new Intent(context,NotificationService.class);
        deleteIntent.setAction(ACTION_DELETE);
        PendingIntent deletePendingIntent = PendingIntent.getService(context,0,deleteIntent,0);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle("Simple notification")
                .setContentText("Demo for simple notification !")
                .setAutoCancel(true)
                .setShowWhen(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_notifiation_big))
                .setContentIntent(pendingIntent)
                .setDeleteIntent(deletePendingIntent);
        nm.notify(NOTIFICATION_SAMPLE,builder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendActionNotification(Context context, NotificationManager nm){
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,NotificationService.class);
        intent.setAction(ACTION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle("Action notification")
                .setContentTitle("Demo for action notification !")
                .setAutoCancel(true)
                .setShowWhen(true)
                .setContentIntent(pendingIntent);
        Intent yesIntent = new Intent(context,NotificationService.class);
        yesIntent.setAction(ACTION_YES);
        PendingIntent yesPendingIntent = PendingIntent.getService(context,0,yesIntent,0);
        Notification.Action yesActionBuilder = new Notification.Action.Builder(
                Icon.createWithResource("", R.mipmap.ic_yes),
                "YES",
                yesPendingIntent
        ).build();

        //创建点击通知 NO 按钮时发送的广播
        Intent noIntent = new Intent(context,NotificationService.class);
        noIntent.setAction(ACTION_NO);
        PendingIntent noPendingIntent = PendingIntent.getService(context,0,noIntent,0);
        Notification.Action noActionBuilder = new Notification.Action.Builder(
                Icon.createWithResource("", R.mipmap.ic_no),
                "NO",
                noPendingIntent)
                .build();
        //为通知添加按钮
        builder.setActions(yesActionBuilder,noActionBuilder);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendCustomViewNotification(Context context, NotificationManager nm, NotificationContentWrapper content, boolean isLoved, boolean isPlaying,int flag) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,NotificationService.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
        //创建各个按钮的点击响应广播
        Intent intentLove = new Intent(context,NotificationService.class);
        intentLove.setAction(ACTION_CUSTOM_VIEW_OPTIONS_LOVE);
        PendingIntent piLove = PendingIntent.getService(context,0,intentLove,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPre = new Intent(context,NotificationService.class);
        intentPre.setAction(ACTION_CUSTOM_VIEW_OPTIONS_PRE);
        PendingIntent piPre = PendingIntent.getService(context,0,intentPre,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPlayOrPause = new Intent(context,NotificationService.class);
        intentPlayOrPause.setAction(ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE);
        PendingIntent piPlayOrPause = PendingIntent.getService(context,0, intentPlayOrPause,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNext = new Intent(context,NotificationService.class);
        intentNext.setAction(ACTION_CUSTOM_VIEW_OPTIONS_NEXT);
        PendingIntent piNext = PendingIntent.getService(context,0,intentNext,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentLyrics = new Intent(context,NotificationService.class);
        intentLyrics.setAction(ACTION_CUSTOM_VIEW_OPTIONS_LYRICS);
        PendingIntent piLyrics = PendingIntent.getService(context,0,intentLyrics,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentCancel = new Intent(context,NotificationService.class);
        intentCancel.setAction(ACTION_CUSTOM_VIEW_OPTIONS_CANCEL);
        PendingIntent piCancel = PendingIntent.getService(context,0,intentCancel,PendingIntent.FLAG_UPDATE_CURRENT);
        //创建自定义小视图
        RemoteViews customView = new RemoteViews(context.getPackageName(),R.layout.custom_view_layout);
        customView.setImageViewBitmap(R.id.iv_content,content.bitmap);
        customView.setTextViewText(R.id.tv_title,content.title);
        customView.setTextViewText(R.id.tv_summery,content.summery);
        customView.setImageViewBitmap(R.id.iv_play_or_pause,BitmapFactory.decodeResource(context.getResources(),
                isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play));
        customView.setOnClickPendingIntent(R.id.iv_play_or_pause,piPlayOrPause);
        customView.setOnClickPendingIntent(R.id.iv_next,piNext);
        customView.setOnClickPendingIntent(R.id.iv_lyrics,piLyrics);
        customView.setOnClickPendingIntent(R.id.iv_cancel,piCancel);
        //创建自定义大视图
        RemoteViews customBigView = new RemoteViews(context.getPackageName(),R.layout.custom_big_view_layout);
        customBigView.setImageViewBitmap(R.id.iv_content_big,content.bitmap);
        customBigView.setTextViewText(R.id.tv_title_big,content.title);
        customBigView.setTextViewText(R.id.tv_summery_big,content.summery);
        customBigView.setImageViewBitmap(R.id.iv_love_big,BitmapFactory.decodeResource(context.getResources(),
                isLoved ? R.mipmap.ic_loved : R.mipmap.ic_love));
        customBigView.setImageViewBitmap(R.id.iv_play_or_pause_big,BitmapFactory.decodeResource(context.getResources(),
                isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play));
        customBigView.setOnClickPendingIntent(R.id.iv_love_big,piLove);
        customBigView.setOnClickPendingIntent(R.id.iv_pre_big,piPre);
        customBigView.setOnClickPendingIntent(R.id.iv_play_or_pause_big,piPlayOrPause);
        customBigView.setOnClickPendingIntent(R.id.iv_next_big,piNext);
        customBigView.setOnClickPendingIntent(R.id.iv_lyrics_big,piLyrics);
        customBigView.setOnClickPendingIntent(R.id.iv_cancel_big,piCancel);
        Notification.Builder nb = new Notification.Builder(context,NotificationChannels.MEDIA)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("Custom notification")
                //设置通知内容
                .setContentText("Demo for custom notification !")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi);
        if (flag == 1){
            //设置自定义小视图
            nb.setCustomContentView(customView);
        }else {
            nb.setCustomBigContentView(customBigView);
        }
        //发送通知
        nm.notify(NOTIFICATION_CUSTOM,nb.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMediaStyleNotification(Context context, NotificationManager nm, boolean isPlaying) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,NotificationService.class);
        intent.setAction(ACTION_MEDIA_STYLE);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
        //创建ACTION按钮
        Intent playOrPauseIntent = new Intent(context,NotificationService.class);
        playOrPauseIntent.setAction(ACTION_MEDIA_STYLE);
        playOrPauseIntent.putExtra(EXTRA_OPTIONS,isPlaying ? MEDIA_STYLE_ACTION_PAUSE : MEDIA_STYLE_ACTION_PLAY);
        PendingIntent playOrPausePendingIntent = PendingIntent.getService(context,0, playOrPauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Action playOrPauseAction = new Notification.Action.Builder(
                Icon.createWithResource(context,isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play),
                isPlaying ? "PAUSE" : "PLAY",
                playOrPausePendingIntent)
                .build();
        Intent nextIntent = new Intent(context,NotificationService.class);
        nextIntent.setAction(ACTION_MEDIA_STYLE);
        nextIntent.putExtra(EXTRA_OPTIONS, MEDIA_STYLE_ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(context,1, nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action nextAction = new Notification.Action.Builder(
                Icon.createWithResource(context,R.mipmap.ic_next),
                "Next",
                nextPendingIntent)
                .build();
        Intent deleteIntent = new Intent(context,NotificationService.class);
        deleteIntent.setAction(ACTION_MEDIA_STYLE);
        deleteIntent.putExtra(EXTRA_OPTIONS,MEDIA_STYLE_ACTION_DELETE);
        PendingIntent deletePendingIntent = PendingIntent.getService(context,2, deleteIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action deleteAction = new Notification.Action.Builder(
                Icon.createWithResource(context,R.mipmap.ic_delete),
                "Delete",
                deletePendingIntent)
                .build();
        //创建媒体样式
        Notification.MediaStyle mediaStyle = new Notification.MediaStyle()
                //最多三个Action
                .setShowActionsInCompactView(0,1,2);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context,NotificationChannels.MEDIA)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("Media style notification")
                //设置通知内容
                .setContentText("Demo for media style notification !")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pendingIntent)
                //设置Action按钮
                .setActions(playOrPauseAction,nextAction,deleteAction)
                //设置信箱样式通知
                .setStyle(mediaStyle);

        //发送通知
        nm.notify(NOTIFICATION_MEDIA_STYLE,nb.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendProgressViewNotification(Context context, NotificationManager nm, int progress) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,NotificationService.class);
        intent.setAction(ACTION_PROGRESS);
        PendingIntent pi = PendingIntent.getService(context,0,intent,0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context,NotificationChannels.LOW)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("Downloading...")
                //设置通知内容
                .setContentText(String.valueOf(progress) + "%")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                .setProgress(100,progress,false);
        //发送通知
        nm.notify(NOTIFICATION_PROGRESS,nb.build());
    }

    public void clearAllNotification(NotificationManager notificationManager){
        notificationManager.cancelAll();
    }
}
