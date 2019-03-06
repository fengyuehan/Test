package com.example.wanandroid.base.presenter;

import com.example.wanandroid.base.view.AbstractView;

public interface AbsPresenter<T extends AbstractView> {
    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}
