package com.example.floatingwindow;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class VoiceFloatingService extends Service {
    private VoiceFloatingView mVoiceFloatingView;
    public static final String ACTION_SHOW_FLOATING = "action_show_floating";
    public static final String ACTION_DISMISS_FLOATING = "action_dismiss_floating";
    private static VoiceFloatingService mVoiceFloatingService;
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
        mVoiceFloatingService = this;
        mVoiceFloatingView = new VoiceFloatingView(this);
        IntentFilter intentFilter = new IntentFilter(ACTION_SHOW_FLOATING);
        intentFilter.addAction(ACTION_DISMISS_FLOATING);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mVoiceFloatingView.show();
        mVoiceFloatingView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent voiceActivityIntent = new Intent(VoiceFloatingService.this,VoiceActivity.class);
                voiceActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(voiceActivityIntent);
                return true;
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mVoiceFloatingView.dismiss();
        mVoiceFloatingView = null;
        isStart = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();

    }

    public static void stop(){
        mVoiceFloatingService.stopSelf();
        mVoiceFloatingService = null;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_SHOW_FLOATING.equals(intent.getAction())){
                mVoiceFloatingView.show();
            }else if (ACTION_DISMISS_FLOATING.equals(intent.getAction())){
                mVoiceFloatingView.dismiss();
            }

        }
    };
}
