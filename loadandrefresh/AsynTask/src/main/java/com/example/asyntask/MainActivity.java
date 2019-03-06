package com.example.asyntask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_start,btn_cancel;
    private ProgressBar pb;
    private TextView textView;
    private MyAsynTask mMyAsynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        btn_cancel = findViewById(R.id.btn_cancel);
        pb = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.tv_show);
        mMyAsynTask = new MyAsynTask();
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
    }

    //输入的参数 String  执行进度 Integer 返回的结果  String
    // a. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
    // b. Progress：异步任务执行过程中，返回下载进度值的类型
    // c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致

     class MyAsynTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
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
            pb.setProgress(values[0]);
            textView.setText("Loading.."+ values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            textView.setText("已取消");
            pb.setProgress(0);
        }
    }
}
