package com.example.synchronizeddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.synchronizeddemo.Atomic.AtomicActivity;
import com.example.synchronizeddemo.ThreadLocalDemo.LocalActivity;
import com.example.synchronizeddemo.ReentrantLock.ReentrantLockActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReentrantLockActivity.class));
            }
        });
        findViewById(R.id.btn_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalActivity.class));
            }
        });
        findViewById(R.id.btn_Atomic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AtomicActivity.class));
            }
        });
        TextView textView = findViewById(R.id.tv);
        /**
         * synchronized修饰非静态方法，用同一个实例能够保证同步
         */
        /*AccountSync accountSync = new AccountSync();
        Thread thread1 = new Thread(accountSync);
        Thread thread2 = new Thread(accountSync);*/
        /**
         * synchronized修饰非静态方法，用不同实例就不能够保证同步，原因是对象锁，他只针对同一个对象有效，
         * 而不同的对象则相互独立，所以不会有效果
         */
        /*Thread thread1 = new Thread(new AccountSync());
        Thread thread2 = new Thread(new AccountSync());*/
        /**
         * synchronized修饰静态方法，用同一个实例能够保证同步
         */
        /*StaticAccountSync staticAccountSync = new StaticAccountSync();
        Thread thread1 = new Thread(staticAccountSync);
        Thread thread2 = new Thread(staticAccountSync);*/
        /**
         * synchronized修饰静态方法，用不同实例也能能够保证同步，原因是修饰静态方法相当于类锁，
         * 对所有的对象都保证只有一个对象拿到锁资源
         */
        /*Thread thread1 = new Thread(new StaticAccountSync());
        Thread thread2 = new Thread(new StaticAccountSync());*/

        /**
         * synchronized修饰代码块，其锁为this.用同一个实例能够保证同步
         */
        /*AccountSync1 accountSync = new AccountSync1();
        Thread thread1 = new Thread(accountSync);
        Thread thread2 = new Thread(accountSync);*/

        /**
         * synchronized修饰代码块，其锁为this.用不同的实例不能能够保证同步
         */
        /*Thread thread1 = new Thread(new AccountSync1());
        Thread thread2 = new Thread(new AccountSync1());*/

        /**
         * synchronized修饰代码块，其锁为AccountSync2.class.用同一个实例能够保证同步
         */
        /*AccountSync2 accountSync = new AccountSync2();
        Thread thread1 = new Thread(accountSync);
        Thread thread2 = new Thread(accountSync);*/

        /**
         * synchronized修饰代码块，其锁为AccountSync2.class.用不同的实例能够保证同步
         */
        Thread thread1 = new Thread(new AccountSync2());
        Thread thread2 = new Thread(new AccountSync2());
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView.setText(AccountSync2.getValue() + "");
    }

    private static class AccountSync implements Runnable {
        static int i = 0;

        private synchronized void increase() {
            i++;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    private static class StaticAccountSync implements Runnable {
        static int i = 0;

        private static synchronized void increase() {
            i++;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    private static class AccountSync1 implements Runnable {
        static int j = 0;

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 0; i < 10000; i++) {
                    j++;
                }
            }
        }

        public static int getValue() {
            return j;
        }
    }

    private static class AccountSync2 implements Runnable {
        static int j = 0;

        @Override
        public void run() {
            synchronized (AccountSync2.class) {
                for (int i = 0; i < 10000; i++) {
                    j++;
                }
            }
        }

        public static int getValue() {
            return j;
        }
    }
}
