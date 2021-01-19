package com.example.synchronizeddemo.wait;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * author : zhangzf
 * date   : 2021/1/5
 * desc   :
 */
public class WaitOrNotifyActivity extends AppCompatActivity {
    //等待列表, 用来记录等待的顺序
    private static List<String> waitList = new LinkedList<>();
    //唤醒列表, 用来唤醒的顺序
    private static List<String> notifyList = new LinkedList<>();
    //等待列表, 用来记录等待的顺序
    private static List<String> waitList1 = new LinkedList<>();
    //唤醒列表, 用来唤醒的顺序
    private static List<String> notifyList1 = new LinkedList<>();
    private static Object lock = new Object();

    /**
     *持有锁的线程会释放锁情况：
     * 1. 执行完同步代码块。
     * 2. 在执行同步代码块的过程中，遇到异常而导致线程终止。
     * 3. 在执行同步代码块的过程中，执行了锁所属对象的wait()方法，这个线程会释放锁，进行对象的等待池。
     *
     * notify选择唤醒的线程是任意的，但是依赖于具体实现的jvm.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        try {
            test();
            Log.e("zzf","==============================================");
            test2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void test2() throws InterruptedException {
        //创建50个线程
        for(int i=0;i<50;i++){
            String threadName = Integer.toString(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        String cthreadName = Thread.currentThread().getName();
                        Log.e("zzf","线程 ["+cthreadName+"] 正在等待.");
                        waitList1.add(cthreadName);
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("zzf","线程 ["+cthreadName+"] 被唤醒了.");
                        notifyList1.add(cthreadName);
                    }
                }
            },threadName).start();
            TimeUnit.MILLISECONDS.sleep(50);
        }

        TimeUnit.SECONDS.sleep(1);

        for(int i=0;i<50;i++){
            synchronized (lock) {
                lock.notify();
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
        TimeUnit.SECONDS.sleep(1);
        Log.e("zzf","wait顺序:"+waitList1.toString());
        Log.e("zzf","唤醒顺序:"+notifyList1.toString());
    }

    private void test() throws InterruptedException {
        //创建50个线程
        for(int i=0;i<50;i++){
            String threadName = Integer.toString(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        String cthreadName = Thread.currentThread().getName();
                        Log.e("zzf","线程 ["+cthreadName+"] 正在等待.");
                        waitList.add(cthreadName);
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("zzf","线程 ["+cthreadName+"] 被唤醒了.");
                        notifyList.add(cthreadName);
                    }
                }
            },threadName).start();
            TimeUnit.MILLISECONDS.sleep(50);
        }

        TimeUnit.SECONDS.sleep(1);

        for(int i=0;i<50;i++){
            synchronized (lock) {
                lock.notify();
                TimeUnit.MILLISECONDS.sleep(10);
            }
        }
        TimeUnit.SECONDS.sleep(1);
        Log.e("zzf","wait顺序:"+waitList.toString());
        Log.e("zzf","唤醒顺序:"+notifyList.toString());
    }
}
