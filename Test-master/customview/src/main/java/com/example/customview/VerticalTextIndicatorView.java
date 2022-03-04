package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.customview.ruler.RulerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private int mSpacing = 60;

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
    private float minVelocityY;
    /**
     * 用户手指按下控件滑动时的初始位置坐标
     */
    private float downY;

    /**
     * 当前手指移动的距离
     */
    private float movedY;

    /**
     * 最长的文字宽度
     */
    private int maxTextWidth;

    /**
     * 指示器半径
     */
    int indicatorRadius = mSpacing / 4;

    /**
     * 控件的高度
     */
    int height;

    /**
     * 圆点选中的文字的回调
     */
    OnTextSelectListener listener;

    public void setListener(OnTextSelectListener listener) {
        this.listener = listener;
    }

    public interface OnTextSelectListener {
        void onNumSelect(String selectedText);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public VerticalTextIndicatorView(Context context) {
        this(context,null);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public VerticalTextIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init(Context context) {

        mDotPaint = new Paint();
        mDotPaint.setColor(Color.parseColor("#3700B3"));
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(60);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        mTextList = new ArrayList<>();
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        minVelocityY = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        //mTextHeight = height/8;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result=600;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 200;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTextList != null && mTextList.size() > 0 ){
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            //Log.e("zzf","top = " + top + "---------" + "bottom = "+bottom);
            int size = mTextList.size();
            float total = (size)*(-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent);
            float offset = total/2 -bottom;
            //Log.e("zzf","total = " + total + "---------" + "offset = "+offset);
            for (int i = 0; i < size; i++) {
                float textTop = -i*(-top+bottom)+offset + 125 + mOffsetStart + movedY;
                //Log.e("zzf","textTop = " + textTop);
                canvas.drawText(mTextList.get(size - i-1),0,textTop,mTextPaint);
            }
        }

        /**
         * 绘制原点
         */
        int indicatorX = indicatorRadius + maxTextWidth+20;
        int indicatorY = indicatorRadius /*+ mTextHeight*/ + 20;
        canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, mDotPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 强制终止在某一个值
                 */
                mScroller.forceFinished(true);
                downY = event.getY();
                movedY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("zzf","mOffsetStart + movedY = " + (mOffsetStart + movedY));
                Log.e("zzf","mTextList.size()*mSpacing = " + mTextList.size()*mSpacing);
                movedY = event.getX() - downY;
                /*if (mOffsetStart + movedY > 0){
                    mOffsetStart = 0;
                    movedY = 0;
                }else*/ if (mOffsetStart + movedY < -mTextList.size()*mSpacing){
                    mOffsetStart = -mTextList.size()*mSpacing;
                    movedY = 0;
                }
                if (listener != null){
                    if(getSelectedNum() >= mTextList.size()){
                        listener.onNumSelect(mTextList.get(mTextList.size() -1));
                    }else {
                        listener.onNumSelect(mTextList.get(getSelectedNum()));
                    }
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mOffsetStart + movedY <= 0 && mOffsetStart + movedY >= -mTextList.size()*mSpacing){
                    mOffsetStart = mOffsetStart + movedY;
                    movedY = 0;
                    /**
                     * 取整
                     */
                    mOffsetStart = ((int) (mOffsetStart / mSpacing)) * mSpacing;
                }/*else if (mOffsetStart + movedY > 0){
                    mOffsetStart = 0;
                    movedY = 0;
                }*/else {
                    mOffsetStart = -mTextList.size()*mSpacing;
                    movedY = 0;
                }
                if (listener != null){
                    if(getSelectedNum() >= mTextList.size()){
                        listener.onNumSelect(mTextList.get(mTextList.size() -1));
                    }else {
                        listener.onNumSelect(mTextList.get(getSelectedNum()));
                    }
                }
                //计算当前手指放开的滑动速率
                mVelocityTracker.computeCurrentVelocity(500);
                /**
                 * 得到Y轴方向的速度
                 */
                float velocityY = mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > minVelocityY){
                    /**
                     * 惯性滑动
                     */
                    mScroller.fling(0, 0, 0, (int) velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                }
                postInvalidate();
                break;
        }
        return true;

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            if (mScroller.getCurrY() == mScroller.getFinalY()){
                //表示停止
                if (mOffsetStart + movedY <= 0 && mOffsetStart + movedY >= -mTextList.size()*mSpacing){
                    mOffsetStart = mOffsetStart + movedY;
                    movedY = 0;
                    /**
                     * 取整
                     */
                    mOffsetStart = ((int) (mOffsetStart / mSpacing)) * mSpacing;
                }else if (mOffsetStart + movedY > 0){
                    mOffsetStart = 0;
                    movedY = 0;
                }else {
                    mOffsetStart = -mTextList.size()*mSpacing;
                    movedY = 0;
                }
            }else {
                //表示没有停止，继续惯性滑动
                movedY = mScroller.getCurrY()-mScroller.getStartY();
                /**
                 * 边界控制
                 */
                if (mOffsetStart +movedY >= 0){
                    movedY = 0;
                    mOffsetStart = 0;
                    mScroller.forceFinished(true);
                }else if (mOffsetStart + movedY <= -mTextList.size()*mSpacing){
                    movedY = 0;
                    mOffsetStart = -mTextList.size()*mSpacing;
                    mScroller.forceFinished(true);
                }
            }
        }else {
            /**
             * 表示处于一直滑动中
             */
            if (mOffsetStart +movedY >= 0){
                movedY = 0;
                mOffsetStart = 0;
            }else if (mOffsetStart + movedY <= -mTextList.size()*mSpacing){
                movedY = 0;
                mOffsetStart = -mTextList.size()*mSpacing;
            }
        }
        if (listener != null){
            if(getSelectedNum() >= mTextList.size()){
                listener.onNumSelect(mTextList.get(mTextList.size() -1));
            }else {
                listener.onNumSelect(mTextList.get(getSelectedNum()));
            }
        }
        postInvalidate();
    }

    int getSelectedNum() {
        Log.e("zzf",(int) (Math.abs((mOffsetStart + movedY)) / mSpacing)+"");
        return (int) (Math.abs((mOffsetStart + movedY)) / 80);
    }

    /**
     * 给外界提供设置文字的入口
     * @param list
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTextList(List<String> list){
        this.mTextList = list;
        int[] textLengths = new int[mTextList.size()];
        for (int i = 0;i<mTextList.size();i++){
            Rect rect = getTextRect(mTextList.get(i));
            textLengths[i] = rect.width();
        }

        maxTextWidth = getMaxTextLength(textLengths);
        Log.e("zzf","maxTextWidth = " + maxTextWidth);
        invalidate();
    }

    /**
     * 获取文字所占的宽度，把文字的参数设置到paint上
     * 用此paint画到rect中，rect的宽就是文字的宽
     * @param text
     * @return
     */
    private Rect getTextRect(String text){
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),rect);
        return rect;
    }

    /**
     *得到数组里的最大长度
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getMaxTextLength(int[] textLengths){
        if (textLengths == null || textLengths.length == 0){
            return 0;
        }
        return Arrays.stream(textLengths).max().getAsInt();
    }
}





