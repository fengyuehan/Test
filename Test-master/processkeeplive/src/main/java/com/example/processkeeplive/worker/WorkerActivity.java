package com.example.processkeeplive.worker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.processkeeplive.R;

/**
 * author : zhangzf
 * date   : 2021/1/22
 * desc   :
 */
public class WorkerActivity extends AppCompatActivity {
    private TextView tv_content;
    private Button btn;
    private WorkManager workManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        init();
        workManager = WorkManager.getInstance(this);
        Data data = new Data.Builder()
                .putString("content","I am the content of notification")
                .putString("title","Work Manager Test")
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();
        /**
         * 使用PeriodicWorkRequest只执行一次，并不重复执行。原因是PeriodicWorkRequest默认的时间间隔是15分钟如果设置的时间小于15分钟，就会出现问题。
         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workManager.enqueue(request);
            }
        });

        workManager.getWorkInfoByIdLiveData(request.getId())
                .observe(this, workInfo -> {
                    if (workInfo.getState().isFinished()){
                        tv_content.setText(workInfo.getOutputData().getString("output"));
                    }
                });

        /**
         * WorkManager多任务调度
         *
         * 1、先后顺序执行单个任务
         *   WorkManager.getInstance()
         *     .beginWith(workA)
         *     .then(workB)  instance
         *     .then(workC)
         *     .enqueue();
         *
         * 2、先后顺序执行多个任务列
         *   WorkManager.getInstance()
         *     // First, run all the A tasks (in parallel):
         *     .beginWith(Arrays.asList(workA1, workA2, workA3))
         *     // ...when all A tasks are finished, run the single B task:
         *     .then(workB)
         *     // ...then run the C tasks (in any order):
         *     .then(Arrays.asList(workC1, workC2))
         *     .enqueue();
         *
         * 3、多路径先后执行
         *   WorkContinuation chain1 = WorkManager.getInstance()
         *     .beginWith(workA)
         *     .then(workB);
         *  WorkContinuation chain2 = WorkManager.getInstance()
         *     .beginWith(workC)
         *     .then(workD);
         *  WorkContinuation chain3 = WorkContinuation
         *     .combine(Arrays.asList(chain1, chain2))
         *     .then(workE);
         *  chain3.enqueue();
         *
         */
    }

    private void init() {
        tv_content = findViewById(R.id.tv_content);
        btn = findViewById(R.id.btn);
    }
}
