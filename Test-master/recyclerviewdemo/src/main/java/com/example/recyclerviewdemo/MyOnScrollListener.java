package com.example.recyclerviewdemo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLayoutManager;
    private ItemBean itemBean;
    private int mItemWidth;
    private int mItemMargin;

    public MyOnScrollListener(LinearLayoutManager mLayoutManager, ItemBean itemBean) {
        this.mLayoutManager = mLayoutManager;
        this.itemBean = itemBean;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                int offset = recyclerView.computeHorizontalScrollOffset();
                itemBean.scrollPosition = mLayoutManager.findFirstVisibleItemPosition() < 0 ? itemBean.scrollPosition:
                        mLayoutManager.findFirstVisibleItemPosition() +1;
                if (mItemWidth <= 0) {
                    View item = mLayoutManager.findViewByPosition(itemBean.scrollPosition);
                    if (item != null) {
                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) item.getLayoutParams();
                        mItemWidth = item.getWidth();
                        mItemMargin = layoutParams.rightMargin;
                    }
                }
                if (offset > 0 && mItemWidth > 0) {
                    //offset % mItemWidth：得到当前position的滑动距离
                    //mEntity.scrollPosition * mItemMargin：得到（0至position）的所有item的margin
                    //用当前item的宽度-所有margin-当前position的滑动距离，就得到offset。
                    itemBean.scrollOffset = mItemWidth - offset % mItemWidth + itemBean.scrollPosition * mItemMargin;
                }
                break;
        }
    }
}
