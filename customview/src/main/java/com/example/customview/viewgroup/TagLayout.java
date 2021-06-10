package com.example.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2021/5/21
 * desc   :
 */
public class TagLayout extends ViewGroup {
    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();
    private BaseAdapter baseAdapter;

    public void setAdapter(BaseAdapter baseAdapter)  {
        if (baseAdapter == null){
            throw new NullPointerException("不能为空指针");
        }
        removeAllViews();
        baseAdapter = null;
        this.baseAdapter = baseAdapter;
        int count = baseAdapter.getCount();
        for (int i = 0; i < count; i++){
            View view = baseAdapter.getView(i,this);
            addView(view);
        }
    }

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modelWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modelHeight = MeasureSpec.getMode(heightMeasureSpec);
        //子View的数量
        int childCount = getChildCount();
        //自己测量的宽高
        int width = 0;
        int height = 0;
        //每一行的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0; i < childCount;i++){
            View childView = getChildAt(i);
            //必须先测量才能拿到测量宽度和高度
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = params.leftMargin + params.rightMargin + childView.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
            int childHeight = params.bottomMargin + params.topMargin + childView.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
            if (lineWidth + childWidth > sizeWidth){
                //宽度超出，需要换行
                width = Math.max(width,lineWidth);
                //换行高度加上子View的高度
                height += childHeight;
                //换行之后，每一行的高度为子view的高度，宽度为子view的宽度
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else {
                //不换行，宽度相加，高度则为子view的最大高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            //最后一个view
            if (i == childCount - 1){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
            setMeasuredDimension(modelWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                    modelHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        //将所有的view保存下来
        //一行的所有view
        List<View> mViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View view = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = getMeasuredWidth();
            int childHeight = getMeasuredHeight();
            if (childWidth + lineWidth + params.leftMargin + params.rightMargin + getPaddingRight() + getPaddingLeft() > width){

                //mAllViews保存所有行的View
                mAllViews.add(mViews);
                //重置宽高
                lineWidth = 0;
                lineHeight = childHeight + params.topMargin + params.bottomMargin + getPaddingBottom() + getPaddingTop();
                mLineHeight.add(lineHeight);
                //这里必须重置，相当于重新记录新的一行
                mViews = new ArrayList<>();
            }
            lineWidth += childWidth + params.leftMargin + params.rightMargin + getPaddingRight() + getPaddingLeft();
            lineHeight = Math.max(lineHeight,childHeight + params.topMargin + params.bottomMargin + getPaddingBottom() + getPaddingTop());
            mViews.add(view);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(mViews);
        int left = 0;
        int top = 0;

        int line = mAllViews.size();
        for (int i = 0; i < line ; i++){
            mViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < mViews.size(); j++){
                View view = mViews.get(j);
                //view不可见时
                if (view.getVisibility() == GONE){
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                int cLeft = left + params.leftMargin + getPaddingLeft();
                int cTop = top + params.topMargin + getPaddingTop() ;
                int cRight = cLeft + view.getMeasuredWidth();
                int cBottom = cTop + view.getMeasuredHeight();

                view.layout(cLeft,cTop,cRight,cBottom);
                left += view.getMeasuredWidth() + params.leftMargin + params.rightMargin+ getPaddingRight() + getPaddingLeft() ;
            }
            //换行之后重置
            left = 0;
            top += lineHeight;
        }
    }
}
