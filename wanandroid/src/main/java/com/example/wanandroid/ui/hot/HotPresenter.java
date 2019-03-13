package com.example.wanandroid.ui.hot;

import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.model.Api.ApiService;
import com.example.wanandroid.model.Api.ApiStore;
import com.example.wanandroid.model.Api.HttpObserver;
import com.example.wanandroid.model.constant.Constant;
import com.example.wanandroid.ui.Bean.BaseResp;
import com.example.wanandroid.ui.Bean.SearchHot;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotPresenter extends BasePresenter<HotContract.View> implements  HotContract.Presenter{
    @Inject
    public HotPresenter(){

    }

    @Override
    public void getHotWeb() {
        ApiStore.createApi(ApiService.class)
                .getHotWeb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<SearchHot>>>() {
                    @Override
                    public void onNext(BaseResp<List<SearchHot>> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHotWebOk(listBaseResp.getData());
                        } else if (listBaseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getHotWebErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHotWebErr(e.getMessage());
                    }
                });
    }
}
