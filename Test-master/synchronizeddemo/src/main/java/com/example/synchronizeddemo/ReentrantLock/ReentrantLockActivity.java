package com.example.synchronizeddemo.ReentrantLock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockActivity extends AppCompatActivity {
    ReentrantLock reentrantLock = new ReentrantLock();
    private MyRunnable myRunnable = new MyRunnable();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printLog();
            }
        });
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                printLog();
            }
        });

        final Thread t3 = new Thread(myRunnable);
        final Thread t4 = new Thread(myRunnable);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.start();
                t2.start();
                Log.e("zzf","--------------------------------");
                t3.start();
                t4.start();
            }
        });
    }

    private void printLog(){
        try {
            reentrantLock.lock();
            for (int i = 0; i < 5; i++){
                Log.e("zzf",Thread.currentThread().getName() + " is print " + i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }



}
