package com.example.lettersidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;


import java.util.List;
import java.util.Objects;

/**
 * 有分类的head的ItemDecoration
 */

public class ContactHeadDecoration extends RecyclerView.ItemDecoration {
    private final int mBgColor1;
    private int mBgColor;
    private int mTextColor;
    private Context mContext;
    private List<ConstactBean> mData;
    private Paint mPaint;
    private Rect mBounds;
    private int mHeadHeight;
    private int mHeadHeightOffset;


    public ContactHeadDecoration(Context context, List<ConstactBean> data) {
        super();
        mContext = context;
        mData = data;
        mPaint = new Paint();
        mBounds = new Rect();
        mHeadHeight = CommonUtil.dp2px(context, 30);
        mHeadHeightOffset = CommonUtil.dp2px(context, 10);
        int textSize = CommonUtil.sp2px(context, 14);
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
        mBgColor = context.getResources().getColor(R.color.color_f5);
        mBgColor1 = context.getResources().getColor(R.color.color_white);
        mTextColor = context.getResources().getColor(R.color.color_33);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        if (mData.size() == 0) {
            return;
        }
        final int left = recyclerView.getPaddingLeft();
        final int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    drawHead(canvas, left, right, child, params, position);
                } else {
                    if (!mData.get(position).memo_py.equals(mData.get(position - 1).memo_py)) {
                        //字母不为空，并且不等于前一个，也要title
                        drawHead(canvas, left, right, child, params, position);
                    }
                }
            }
        }
    }

    /**
     * 绘制头部区域背景和文字
     */
    private void drawHead(Canvas canvas, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(mBgColor);

        int top = child.getTop() - params.topMargin - mHeadHeight - mHeadHeightOffset;
        canvas.drawRect(left, top, right, top + mHeadHeightOffset, mPaint);

        mPaint.setColor(mTextColor);

        //将文本放入到一个矩形框中，并测量文本的宽和高
        String text = mData.get(position).memo_py;
        if (text.length() == 2) {
            text = text.replace("Z", "").trim();
        }

        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        int baseLine = child.getTop() - params.topMargin - (mHeadHeight / 2 - mBounds.height() / 2);
        //drawText是在左下角比较接近的位置。
        canvas.drawText(text, CommonUtil.dp2px(mContext, 19), baseLine, mPaint);
    }

    /**
     * 绘制最上层的头部
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull final RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mData.size() == 0) {
            return;
        }
        int position = ((LinearLayoutManager) (Objects.requireNonNull(parent.getLayoutManager()))).findFirstVisibleItemPosition();
        //在搜索到没有的索引的时候position可能等于-1
        if (position == -1) {
            return;
        }

        String text = mData.get(position).memo_py;
        if (text.length() == 2) {
            text = text.replace("Z", "").trim();
        }

        View itemView = Objects.requireNonNull(parent.findViewHolderForLayoutPosition(position)).itemView;
        //Canvas是否位移过的标志
        boolean flag = false;
        if ((position + 1) < mData.size()) {
            //当前第一个可见的Item的字母索引，不等于其后一个item的字母索引，说明悬浮的View要切换了
            if (!text.equals(mData.get(position + 1).memo_py)) {
                //当第一个可见的item在屏幕中剩下的高度小于title的高度时，开始悬浮Title的动画
                if (itemView.getHeight() + itemView.getTop() < mHeadHeight - mHeadHeightOffset) {
                    canvas.save();
                    flag = true;
                    //下边的索引把上边的索引顶上去的效果
                    canvas.translate(0, itemView.getHeight() + itemView.getTop() - mHeadHeight + mHeadHeightOffset);

                    //头部折叠起来的视效（下边的索引慢慢遮住上边的索引）
                    /*canvas.clipRect(parent.getPaddingLeft(),
                            parent.getPaddingTop(),
                            parent.getRight() - parent.getPaddingRight(),
                            parent.getPaddingTop() + itemView.getHeight() + itemView.getTop());*/
                }
            }
        }

        mPaint.setColor(mBgColor1);
        canvas.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mHeadHeight, mPaint);
        mPaint.setColor(mTextColor);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float baseLine = parent.getPaddingTop() + mHeadHeight - (mHeadHeight / 2f - mBounds.height() / 2f);
        canvas.drawText(text, CommonUtil.dp2px(mContext, 19), baseLine, mPaint);
        if (flag) {
            canvas.restore();
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, mHeadHeight, 0, 0);
            } else {
                if (!mData.get(position).memo_py.equals(mData.get(position - 1).memo_py)) {
                    //字母不为空，并且不等于前一个，绘制头部
                    outRect.set(0, mHeadHeight + mHeadHeightOffset, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }
}
