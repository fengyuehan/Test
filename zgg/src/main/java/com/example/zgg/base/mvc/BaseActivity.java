package com.example.zgg.base.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidbase.utils.NetUtils;
import com.androidbase.utils.ToastUtils;
import com.example.zgg.base.mvp.BaseView;
import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    //所有的基类的都用protected修饰
    protected KProgressHUD mKProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetUtils.isConnected()){
            onNetError();
        }
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout() ;

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(this);
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
    public void onSucess() {

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
