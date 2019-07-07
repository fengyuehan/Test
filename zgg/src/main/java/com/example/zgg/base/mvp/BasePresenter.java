package com.example.zgg.base.mvp;

public interface BasePresenter<T extends BaseView> {

    /**
     * attachView 绑定view
     *
     * @param view
     */
    void attachView(T view);

    /**
     * detachView 解除绑定
     */
    void detachView();
}
