package com.example.rxjava.combine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class CombineActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button,button1,button2,button3,button4,button5;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        initView();
        initListener();
    }

    private void initListener() {
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    private void initView() {
        button = findViewById(R.id.btn_1);
        button1 = findViewById(R.id.btn_2);
        button2 = findViewById(R.id.btn_3);
        button3 = findViewById(R.id.btn_4);
        button4 = findViewById(R.id.btn_5);
        button5 = findViewById(R.id.btn_6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                concat();
                break;
            case R.id.btn_2:
                concatArray();
                break;
            case R.id.btn_3:
                merge();
                break;
            case R.id.btn_4:
                mergeArray();
                break;
            case R.id.btn_5:
                zip();
                break;
            case R.id.btn_6:
                concatDelayError();
                break;
        }
    }

    private void zip() {
        /**
         * 按照原先事件序列进行对位合并
         * 最终的观察者数量为多个观察者数量中最少的数量
         */
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("zzf", "被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                Log.d("zzf", "被观察者1发送了事件2");
                emitter.onNext(2);
                Thread.sleep(1000);

                Log.d("zzf", "被观察者1发送了事件3");
                emitter.onNext(3);
                Thread.sleep(1000);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("zzf", "被观察者2发送了事件A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.d("zzf", "被观察者2发送了事件B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d("zzf", "被观察者2发送了事件C");
                emitter.onNext("C");
                Thread.sleep(1000);

                Log.d("zzf", "被观察者2发送了事件D");
                emitter.onNext("D");
                Thread.sleep(1000);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable.zip(observable, observable1, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d("zzf", "最终接收到的事件 =  " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "onComplete");
            }
        });
    }

    private void concatDelayError() {
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new NullPointerException());
                    }
                }),
                Observable.just(4,5,6)
        ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer aLong) {
                Log.d("zzf", "接收到了事件"+ aLong  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "对Complete事件作出响应");
            }
        });
    }

    private void mergeArray() {
        /**
         * 合并多个观察者  数量 》 4
         * 并行
         */

        Observable.mergeArray(
                Observable.intervalRange(0, 5, 1, 1, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 5, 1, 1, TimeUnit.SECONDS)
        ).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d("zzf", "接收到了事件"+ aLong  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "对Complete事件作出响应");
            }
        });
    }

    private void merge() {
        /**
         * 组合多个观察者 《= 4个
         * 并行
         */
        Observable.merge(
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS)
        ).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d("zzf", "接收到了事件"+ aLong  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "对Complete事件作出响应");
            }
        });
    }

    private void concatArray() {
        /**
         * 组合多个观察者>4个
         * 串行执行
         */
        Observable.concatArray(
                Observable.just(1, 2, 3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9),
                Observable.just(10, 11, 12),
                Observable.just(13, 14, 15)
        ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d("zzf", "接收到了事件"+ integer  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "对Complete事件作出响应");
            }
        });
    }

    private void concat() {
        /**
         * 组合多个观察者《=4个
         * 串行执行
         */
        Observable.concat(
                Observable.just(1,2,3),
                Observable.just(4,5,6),
                Observable.just(7,8,9)
        ).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d("zzf", "接收到了事件"+ integer  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzf", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "对Complete事件作出响应");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
