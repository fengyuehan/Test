package com.example.dell.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

public class DividerGridItemDecoration  extends RecyclerView.ItemDecoration{
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int space;
    public DividerGridItemDecoration(Context context){
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    public DividerGridItemDecoration(Context context,int space) {
        this.space = space;
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent){

        int childCount = parent.getChildCount();
        for (int i = 0;i < childCount; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin + mDivider.getIntrinsicHeight();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,right);
            mDivider.draw(c);
        }
    }

    private int getSpanCount(RecyclerView parent){
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    //列
    private boolean isLastColum(RecyclerView parent,int pos,int spanCount,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            if ((pos + 1)% spanCount == 0){
                return true;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if ((pos + 1) % spanCount == 0){
                    return true;
                }
            }else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount){
                    return true;
                }
            }
        }
        return false;
    }

    //行
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount){
                return true;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount){
                    return true;
                }
            }else {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

/*    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = layoutParams.getSpanIndex();
        if (spanIndex % 2 == 0){
            outRect.left = space;
            outRect.right = space / 2;
        }else {
            outRect.left = space / 2;
            outRect.right = space;
        }
        outRect.top = space;
    }*/

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

       /* StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) parent.getLayoutParams();
        int spanIndex = layoutParams.getSpanIndex();
        if (spanIndex % 2 == 0){
            outRect.left = space;
            outRect.right = space / 2;
        }else {
            outRect.left = space / 2;
            outRect.right = space;
        }
        outRect.top = space;*/
        if (isLastRaw(parent,itemPosition,spanCount,childCount)){
            outRect.set(0,0,mDivider.getMinimumWidth(),0);

        }else if (isLastColum(parent,itemPosition,spanCount,childCount)){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mDivider.getMinimumWidth(),mDivider.getIntrinsicWidth());
        }
    }
}
