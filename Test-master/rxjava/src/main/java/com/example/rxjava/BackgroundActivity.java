package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 后台执行耗时操作
 */
public class BackgroundActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        mButton = findViewById(R.id.btn_download);
        mTextView = findViewById(R.id.tv_download);
        
        initView();
    }

    private void initView() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stratDownload();
            }
        });
    }

    private void stratDownload() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100;i++){
                    if (i %20 == 0){
                        try {
                            Thread.sleep(500);
                        }catch (Exception e){
                            if (!emitter.isDisposed()){
                                emitter.onError(e);
                            }
                        }
                        emitter.onNext(i);
                    }
                }
                emitter.onComplete();
            }
        });
        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.d("BackgroundActivity", "onNext=" + integer);
                mTextView.setText("Current Progress=" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("BackgroundActivity", "onError=" + e);
                mTextView.setText("Download Error");
            }

            @Override
            public void onComplete() {
                Log.d("BackgroundActivity", "onComplete");
                mTextView.setText("Download onComplete");
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
