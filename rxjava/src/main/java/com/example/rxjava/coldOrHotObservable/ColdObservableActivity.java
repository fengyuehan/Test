package com.example.rxjava.coldOrHotObservable;

import android.annotation.SuppressLint;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zhangzf
 * date   : 2021/2/20
 * desc   :
 */
public class ColdObservableActivity extends AppCompatActivity {
    private Button btn_cold,btn_cold_change_hot,btn_hot,btn_hot_change_cold;

    /**
     * Hot Observable 无论有没有观察者进行订阅，事件始终都会发生。当 Hot Observable 有多个订阅者时（多个观察者进行订阅时） , Hot Observable 与订阅者们的关系是一对多的关系，可以与多个订阅者共享信息。
     *
     * Cold Observable 是只有观察者订阅了，才开始执行发射数据流的代码。井且 Cold Observable 和 Observer 只能是一对一的关系 。当有多个不同的订阅者时，消息是重新完整发送的。也就是说，对 Cold Observable ，有多个 Observer 的时候，它们各自的事件是独立的。
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_or_hot);
        initView();
    }

    private void initView() {
        btn_cold = findViewById(R.id.btn_cold);
        btn_cold_change_hot = findViewById(R.id.btn_cold_change_hot);
        btn_hot = findViewById(R.id.btn_hot);
        btn_hot_change_cold = findViewById(R.id.btn_hot_change_cold);
        btn_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cold();
            }
        });
        btn_cold_change_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cold_change_hot();
            }
        });
        btn_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_hot_change_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hot_change_cold();
            }
        });
    }

    private void hot_change_cold() {
        /*Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber1-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber2-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };*/
        Consumer<Long> subscriber1 = new Consumer<Long>() {

            @Override
            public void accept(Long aLong) throws Exception {
                Log.d("zzf", "subscriber1-> " + aLong);
            }
        };

        Consumer<Long> subscriber2 = new Consumer<Long>() {

            @Override
            public void accept(Long aLong) throws Exception {
                Log.d("zzf", "   subscriber2-> " + aLong);
            }
        };
        ConnectableObservable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10,TimeUnit.MILLISECONDS,Schedulers.computation())
                        .take(10)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                emitter.onNext(aLong);
                            }
                        });
            }
        }).observeOn(Schedulers.newThread()).publish();
        observable.connect();
        Observable<Long> observable1 = observable.refCount();
        Disposable disposable = observable1.subscribe(subscriber1);
        Disposable disposable1 = observable1.subscribe(subscriber2);
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 如果所有的订阅者／观察者都取消订阅了，则数据流停止：如果重新订阅，则重新开始数据流。
         * 如果不是所有的订阅者／观察者都取消了订阅 ，而只是部分取消，则部分的订阅者／观察者重新开始订阅时，不会从头开始数据流
         */
        disposable1.dispose();
        disposable.dispose();

        observable1.subscribe(subscriber1);
        observable1.subscribe(subscriber2);
    }

    /**
     * 使用 publish 操作符 ，可以让 Cold Observable 转换成 Hot Observable，它将原先 Observable
     * 转换 ConnectableObservable
     */
    private void cold_change_hot() {
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber1-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber2-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        ConnectableObservable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(10)
                        .subscribe(new io.reactivex.functions.Consumer<Long>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void accept(Long aLong) throws Exception {
                                /*if (aLong == 10){
                                    emitter.onComplete();
                                }*/
                                emitter.onNext(aLong);
                            }
                        });
            }
        }).observeOn(Schedulers.newThread()).publish();
        observable.connect();
        observable.subscribe(observer);
        observable.subscribe(observer1);
    }

    private void cold() {

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber1-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observer<Long> observer1 = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d("zzf", "subscriber2-> " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(Integer.MAX_VALUE)
                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 10){
                            emitter.onComplete();
                        }
                        emitter.onNext(aLong);
                    }
                });
            }
        }).observeOn(Schedulers.newThread());

        observable.subscribe(observer);
        observable.subscribe(observer1);
    }
}
