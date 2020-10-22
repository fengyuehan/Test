package com.example.floatingwindow;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class VoiceActivity extends AppCompatActivity {

    private ImageView iv_dismiss,iv_close;
    private boolean mIsClose = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        iv_dismiss = findViewById(R.id.iv_dismiss);
        iv_close = findViewById(R.id.iv_close);
        iv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (Settings.canDrawOverlays(VoiceActivity.this)){
                        showFloatingView();
                        finish();
                    }else {
                        startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName())));
                    }
                }else {
                    showFloatingView();
                    finish();
                }
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsClose = true;
                VoiceFloatingService.stop();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissFloatingView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mIsClose){
            showFloatingView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //隐藏悬浮窗
    private void dismissFloatingView(){
        if (VoiceFloatingService.isStart) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(VoiceFloatingService.ACTION_DISMISS_FLOATING));
        }
    }

    /**
     * 显示悬浮窗
     */
    private void showFloatingView() {
        if (VoiceFloatingService.isStart) {
            //通知显示悬浮窗
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(VoiceFloatingService.ACTION_SHOW_FLOATING));
        } else {
            //启动悬浮窗管理服务
            startService(new Intent(this, VoiceFloatingService.class));
        }
    }
}
