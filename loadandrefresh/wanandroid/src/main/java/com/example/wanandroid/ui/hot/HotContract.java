package com.example.wanandroid.ui.hot;

import com.example.wanandroid.base.presenter.AbsPresenter;
import com.example.wanandroid.base.view.AbstractView;

import java.util.List;

public class HotContract {
    public interface View extends AbstractView{
        void getHotWebOk(List<SearchHotBean> data);
        void getHotWebErr(String info);
    }

    public interface Presenter extends AbsPresenter<HotContract.View>{
        void getHotWeb();
    }
}
