package com.example.synchronizeddemo.LocalDemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : zhangzf
 * date   : 2020/12/21
 * desc   :
 */
public class LocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        init();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInheritableThreadLocal();
            }
        });
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testThreadLocal2();
            }
        });
    }

    /**
     * 线程只有在初始化的时候才会继承父类ThreadLocal的值。也就是若是线程池的话，以及子类依旧不能继承父类的ThreadLocal的值。
     */
    private void testThreadLocal2() {
        final ThreadLocal<String> local = new InheritableThreadLocal<>();
        try {
            local.set("我是主线程");
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            final CountDownLatch c1 = new CountDownLatch(1);
            final CountDownLatch c2 = new CountDownLatch(1);
            //初始化init的时候，赋予了父线程的ThreadLocal的值
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("zzf","线程1" + local.get());
                    c1.countDown();
                }
            });
            c1.await();
            //主线程修改值
            local.set("修改主线程");
            //再次调用，查看效果
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("zzf","线程2" + local.get());
                    c2.countDown();
                }
            });
            c2.await();
            executorService.shutdownNow();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            //使用完毕，清除线程中ThreadLocalMap中的key。
            local.remove();
        }
    }

    private void testInheritableThreadLocal() {
        final ThreadLocal<String> local = new InheritableThreadLocal<>();
        local.set("我是主线程");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("zzf","子线程1" + local.get());
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 若是在线程中开启子线程，那么子线程也会存在一个ThreadLocalMap对象，但是它不存在父线程ThreadLocalMap对象中的值。
     */
    private void init() {
        final ThreadLocal<String> local = new ThreadLocal<>();
        try {
            local.set("我是主线程");
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            final CountDownLatch c1 = new CountDownLatch(1);
            final CountDownLatch c2 = new CountDownLatch(1);

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("zzf","线程1" + local.get());
                    c1.countDown();
                }
            });
            c1.await();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("zzf","线程2" + local.get());
                    c2.countDown();
                }
            });
            c2.await();
            executorService.shutdownNow();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            local.remove();
        }
    }
}
