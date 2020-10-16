package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.example.customview.ruler.RulerView;

import java.util.ArrayList;
import java.util.List;

public class VerticalTextIndicatorView extends View {

    private Paint mDotPaint,mTextPaint;
    /**
     * 需要传入的文字
     */
    private List<String> mTextList;
    /**
     * 文字的高度
     */
    private int mTextHeight;
    /**
     * 竖直方向文字间隔30px
     */
    private float mSpacing = 30;

    /**
     * 第一刻度位置距离当前位置的偏移量，一定小于0
     */
    private float mOffsetStart = 0;

    /**
     * 辅助计算滑动，主要用于惯性计算
     */
    private Scroller mScroller;

    /**
     * 跟踪用户手指滑动速度
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 定义惯性作用的最小速度
     */
    private float minVelocityX;
    /**
     * 用户手指按下控件滑动时的初始位置坐标
     */
    float downY;

    /**
     * 当前手指移动的距离
     */
    float movedY;

    /**
     * 圆点选中的文字的回调
     */
    OnTextSelectListener listener;

    private Context mContext;

    public interface OnTextSelectListener {
        void onNumSelect(String selectedText);
    }

    public VerticalTextIndicatorView(Context context) {
        this(context,null);
        this.mContext = context;
        init(context);
    }

    public VerticalTextIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {

        mDotPaint = new Paint();
        mDotPaint.setColor(Color.parseColor("#3700B3"));
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        mTextList = new ArrayList<>();
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        minVelocityX = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTextList != null && mTextList.size() > 0 ){
            for (int i = 0; i < mTextList.size(); i++) {
                float textTop = mOffsetStart + movedY + i * mSpacing;

            }
        }
    }

    /**
     * 给外界提供设置文字的入口
     * @param list
     */
    public void setTextList(List<String> list){
        this.mTextList = list;
        invalidate();
    }
}





