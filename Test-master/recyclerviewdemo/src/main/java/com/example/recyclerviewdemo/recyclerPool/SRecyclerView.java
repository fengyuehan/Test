package com.example.recyclerviewdemo.recyclerPool;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;

public class SRecyclerView extends RecyclerView {
    public ArrayListWrapper<ViewHolder> mAttachedRecord;
    public ArrayListWrapper<ViewHolder> mCachedRecord;
    public ArrayListWrapper<ViewHolder> mChangedRecord;

    public interface onLayoutListener {
        void beforeLayout();

        void afterLayout();
    }

    private onLayoutListener onLayoutListener;

    public void setOnLayoutListener(SRecyclerView.onLayoutListener onLayoutListener) {
        this.onLayoutListener = onLayoutListener;
    }

    public SRecyclerView(Context context) {
        this(context, null);
    }

    public SRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttachedRecord = new ArrayListWrapper<>();
        mCachedRecord = new ArrayListWrapper<>();
        mAttachedRecord.setKey("mAttachedScrap");
        mCachedRecord.setKey("mCachedViews");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        onLayoutListener.beforeLayout();
        super.onLayout(changed, l, t, r, b);
        onLayoutListener.afterLayout();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    public void setAllCache() {
        try {
            Field mRecycler =
                    Class.forName("androidx.recyclerview.widget.RecyclerView").getDeclaredField("mRecycler");
            mRecycler.setAccessible(true);
            RecyclerView.Recycler recyclerInstance =
                    (RecyclerView.Recycler) mRecycler.get(this);

            Class<?> recyclerClass = Class.forName(mRecycler.getType().getName());
            Field mAttachedScrap = recyclerClass.getDeclaredField("mAttachedScrap");
            mAttachedScrap.setAccessible(true);
            mAttachedScrap.set(recyclerInstance, mAttachedRecord);
            Field mCacheViews = recyclerClass.getDeclaredField("mCachedViews");
            mCacheViews.setAccessible(true);
            mCacheViews.set(recyclerInstance, mCachedRecord);
            Field mChangeScrap = recyclerClass.getDeclaredField("mChangedScrap");
            mChangeScrap.setAccessible(true);
            mChangeScrap.set(recyclerInstance,mChangedRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
