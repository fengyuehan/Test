package com.example.wanandroid.ui.hot;

import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseRootActivity;

import java.util.List;

public class HotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View{
    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    public void getHotWebOk(List<SearchHotBean> data) {

    }

    @Override
    public void getHotWebErr(String info) {

    }
}
