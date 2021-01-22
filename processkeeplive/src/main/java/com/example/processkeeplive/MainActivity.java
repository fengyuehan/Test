package com.example.processkeeplive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.processkeeplive.JobScheduler.MyJobService;
import com.example.processkeeplive.worker.WorkerActivity;

public class MainActivity extends AppCompatActivity {

    private Button JobServiceButton,btn_worker;
    private ComponentName jobService;
    private static final long JOB_PERIODIC = 15 * 60 * 1000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeepManager.getManager().registerReceiver(this);
        init();
        jobService = new ComponentName(this, MyJobService.class);
        Intent intent = new Intent(this,MyJobService.class);
        startService(intent);
    }

    private void init() {
        JobServiceButton = findViewById(R.id.btn_job);
        JobServiceButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                pollServer();
            }
        });
        btn_worker = findViewById(R.id.btn_worker);
        btn_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WorkerActivity.class));
            }
        });
    }

    /**
     * setRequiredNetworkType：设置需要的网络条件，有三个取值：-
     * JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
     * JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
     * JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
     *
     * setPersisted：重启后是否还要继续执行，此时需要声明权限RECEIVE_BOOT_COMPLETED，否则会报错“java.lang.IllegalArgumentException: Error: requested job be persisted without holding RECEIVE_BOOT_COMPLETED permission.”
     * 而且RECEIVE_BOOT_COMPLETED需要在安装的时候就要声明，如果一开始没声明，而在升级时才声明，那么依然会报权限不足的错误。
     *
     * setRequiresCharging：是否在充电时执行
     * setRequiresDeviceIdle：是否在空闲时执行
     * setPeriodic：设置时间间隔，单位毫秒。该方法不能和setMinimumLatency、setOverrideDeadline这两个同时调用，
     * 否则会报错“java.lang.IllegalArgumentException: Can't call setMinimumLatency() on a periodic job”，
     * 或者报错“java.lang.IllegalArgumentException: Can't call setOverrideDeadline() on a periodic job”。
     *
     * setMinimumLatency：设置至少延迟多久后执行，单位毫秒。
     * setOverrideDeadline：设置最多延迟多久后执行，单位毫秒。
     * setBackoffCriteria: 退避策略 , 可以设置等待时间以及重连策略
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void pollServer() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("data","zzf");
        JobInfo jobInfo = new JobInfo.Builder(10087, jobService) //任务Id等于123
                .setPeriodic(JOB_PERIODIC)
                .setMinimumLatency(12345)// 任务最少延迟时间
                .setOverrideDeadline(60000)// 任务deadline，当到期没达到指定条件也会开始执行
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)// 网络条件，默认值NETWORK_TYPE_NONE
                .setRequiresCharging(true)// 是否充电
                .setRequiresDeviceIdle(false)// 设备是否空闲
                .setPersisted(true) //设备重启后是否继续执行
                .setBackoffCriteria(3000,JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略
                .setExtras(bundle)
                .build();
        scheduler.schedule(jobInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getManager().unRegisterReceiver(this);
    }
}