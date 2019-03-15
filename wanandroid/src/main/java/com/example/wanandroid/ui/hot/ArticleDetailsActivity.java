package com.example.wanandroid.ui.hot;

import android.support.v7.app.AppCompatActivity;

import com.example.wanandroid.base.activity.BaseRootActivity;

public class ArticleDetailsActivity extends BaseRootActivity<ArticleDetailsPresenter> implements ArticleDetailsContact.View{

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
