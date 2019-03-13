package com.example.wanandroid.base.activity;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.wanandroid.base.app.MyApplication;
import com.example.wanandroid.base.presenter.AbsPresenter;
import com.example.wanandroid.base.view.AbstractView;
import com.example.wanandroid.di.component.ActivityComponent;
import com.example.wanandroid.di.component.DaggerActivityComponent;
import com.example.wanandroid.di.module.ActivityModule;
import com.example.wanandroid.event.MessageEvent;
import com.example.wanandroid.util.NetworkBroadcastReceiver;
import com.example.wanandroid.util.app.DisplayUtil;
import com.example.wanandroid.util.network.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView,NetworkBroadcastReceiver.NetEvent {
    protected MyApplication mContext;
    protected BaseActivity mBaseActivity;
    protected ActivityComponent mActivityComponent;
    private int netMobile;
    public static NetworkBroadcastReceiver.NetEvent eventActivity;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DisplayUtil.setCustomDensity(this,MyApplication.getInstance());
        setContentView(getLayoutId());
        mContext = MyApplication.getInstance();
        mBaseActivity = this;
        eventActivity = this;
        initActivityComponent();
        initBind();
        initInject();
        onViewCreated();
        //initToolbar();
        initUI();
        initData();
    }

    protected abstract void initData();

    protected abstract void initUI();

    /*protected  void initToolbar(){

    }*/

    protected  void onViewCreated(){
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    protected  void initInject(){

    }

    public void initBind() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        NetUtils.init(MyApplication.getInstance());
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract int getLayoutId() ;

    @Override
    protected void onDestroy() {
        if (mPresenter!=null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(MessageEvent event) {
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile =  netMobile;
        isNetConnect();
    }

    private boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_WIFI){
            return true;
        }else if (netMobile == NetUtils.NETWORK_MOBILE){
            return true;
        }else if (netMobile == NetUtils.NETWORK_NONE){
            return false;
        }
        return false;
    }

    @Override
    public void setVisible(View... views) {
        for (View view: views){
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for (View view:views){
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setGone(View... views) {
        for (View view:views){
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showNeteaseLoading() {

    }
}
