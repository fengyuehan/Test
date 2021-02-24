package com.example.rxjava.merge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rxjava.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * author : zhangzf
 * date   : 2021/2/22
 * desc   :
 */
public class MerageActivity extends AppCompatActivity {
    TextView tv_merge,tv_concat,tv_zip;

    /**
     * merge是按时间线来进行排列
     * concat是等前一个的observable发送完全，在进行下一个observable的发送
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        tv_merge = findViewById(R.id.tv_merge);
        tv_concat = findViewById(R.id.tv_concat);
        tv_zip = findViewById(R.id.tv_zip);
        tv_merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mergeData();
            }
        });
        tv_concat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concatData();
            }
        });
        tv_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipData();
            }
        });
    }

    /**
     * 合并多个数据流，然后发送 (Emit) 最终合并的数据。
     */
    @SuppressLint("CheckResult")
    private void zipData() {
        Observable.zip(Observable.just(2, 4), Observable.just(3, 9, 2), new BiFunction<Integer, Integer, Integer>() {
            @NonNull
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("zzf","--------zip--------" + integer);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void concatData() {
        Observable.concat(Observable.intervalRange(0,6,1,1, TimeUnit.MILLISECONDS),Observable.intervalRange(8,6,2,3, TimeUnit.MILLISECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("zzf","--------concat--------" + aLong);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void mergeData() {
        Observable.merge(Observable.intervalRange(0,6,1,1, TimeUnit.MILLISECONDS),Observable.intervalRange(8,6,2,3, TimeUnit.MILLISECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("zzf","--------merge--------" + aLong);
                    }
                });
    }
}
