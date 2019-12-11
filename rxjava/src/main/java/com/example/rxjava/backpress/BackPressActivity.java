package com.example.rxjava.backpress;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName BackPressActivity
 * @Description TODO
 * @Author user
 * @Date 2019/12/11
 * @Version 1.0
 */
public class BackPressActivity extends AppCompatActivity {
    private Button button1,button2;
    private final String TAG = "zzf";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_press);
        initView();
        initListener();
    }

    private void initListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronizeBackPress();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asynchronousBackPress();
            }
        });
    }

    private void asynchronousBackPress() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.e(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.e(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.e(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.e(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void synchronizeBackPress() {
            Flowable.create(new FlowableOnSubscribe<Integer>() {
                @Override
                public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                    // 调用emitter.requested()获取当前观察者需要接收的事件数量
                    long n = emitter.requested();

                    Log.e(TAG, "观察者可接收事件" + n);

                    // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                    for (int i = 0; i < n; i++) {
                        Log.e(TAG, "发送了事件" + i);
                        emitter.onNext(i);
                    }
                }
            },BackpressureStrategy.ERROR)/*.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())*/
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(10);
                        }

                        @Override
                        public void onNext(Integer integer) {
                            Log.e(TAG, "接收到了事件" + integer);
                        }

                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }

    private void initView() {
        button1 = findViewById(R.id.btn_synchronize);
        button2 = findViewById(R.id.btn_asynchronous);
    }
}
