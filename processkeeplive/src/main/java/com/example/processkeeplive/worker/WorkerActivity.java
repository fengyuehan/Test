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
    }

    private void init() {
        tv_content = findViewById(R.id.tv_content);
        btn = findViewById(R.id.btn);
    }
}
