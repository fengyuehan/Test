package com.example.intentservice.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.intentservice.R;

public class IntentServiceActivity extends AppCompatActivity {
    private Button start;
    private TextView tv_status,tv_progress;
    private ProgressBar mProgressBar;

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;
    public final static String ACTION_TYPE_THREAD = "action.type.thread";
    private HandlerThread mH;


    //对于动态广播，在onResume注册广播，在onPause注销广播。

    //当其他优先级更高的应用需要内存时，activity在执行完onPause方法以后就会被销毁，
    // 那么onStop,onDestory方法就会不执行，当再回到这个activity时，就会从onCreate方法开始执行。
    //但是为了成对出现，所以在onResume中进行
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new MyBroadcastReceiver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Broadcast发送的主流程为先匹配action，然后匹配data，最后匹配category。
        IntentFilter intentFilter = new IntentFilter();
        //Android系统会根据配置的 “意图过滤器” 来寻找可以响应该操作的组件，服务。
        intentFilter.addAction(ACTION_TYPE_THREAD);//也可使用当前activity的包名
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver,intentFilter);
    }

    private void initView() {
        start = findViewById(R.id.btn_start);
        tv_status = findViewById(R.id.tv_status);
        tv_progress = findViewById(R.id.tv_progress);
        mProgressBar = findViewById(R.id.progress_bar);

        tv_status.setText("线程状态：未运行");
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        tv_progress.setText("0%");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentServiceActivity.this,MyIntentService.class);
                startService(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        //public final static String ACTION_TYPE_THREAD = "action.type.thread";
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case ACTION_TYPE_THREAD:
                    int progress = intent.getIntExtra("count",0);
                    String status = intent.getStringExtra("status");
                    tv_status.setText("线程状态：" + status);
                    mProgressBar.setProgress(progress);
                    tv_progress.setText(progress + "%");
                    break;
                default:
                    break;
            }
        }
    }
}
