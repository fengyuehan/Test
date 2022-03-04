package com.example.asyntask;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_start,btn_cancel,btn_handler,btn_a;
    private ProgressBar pb;
    private TextView textView;
    private MyAsynTask mMyAsynTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_a = findViewById(R.id.btn_a);
        pb = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.tv_show);
        btn_handler = findViewById(R.id.btn_handler);
        mMyAsynTask = new MyAsynTask(pb,textView);
        textView.setText("还没有开始");
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyAsynTask.execute();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AsyncTask不与任何组件绑定生命周期
                //在Activity 或 Fragment中使用 AsyncTask时，最好在Activity 或 Fragment的onDestory（）调用 cancel(boolean)；
                mMyAsynTask.cancel(true);
            }
        });
        btn_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HandlerActivity.class));
            }
        });
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyTask("AsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
                new MyTask("AsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
            }
        });
    }

    static class MyTask extends AsyncTask<String, Integer, String>{
        private String mName = "AsyncTask";

        public MyTask(String name) {
            super();
            mName = name;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.e("AsyncTask", s + "execute finish at " + df.format(new Date()));
        }
    }

    //输入的参数 String  执行进度 Integer 返回的结果  String
    // a. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
    // b. Progress：异步任务执行过程中，返回下载进度值的类型
    // c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致

     static class MyAsynTask extends AsyncTask<String,Integer,String>{
         /**
          * 避免内存泄露，用弱引用包裹
          */
         private WeakReference<ProgressBar> progressBarWeakReference;
         private WeakReference<TextView> textViewWeakReference;

         public  MyAsynTask(ProgressBar progressBar,TextView textView){
             progressBarWeakReference = new WeakReference<>(progressBar);
             textViewWeakReference = new WeakReference<>(textView);
         }


        @Override
        protected void onPreExecute() {
            TextView textView = textViewWeakReference.get();
            textView.setText("加载中");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int count = 0;
                while (count <= 100){
                    Thread.sleep(1000);
                    count += 1;
                    publishProgress(count);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar progressBar = progressBarWeakReference.get();
            progressBar.setProgress(values[0]);
            TextView textView = textViewWeakReference.get();
            textView.setText("Loading.."+ values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = textViewWeakReference.get();
            textView.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            TextView textView = textViewWeakReference.get();
            textView.setText("已取消");
            ProgressBar progressBar = progressBarWeakReference.get();
            progressBar.setProgress(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAsynTask.cancel(true);
    }
}
