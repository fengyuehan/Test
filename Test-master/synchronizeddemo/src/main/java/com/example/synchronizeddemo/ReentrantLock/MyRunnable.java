package com.example.synchronizeddemo.ReentrantLock;

import android.util.Log;

import java.util.concurrent.locks.ReentrantLock;

public class MyRunnable implements Runnable {
    private int num = 0;
    private ReentrantLock lock = new ReentrantLock(true);
    @Override
    public void run() {
        while (num < 20){
            lock.lock();
            try{
                num++;
                Log.e("zzf",Thread.currentThread().getName() + "获得锁，num is"+ num);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
