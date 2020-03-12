package com.example.roomdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.roomdemo.LogUtils;
import com.example.roomdemo.R;
import com.example.roomdemo.data.DBInstance;
import com.example.roomdemo.data.Dog;
import com.example.roomdemo.databinding.ActivityRxjavaBinding;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WithRxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRxjavaBinding activityRxjavaBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        activityRxjavaBinding = DataBindingUtil.setContentView(this,R.layout.activity_rxjava);
        activityRxjavaBinding.setOnClickListener(this);
        //activityRxjavaBinding.txtAll.setText("当前狗狗总数" + DBInstance.getInstance().getDogDao().getAll().size());
        DBInstance.getInstance().getDogDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Dog>>() {
                    @Override
                    public void accept(List<Dog> dogs) throws Exception {
                        activityRxjavaBinding.txtAll.setText("当前狗狗总数" + dogs.size());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert_completable:
                Completable.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        Dog dog = new Dog();
                        return DBInstance.getInstance().getDogDao().insert(dog);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.i("使用Completable插入数据", "onComplete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.i("使用Completable插入数据", "onError");
                            }
                        });
                break;
            case R.id.btn_insert_single:
                Single.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        Dog dog = new Dog();
                        return DBInstance.getInstance().getDogDao().insert(dog);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<Long>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<Long> longs) {
                                for (Long data : longs) {
                                    LogUtils.i("使用Single插入数据", "onSuccess ==> " + data);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                break;
            case R.id.btn_insert_maybe:
                Maybe.fromCallable(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        Dog dog = new Dog();
                        return DBInstance.getInstance().getDogDao().insert(dog);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MaybeObserver<List<Long>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<Long> longs) {
                                for (Long data : longs) {
                                    LogUtils.i("使用Maybe插入数据", "onSuccess ==> " + data);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.i("使用Maybe插入数据", "onError");
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.i("使用Maybe插入数据", "onComplete");
                            }
                        });
                break;
            case R.id.btn_with_livedata:
                DBInstance.getInstance().getDogDao().getToLiveData(2, 12).observe(this, new Observer<List<Dog>>() {
                    @Override
                    public void onChanged(List<Dog> dogs) {
                        Toast.makeText(WithRxJavaActivity.this,"查出来的当前size长度 ==> " + dogs.size(),Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}
