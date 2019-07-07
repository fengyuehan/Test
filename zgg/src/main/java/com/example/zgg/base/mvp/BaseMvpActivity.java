package com.example.zgg.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.zgg.MyApplication;
import com.example.zgg.dragger.component.ActivityComponent;
import com.example.zgg.dragger.component.DaggerActivityComponent;
import com.example.zgg.dragger.module.ActivityModule;
import com.example.zgg.dragger.scope.ApplicationComponent;
import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class BaseMvpActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

    public  T mPresenter;
    protected KProgressHUD mKProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initInject();

    protected abstract int getLayout();

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .applicationComponent((ApplicationComponent) MyApplication.getAppConponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


}
