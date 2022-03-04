package com.example.rxjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

public class BufferActivity extends AppCompatActivity {
    private PublishSubject<Double> mPublishSubject;
    private CompositeDisposable mCompositeDisposable;
    private TextView textView;
    SourceHandler mSourceHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        textView = findViewById(R.id.tv);
        mCompositeDisposable = new CompositeDisposable();
        mPublishSubject = PublishSubject.create();
        DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> doubles) {
                double result = 0;
                if (doubles.size() > 0){
                    for (Double o:doubles){
                        result += o;
                    }
                    result = result / doubles.size();
                }
                Log.d("BufferActivity", "更新平均温度：" + result);
                textView.setText("过去3秒收到了" + doubles.size() + "个数据， 平均温度为：" + result);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //buffer(int count)
        //以列表的形式发出非重叠缓冲区，每个缓冲区为一组发出。
        //例如：[1,2,3,4,5,6,7,8,9],buffer(3)，则输出形式为：
        //123,456,789
        //buffer(count, skip)
        //从第一项开始，取count个项创建缓存，从第一项开始skip个项之后再取count个项创建缓存。根据count和skip的值，这些缓冲区可能重叠（多个缓冲区可能包含相同的项），也可能有间隙（源Observable发出的项不在任何缓冲区中）。
        //1.count = skip 非重叠不遗漏缓冲，等同于buffer(int count)
        //2.count > skip 重叠不遗漏缓冲.buffer(3,2):123,345,567,789
        //3.count < skip 非重叠遗漏缓冲.buffer(2,3):12,45,78
        //buffer(long timespan, TimeUnit unit)
        //时间片段缓存，等时发送缓存区的一组数据。
        //按时间缓存，元素的个数取决于这段时间里面总共发出的个数，所以缓存集合的size是不固定的，可能为空，也可能很多。
        mPublishSubject.buffer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
        //开始测量温度。
        mSourceHandler = new SourceHandler();
        mSourceHandler.sendEmptyMessage(0);
    }

    private class  SourceHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            double temperature = Math.random()*25 +5;
            updateTemperature(temperature);
            //循环地发送。
            sendEmptyMessageDelayed(0, 250 + (long) (250 * Math.random()));
        }
    }

    private void updateTemperature(double temperature) {
        Log.d("BufferActivity", "温度测量结果：" + temperature);
        mPublishSubject.onNext(temperature);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSourceHandler.removeCallbacksAndMessages(null);
        mCompositeDisposable.clear();
    }
}
