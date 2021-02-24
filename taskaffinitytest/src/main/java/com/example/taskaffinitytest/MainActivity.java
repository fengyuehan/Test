package com.example.taskaffinitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * intent 未指定 FLAG_ACTIVITY_NEW_TASK,TestActivity未指定taskAffinity 属性
                 * 2021-02-20 11:23:07.484 21211-21211/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 286
                 * 2021-02-20 11:23:28.023 21211-21211/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 286
                 *
                 *intent 未指定 FLAG_ACTIVITY_NEW_TASK,TestActivity指定taskAffinity 属性
                 * 2021-02-20 11:25:36.709 21721-21721/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 288
                 * 2021-02-20 11:25:41.590 21721-21721/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 288
                 *
                 *intent 指定 FLAG_ACTIVITY_NEW_TASK,TestActivity未指定taskAffinity 属性
                 * 2021-02-20 11:28:08.313 22119-22119/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 291
                 * 2021-02-20 11:28:12.974 22119-22119/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 291
                 *
                 *intent 指定 FLAG_ACTIVITY_NEW_TASK,TestActivity指定taskAffinity 属性
                 * 2021-02-20 11:29:01.256 22556-22556/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 293
                 * 2021-02-20 11:29:08.408 22556-22556/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 294
                 *
                 * intent 指定 FLAG_ACTIVITY_NEW_TASK ,TestActivity未指定taskAffinity 属性,启动模式为SingleTask
                 * 2021-02-20 11:30:38.551 22732-22732/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 296
                 * 2021-02-20 11:30:40.949 22732-22732/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 296
                 *
                 * intent 指定 FLAG_ACTIVITY_NEW_TASK ,TestActivity未指定taskAffinity 属性,启动模式为singleTop
                 * 2021-02-20 11:32:44.453 23015-23015/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 298
                 * 2021-02-20 11:32:48.219 23015-23015/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 298
                 *
                 * intent 指定 FLAG_ACTIVITY_NEW_TASK ,TestActivity未指定taskAffinity 属性,启动模式为singleInstance
                 * 2021-02-20 11:35:34.122 23644-23644/com.example.taskaffinitytest D/zzf: onActivityResumed+MainActivity####taskid = 303
                 * 2021-02-20 11:35:44.829 23644-23644/com.example.taskaffinitytest D/zzf: onActivityResumed+TestActivity####taskid = 304
                 *
                 * FLAG_ACTIVITY_NEW_TASK得配合taskAffinity一起才起效，然后singleInstance的启动模式也起效
                 * 非activity启动activity必须添加intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 */
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}