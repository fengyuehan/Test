package com.example.asyntask;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

public class HandlerActivity extends AppCompatActivity {
    private Button button1,button2,button3,button4;
    public static final int MESSAGE_TYPE_SYNC=1;
    public static final int MESSAGE_TYPE_ASYN=2;
    private int token;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        initView();
        initHandler();
    }

    private void initHandler() {
        new Thread(new Runnable() {
            @SuppressLint("HandlerLeak")
            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        //super.handleMessage(msg);
                        if (msg.what == MESSAGE_TYPE_SYNC){
                            Log.d("MainActivity","收到普通消息");
                        }else if (msg.what == MESSAGE_TYPE_ASYN){
                            Log.d("MainActivity","收到异步消息");
                        }
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    private void initView() {
        button1 = findViewById(R.id.send_syne);
        button2 = findViewById(R.id.remove_sunc);
        button3 = findViewById(R.id.send_message);
        button4 = findViewById(R.id.send_async_message);
        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                sendSyncBarrier();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                removeSyncBarrier();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSyncMessage();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                sendAsynMessage();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sendAsynMessage() {
        Log.d("MainActivity","插入异步消息");
        Message message=Message.obtain();
        message.what=MESSAGE_TYPE_ASYN;
        message.setAsynchronous(true);
        mHandler.sendMessageDelayed(message,1000);
    }

    private void sendSyncMessage() {
        Log.d("MainActivity","插入普通消息");
        Message message= Message.obtain();
        message.what=MESSAGE_TYPE_SYNC;
        mHandler.sendMessageDelayed(message,1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void removeSyncBarrier() {
        try{
            Log.d("MainActivity","移除屏障");
            MessageQueue queue=mHandler.getLooper().getQueue();
            Method method=MessageQueue.class.getDeclaredMethod("removeSyncBarrier",int.class);
            method.setAccessible(true);
            method.invoke(queue,token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendSyncBarrier() {
        try{
            Log.d("MainActivity","插入同步屏障");
            MessageQueue queue=mHandler.getLooper().getQueue();
            Method method=MessageQueue.class.getDeclaredMethod("postSyncBarrier");
            method.setAccessible(true);
            token= (int) method.invoke(queue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
