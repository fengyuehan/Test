package com.example.smartrefreshlayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public abstract class BaseRefreshActivity<T> extends BaseActivity{

    protected int mPageIndex = 1;
    protected String mSize = "20";
    protected SmartRefreshLayout mRefreshLayout;
    private BaseQuickAdapter mAdapter;
    private List<T> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSmartRefreshLayout();
    }

    private void initSmartRefreshLayout() {
        mRefreshLayout = setRefreshLayout();
        mAdapter = setRefreshAdapter();
        setSmartRefreshLayoutListener();
    }

    private void setSmartRefreshLayoutListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //mAdapter.removeAllFooterView();
                mPageIndex++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageIndex = 1;
                getData();
            }
        });
    }

    protected void refreshOrLoadMoreSuccess(List<T> data){
        mData = data;
        if (mPageIndex == 1){
            /**
             * mPageIndex == 1表示是下拉刷新
             */
            if (data == null){
                mRefreshLayout.finishRefresh();
            }else {
                /**
                 * 这里不能用setNewData，会导致最终上拉的只有一页的数据
                 */
                mAdapter.addData(data);
                if (data.size() < Integer.parseInt(mSize)){
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }else {
                    mRefreshLayout.finishRefresh();
                }
            }
        }else {
            if (data == null){
                mRefreshLayout.finishLoadMore();
            }else {
                /**
                 * 这里不能用setNewData，会导致最终上拉的只有一页的数据
                 */
                mAdapter.addData(data);
                if (data.size() < Integer.parseInt(mSize)) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }

    /**
     * 刷新或者加载失败
     */
    protected void refreshOrLoadMoreFail(){
        if (mPageIndex == 1){
            mRefreshLayout.finishRefresh(false);
        }else {
            mRefreshLayout.finishLoadMore(true);
        }
    }

    protected void refreshOrLoadMoreError(){
        if (mPageIndex > 1){
            /**
             * 加载更多的时候，请求失败，此时mPageIndex已经+1，所以失败时需要回退
             */
            mPageIndex--;
            mRefreshLayout.finishLoadMore(false);
        }else {
            mRefreshLayout.finishRefresh(false);
        }
    }
    protected abstract void getData();

    protected abstract BaseQuickAdapter setRefreshAdapter();

    protected abstract SmartRefreshLayout setRefreshLayout();
}
