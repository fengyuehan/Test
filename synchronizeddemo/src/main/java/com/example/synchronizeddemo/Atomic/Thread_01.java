package com.example.synchronizeddemo.Atomic;

import android.util.Log;

import java.util.concurrent.CyclicBarrier;

/**
 * author : zhangzf
 * date   : 2020/12/22
 * desc   :
 */
public class Thread_01 extends Thread {
    private CyclicBarrier cbRef;

    public Thread_01(CyclicBarrier cbRef) {
        super();
        this.cbRef = cbRef;
     }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep((long) (Math.random() * 1000));
            Log.e("zzf",Thread.currentThread().getName() + "到了!" + System.currentTimeMillis());
            cbRef.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
