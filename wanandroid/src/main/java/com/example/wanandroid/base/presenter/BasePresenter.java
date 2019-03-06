package com.example.wanandroid.base.presenter;

import com.example.wanandroid.base.view.AbstractView;

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {
    protected T mView;
    private int currentPage;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null){
            this.mView = null;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
