package com.example.rxjava.filter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.R;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zhangzf
 * date   : 2021/2/22
 * desc   :
 */
public class FilterActivity extends AppCompatActivity {
    Button btn_throttleFirst,btn_throttleLast,btn_Debounce,btn_takeUntil,btn_takeWhile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fliter);
        btn_throttleFirst = findViewById(R.id.btn_throttleFirst);
        btn_throttleLast = findViewById(R.id.btn_throttleLast);
        btn_Debounce = findViewById(R.id.btn_Debounce);
        btn_takeUntil = findViewById(R.id.btn_takeUntil);
        btn_takeWhile = findViewById(R.id.btn_takeWhile);
        btn_throttleFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throttleFirstData();
            }
        });
        btn_throttleLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throttleLastData();
            }
        });
        btn_Debounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debounceData();
            }
        });
        btn_takeUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeUntilData();
            }
        });
        btn_takeWhile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeWhileData();
            }
        });
    }

    /**
     * takeUntil :先发送事件，再判断是否符合条件，当符合条件的时候，终止。
     * takeWhile :先判断是否符合条件，再发送事件。当不符合条件的时候，直接终止。
     */
    private void takeWhileData() {
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer <= 5;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("zzf",integer + "");
            }
        });
    }

    private void takeUntilData() {
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 5;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("zzf",integer + "");
            }
        });
    }

    /**
     * 过滤掉了由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
     */
    private void debounceData() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.computation())
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzf", "onError:");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("zzf", "onCompleted:");
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("zzf", "onNext:"+integer);
                    }
                });
    }

    /**
     * 像搜索框搜索操作：
     * throttleLast + interval + take：防爆击 /倒计时  interval会一直发射数据，用take、takeWhile、fliter来作为条件判断
     * debounce + switchMap + filter :优化 App 搜索功能、防止button重复点击
     *
     */
    private void throttleLastData() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws InterruptedException {
                emitter.onNext(1);//第一次不发送
                Thread.sleep(400);
                emitter.onNext(2);
                Thread.sleep(400);
                emitter.onNext(3);
                Thread.sleep(900);
                emitter.onNext(4);
                Thread.sleep(400);
                emitter.onNext(5);
                Thread.sleep(700);
                emitter.onNext(6);
                Thread.sleep(900);
                emitter.onNext(7);

            }
        }).throttleLast(1000,TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer)  {
                Log.e("--------rxjava",integer.toString());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void throttleFirstData() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws InterruptedException {
                emitter.onNext(1);//第一次发送
                Thread.sleep(500);
                emitter.onNext(2);//时间没有超过1s 不发送
                Thread.sleep(500);
                emitter.onNext(3); //时间为500+500 为1 s 发送
                Thread.sleep(500);
                emitter.onNext(4); //时间没有超过1s 不发送
                Thread.sleep(1500);
                emitter.onNext(5); //时间超过1 s 发送
                Thread.sleep(500);
                emitter.onNext(6);//时间没有超过1s 不发送
                Thread.sleep(500);
                emitter.onNext(7);//时间超过1 s 发送

            }
        }).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Log.e("--------rxjava",integer.toString());
            }
        });
    }
}
