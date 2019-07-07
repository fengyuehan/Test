package com.example.zgg.base.mvc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbase.utils.NetUtils;
import com.androidbase.utils.ToastUtils;
import com.example.zgg.base.mvp.BaseView;
import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class BaseFragment extends Fragment implements BaseView {
    protected View mRootView;
    protected KProgressHUD mKProgressHUD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(getLayout(),container,false);
        }
        //这样做的目的是为了用黄油刀绑定
        initView(mRootView);
        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!NetUtils.isConnected()){
            onNetError();
        }
    }

    protected abstract void initData();

    protected abstract void initView(View mRootView);

    protected abstract int getLayout();

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(getActivity());
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }

    @Override
    public void onFail() {
        ToastUtils.showLongToast("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showLongToast("请检查网络是否连接");
    }


    @Override
    public void onReLoad() {

    }

    @Override
    public void onSucess() {

    }
}
