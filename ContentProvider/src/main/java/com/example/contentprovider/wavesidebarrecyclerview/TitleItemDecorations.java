package com.example.contentprovider.wavesidebarrecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

public class TitleItemDecorations extends RecyclerView.ItemDecoration {
    private List<SortModelBean> datas;
    private Paint mPaint;
    private Rect rect;
    private int mTitleHeight;
    private static int TITLE_BG_COLOR = Color.parseColor("#FFDFDFDF");
    private static int TITLE_TEXT_COLOR = Color.parseColor("#FF000000");
    private static int mTitleTextSize;

    public TitleItemDecorations(Context context, List<SortModelBean> datas) {
        super();
        this.datas = datas;
        mPaint = new Paint();
        rect = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setAntiAlias(true);
    }


    //onDraw是绘制每个item上面的title
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //getLayoutPosition
            //返回布局中最新的计算位置，和用户所见到的位置一致，当做用户输入（例如点击事件）的时候考虑使用
            //getAdapterPosition
            //返回数据在Adapter中的位置（也许位置的变化还未来得及刷新到布局中），当使用Adapter的时候（例如调用Adapter的notify相关方法时）考虑使用
            //getViewLayoutPosition:return mViewHolder.getLayoutPosition();所以跟getLayoutPosition一样。
            int position = layoutParams.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    drawTitle(c, left, right, child, layoutParams, position);
                } else {
                    //字母部位空，并且不等于前一个
                    if (null != datas.get(position).getSortLetters() && !datas.get(position).getSortLetters().equals(datas.get(position - 1).getSortLetters())) {
                        drawTitle(c, left, right, child, layoutParams, position);
                    }
                }
            }
        }
    }

    private void drawTitle(Canvas c, int left, int right, View child, RecyclerView.LayoutParams layoutParams, int position) {
        mPaint.setColor(TITLE_BG_COLOR);
        int top = child.getTop() - layoutParams.topMargin - mTitleHeight;
        int bottom = child.getBottom() - layoutParams.topMargin;
        c.drawRect(left, top, right, bottom, mPaint);

        mPaint.setColor(TITLE_TEXT_COLOR);
        mPaint.getTextBounds(datas.get(position).getSortLetters(), 0, datas.get(position).getSortLetters().length(), rect);
        c.drawText(datas.get(position).getSortLetters(),
                child.getPaddingLeft(),
                child.getTop() - layoutParams.topMargin - (mTitleHeight / 2 - rect.height() / 2), mPaint);
    }

    //悬浮效果，就是item往上滑动时，到顶部时，title保持不变，item可以滑动。
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (position == -1) {
            return;
        }
        String letter = datas.get(position).getSortLetters();
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
        /*if (parent.findViewHolderForAdapterPosition(position) != null){
            child = parent.findViewHolderForAdapterPosition(position).itemView;
        }*/
        boolean flag = false;
        if ((position + 1) < datas.size()) {
            if (null != letter && !letter.equals(datas.get(position + 1).getSortLetters())) {
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    c.save();
                    flag = true;
                    /**
                     * 下边的索引把上边的索引顶上去的效果
                     */
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);

                    /**
                     * 头部折叠起来的视效（下边的索引慢慢遮住上边的索引）
                     */
                    /*c.clipRect(parent.getPaddingLeft(),
                            parent.getPaddingTop(),
                            parent.getRight() - parent.getPaddingRight(),
                            parent.getPaddingTop() + child.getHeight() + child.getTop());*/
                }
                mPaint.setColor(TITLE_BG_COLOR);
                c.drawRect(parent.getPaddingLeft(),
                        parent.getPaddingTop(),
                        parent.getRight() - parent.getPaddingRight(),
                        parent.getPaddingTop() + mTitleHeight, mPaint);
                mPaint.setColor(TITLE_TEXT_COLOR);
                mPaint.getTextBounds(letter, 0, letter.length(), rect);
                c.drawText(letter, child.getPaddingLeft(),
                        parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - rect.height() / 2),
                        mPaint);
                if (flag) {
                    c.restore();//恢复画布到之前保存的状态
                }
            }
        }
    }

    //getItemOffsets设置分割线
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            //等于0的时候绘制title
            if (position == 0) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                if (null != datas.get(position).getSortLetters() &&
                        !datas.get(position).getSortLetters().equals(datas.get(position - 1).getSortLetters())) {
                    //字母不为空，并且不等于前一个，绘制title
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }
}
