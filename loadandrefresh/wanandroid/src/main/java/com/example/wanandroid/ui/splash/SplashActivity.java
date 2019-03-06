package com.example.wanandroid.ui.splash;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseActivity;
import com.example.wanandroid.base.app.MyApplication;
import com.example.wanandroid.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTextView;
    private Disposable timer;
    private int time = 3;
    private boolean isIn;

    @Override
    protected void initData() {
        timer = Observable.interval(0,1, TimeUnit.SECONDS)
                .take(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (time - aLong == 1 && !timer.isDisposed()) {    //aLong -> 0,1,2
                            jump();
                        }
                    }
                });
    }

    private void jump() {
        if (isIn){
            return;
        }
        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @Override
    protected void initUI() {
        mTextView = findViewById(R.id.tv_jump);
        mTextView.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_jump:
                if (!timer.isDisposed()) {
                    timer.dispose();
                }
                jump();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!timer.isDisposed() && timer != null) {
            timer.dispose();
            timer = null;
        }
    }
}
