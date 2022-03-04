package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : zhangzf
 * date   : 2021/2/24
 * desc   :当快速滚动列表时onPreload()没有执行，当慢慢滚动列表时onPrelaod()会执行多次。原因是RecyclerView并不保证每个表项出现时onScrolled()都会被调用，若滚动非常快，某个表项错过该回调是有可能发生的。
 * 这样我们可以放到adapter中进行操作，因为每次都会onBindViewHolder().
 *
 *
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0;
    private boolean loading = false;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        totalItemCount = mLinearLayoutManager.getItemCount();
        
        /*if (loading){
            if (totalItemCount > previousTotal){
                loading = false;
                previousTotal = totalItemCount;
            }
        }*/
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
            current_page++;
            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);
}
