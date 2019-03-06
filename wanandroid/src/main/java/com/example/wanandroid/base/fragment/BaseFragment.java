package com.example.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.base.app.MyApplication;
import com.example.wanandroid.base.presenter.AbsPresenter;
import com.example.wanandroid.base.view.AbstractView;
import com.example.wanandroid.di.component.DaggerFragmentComponent;
import com.example.wanandroid.di.component.FragmentComponent;
import com.example.wanandroid.di.module.FragmentModule;
import com.example.wanandroid.event.MessageEvent;
import com.example.wanandroid.util.NetworkBroadcastReceiver;
import com.example.wanandroid.util.network.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment<T extends AbsPresenter> extends SupportFragment implements AbstractView,NetworkBroadcastReceiver.NetEvent{
    public View rootView;
    protected Activity activity;
    protected MyApplication mContext;
    protected FragmentComponent mFragmentComponent;
    private int netMobile;

    @Inject
    protected T mPresenter;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        activity = getActivity();
        mContext = MyApplication.getInstance();
        initFragmentComponent();
        initInjector();
        onViewCreat();
        initBind(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initUI();
        initData();
    }

    protected abstract void initUI();
    protected abstract void initData();

    public void initBind(View view){
        ButterKnife.bind(this,view);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        NetUtils.init(MyApplication.getInstance());
    };

    protected void initInjector() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected void onViewCreat(){
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }
    public abstract int getLayoutResID();

    @Override
    public void onDestroy() {
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    /**
     * 设置可见
     */
    @Override
    public void setVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置隐藏
     */
    @Override
    public void setInVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置不可见
     */
    @Override
    public void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNeteaseLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

}
