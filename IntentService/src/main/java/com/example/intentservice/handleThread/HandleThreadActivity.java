package com.example.intentservice.handleThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.intentservice.R;

public class HandleThreadActivity extends AppCompatActivity {
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_4);

        mTextView = findViewById(R.id.tv);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerThread = new HandlerThread("HandlerThread");
                mHandlerThread.start();
                mHandler = new Handler(mHandlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1){
                            String str = (String) msg.obj;
                        }
                        Log.d("Kathy","Received Message = " + msg.what + "   CurrentThread = " + Thread
                                .currentThread().getName());
                    }
                };

                Message message = Message.obtain();
                message.obj = "this is message";
                message.what = 1;
                mHandler.sendMessage(message);
                Log.e("zzf", "handleMessage: " + Thread.currentThread().getName());
            }
        });

        //mHandler.sendEmptyMessage(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(2);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
