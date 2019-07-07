package com.example.zgg.base.mvp;

public class BaseMvpPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null){
            mView = null;
        }
    }
}
