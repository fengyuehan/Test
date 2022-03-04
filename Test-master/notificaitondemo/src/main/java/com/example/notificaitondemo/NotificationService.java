package com.example.notificaitondemo;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_ANSWER;
import static android.content.Intent.ACTION_DELETE;
import static com.example.notificaitondemo.Notificaitons.ACTION_ACTION;
import static com.example.notificaitondemo.Notificaitons.ACTION_BIG_PICTURE_STYLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_BIG_TEXT_STYLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_HEADS_UP_VIEW;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_LOVE;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_NEXT;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE;
import static com.example.notificaitondemo.Notificaitons.ACTION_CUSTOM_VIEW_OPTIONS_PRE;
import static com.example.notificaitondemo.Notificaitons.ACTION_INBOX_STYLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_MEDIA_STYLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_MESSAGING_STYLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_NO;
import static com.example.notificaitondemo.Notificaitons.ACTION_PROGRESS;
import static com.example.notificaitondemo.Notificaitons.ACTION_REJECT;
import static com.example.notificaitondemo.Notificaitons.ACTION_REMOTE_INPUT;
import static com.example.notificaitondemo.Notificaitons.ACTION_REPLY;
import static com.example.notificaitondemo.Notificaitons.ACTION_SIMPLE;
import static com.example.notificaitondemo.Notificaitons.ACTION_YES;
import static com.example.notificaitondemo.Notificaitons.EXTRA_OPTIONS;
import static com.example.notificaitondemo.Notificaitons.MEDIA_STYLE_ACTION_DELETE;
import static com.example.notificaitondemo.Notificaitons.MEDIA_STYLE_ACTION_NEXT;
import static com.example.notificaitondemo.Notificaitons.MEDIA_STYLE_ACTION_PAUSE;
import static com.example.notificaitondemo.Notificaitons.MEDIA_STYLE_ACTION_PLAY;
import static com.example.notificaitondemo.Notificaitons.NOTIFICATION_ACTION;
import static com.example.notificaitondemo.Notificaitons.NOTIFICATION_CUSTOM;
import static com.example.notificaitondemo.Notificaitons.NOTIFICATION_CUSTOM_HEADS_UP;
import static com.example.notificaitondemo.Notificaitons.NOTIFICATION_MEDIA_STYLE;
import static com.example.notificaitondemo.Notificaitons.NOTIFICATION_REMOTE_INPUT;
import static com.example.notificaitondemo.Notificaitons.REMOTE_INPUT_RESULT_KEY;

/**
 * @ClassName NotificationService
 * @Description TODO
 * @Author user
 * @Date 2019/9/16
 * @Version 1.0
 */
public class NotificationService extends Service {
    public final static String ACTION_SEND_PROGRESS_NOTIFICATION = "ACTION_SEND_PROGRESS_NOTIFICATION";

    private Context mContext;
    private NotificationManager notificationManager;
    private boolean mIsLoved;
    private boolean mIsPlaying = true;

    private List<NotificationContentWrapper> mContent = new ArrayList<>();
    private int mCurrentPosition = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        initNotificationContent();
    }

    private void initNotificationContent() {
        mContent.clear();
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.custom_view_picture_pre), "远走高飞", "金志文"));
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.custom_view_picture_current), "最美的期待", "周笔畅 - 最美的期待"));
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.custom_view_picture_next), "你打不过我吧", "跟风超人"));
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_SIMPLE:
                break;
            case ACTION_ACTION:
                break;
            case ACTION_REMOTE_INPUT:
                break;
            case ACTION_BIG_PICTURE_STYLE:
                break;
            case ACTION_BIG_TEXT_STYLE:
                break;
            case ACTION_INBOX_STYLE:
                break;
            case ACTION_MEDIA_STYLE:
                dealWithActionMediaStyle(intent);
                break;
            case ACTION_MESSAGING_STYLE:
                break;
            case ACTION_YES:
                notificationManager.cancel(NOTIFICATION_ACTION);
                break;
            case ACTION_NO:
                notificationManager.cancel(NOTIFICATION_ACTION);
                break;
            case ACTION_DELETE:
                break;
            case ACTION_REPLY:
                dealWithActionReplay(intent);
                break;
            case ACTION_PROGRESS:
                break;
            case ACTION_SEND_PROGRESS_NOTIFICATION:
                dealWithActionSendProgressNotification();
                break;
            case ACTION_CUSTOM_HEADS_UP_VIEW:
                dealWithActionCustomHeadsUpView(intent);
                break;
            case ACTION_CUSTOM_VIEW:
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_LOVE:
                Notificaitons.getInstance().sendCustomViewNotification(this, notificationManager, mContent.get(mCurrentPosition), !mIsLoved, mIsPlaying,1);
                mIsLoved = !mIsLoved;
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_PRE:
                --mCurrentPosition;
                Notificaitons.getInstance().sendCustomViewNotification(this, notificationManager, getNotificationContent(), mIsLoved, mIsPlaying,1);
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE:
                Notificaitons.getInstance().sendCustomViewNotification(this, notificationManager, mContent.get(mCurrentPosition), mIsLoved, !mIsPlaying,1);
                mIsPlaying = !mIsPlaying;
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_NEXT:
                ++mCurrentPosition;
                Notificaitons.getInstance().sendCustomViewNotification(this, notificationManager, getNotificationContent(), mIsLoved, mIsPlaying,1);
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_LYRICS:
                break;
            case ACTION_CUSTOM_VIEW_OPTIONS_CANCEL:
                notificationManager.cancel(NOTIFICATION_CUSTOM);
                break;
            default:
                break;
        }
        return START_STICKY;
    }

    private NotificationContentWrapper getNotificationContent() {
        switch (mCurrentPosition){
            case -1:
                mCurrentPosition = 2;
                break;
            case 3:
                mCurrentPosition = 0;
                break;
        }
        return mContent.get(mCurrentPosition);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void dealWithActionReplay(Intent intent) {
        Bundle result = RemoteInput.getResultsFromIntent(intent);
        if(result != null) {
            notificationManager.cancel(NOTIFICATION_REMOTE_INPUT);
        }
    }

    private void dealWithActionSendProgressNotification() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                for (int i = 0; i <= 100;i++){
                    Notificaitons.getInstance().sendProgressViewNotification(mContext,notificationManager,i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void dealWithActionCustomHeadsUpView(Intent intent) {
        String headsUpOption = intent.getStringExtra(EXTRA_OPTIONS);
        if(headsUpOption == null) {
            return;
        }
        switch (headsUpOption) {
            case ACTION_ANSWER:
                notificationManager.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
                break;
            case ACTION_REJECT:
                notificationManager.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
                break;
            default:
                //do nothing
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dealWithActionMediaStyle(Intent intent) {
        String option = intent.getStringExtra(EXTRA_OPTIONS);
        if (option == null) {
            return;
        }
        switch (option) {
            case MEDIA_STYLE_ACTION_PAUSE:
                Notificaitons.getInstance().sendMediaStyleNotification(this, notificationManager, false);
                break;
            case MEDIA_STYLE_ACTION_PLAY:
                Notificaitons.getInstance().sendMediaStyleNotification(this, notificationManager, true);
                break;
            case MEDIA_STYLE_ACTION_NEXT:
                break;
            case MEDIA_STYLE_ACTION_DELETE:
                notificationManager.cancel(NOTIFICATION_MEDIA_STYLE);
                break;
            default:
                break;
        }
    }
}
