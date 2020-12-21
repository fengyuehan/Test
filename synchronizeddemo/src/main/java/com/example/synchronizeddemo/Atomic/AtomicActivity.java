package com.example.synchronizeddemo.Atomic;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * author : zhangzf
 * date   : 2020/12/21
 * desc   :
 */
public class AtomicActivity extends AppCompatActivity {
    public  int count = 0;
    public static AtomicLong count1 = new AtomicLong(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atomic);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_atomic_example_1).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AtomicExample1();
            }
        });
        findViewById(R.id.btn_atomic_example_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample2();
            }
        });
        findViewById(R.id.btn_atomic_example_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtomicExample2();
            }
        });
        findViewById(R.id.btn_atomic_example_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_atomic_example_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void AtomicExample2() {
        int requestTotal = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        long start = System.currentTimeMillis();
        for (int i = 0; i < requestTotal; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //add();
                    add1();
                    countDownLatch.countDown();
                }

            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("zzf","count=" + count);
        Log.e("zzf","count=" + count1);
        Log.e("zzf","耗时：" + (System.currentTimeMillis() - start));
    }

    private void add() {
        ++count;
    }

    private void add1() {
        count1.getAndIncrement();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AtomicExample1() {
        AtomicLong count = new AtomicLong(0);
        LongUnaryOperator longUnaryOperator = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long operand) {
                return 1;
            }
        };

        LongBinaryOperator longBinaryOperator = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        };
        /**
         * getAndIncrement:以原子方式将当前值加1，返回旧值 （i++）
         */
        Log.e("zzf","getAndIncrement=" + count.getAndIncrement());
        /**
         * 以原子方式将当前值加1，返回新值（++i）
         */
        Log.e("zzf","incrementAndGet=" + count.incrementAndGet());
        /**
         * 以原子方式将当前值减少 1，返回旧值（i--）
         */
        Log.e("zzf","incrementAndGet=" + count.getAndDecrement());
        /**
         * /以原子方式将当前值减少 1，返回旧值 （--i）
         */
        Log.e("zzf","incrementAndGet=" + count.decrementAndGet());
        /**
         * 以原子方式将输入的数值与实例中的值（AtomicLong里的value）相加，并返回结果
         */
        Log.e("zzf","addAndGet=" + count.addAndGet(10));
        /**
         * 以原子方式设置为`newValue`的值，并返回旧值
         */
        Log.e("zzf","getAndSet=" + count.getAndSet(100));

        Log.e("zzf","get=" + count.get());

        Log.e("zzf","*********** JDK 1.8 ***********");

        /**
         * 使用将给定函数定函数的结果原子更新当前值，返回上一个值
         */
        Log.e("zzf","getAndUpdate=" + count.getAndUpdate(longUnaryOperator));

        Log.e("zzf","getAndUpdate=" + count.getAndUpdate(longUnaryOperator));
        Log.e("zzf","get=" + count.get());
        /**
         *使用给定函数应用给当前值和给定值的结果原子更新当前值，返回上一个值
         */
        Log.e("zzf","getAndAccumulate=" + count.getAndAccumulate(2, longBinaryOperator));
        Log.e("zzf","getAndAccumulate=" + count.getAndAccumulate(2, longBinaryOperator));
    }
}
