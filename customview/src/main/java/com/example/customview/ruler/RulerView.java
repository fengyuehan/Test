package com.example.customview.ruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.customview.ColorUtils;

public class RulerView extends View {

    /**
     * 渐变色起始色
     */
    @ColorInt
    int startColor = Color.parseColor("#ff3415b0");

    /**
     * 渐变色结束色
     */
    @ColorInt
    int endColor = Color.parseColor("#ffcd0074");

    /**
     * 指示器颜色
     */
    @ColorInt
    int indicatorColor = startColor;

    /**
     * 控件宽高
     */
    int width, height;

    /**
     * 控件绘制画笔
     */
    Paint paint;

    /**
     * 文字画笔
     */
    Paint textPaint;

    /**
     * 线条宽度，默认12px
     */
    int lineWidth = 24;

    /**
     * 长、中、短刻度的高度
     */
    int maxLineHeight, midLineHeight, minLineHeight;

    /**
     * 指示器半径
     */
    int indicatorRadius = lineWidth / 2;

    /**
     * 刻度尺的开始、结束数字
     */
    int startNum = 0, endNum = 40;

    /**
     * 每个刻度代表的数字单位
     */
    int unitNum = 1;

    /**
     * 刻度间隔
     */
    int lineSpacing = 3 * lineWidth;

    /**
     * 第一刻度位置距离当前位置的偏移量，一定小于0
     */
    float offsetStart = 0;

    /**
     * 辅助计算滑动，主要用于惯性计算
     */
    Scroller scroller;

    /**
     * 跟踪用户手指滑动速度
     */
    VelocityTracker velocityTracker;

    /**
     * 定义惯性作用的最小速度
     */
    float minVelocityX;

    /**
     * 刻度文字大小
     */
    int textSize = 96;

    /**
     * 文字高度
     */
    float textHeight;

    /**
     * 用户手指按下控件滑动时的初始位置坐标
     */
    float downX;

    /**
     * 当前手指移动的距离
     */
    float movedX;

    OnNumSelectListener listener;

    public interface OnNumSelectListener {
        void onNumSelect(int selectedNum);
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public void setOnNumSelectListener(OnNumSelectListener listener) {
        this.listener = listener;
    }

    public RulerView(Context context) {
        this(context,null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        paint = new Paint();
        paint.setColor(startColor);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(startColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.descent - fontMetrics.ascent;

        scroller = new Scroller(context);

        velocityTracker = VelocityTracker.obtain();
        minVelocityX = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0;i < (endNum - startNum) +1;i++){
            int mLineHeight = minLineHeight;
            if (i % 10 == 0){
                mLineHeight = maxLineHeight;
            }else if (i % 5 == 0){
                mLineHeight = midLineHeight;
            }

            /**
             * 线段的左右两边的位置
             */
            float lineLeft = offsetStart + movedX + 200 + i * lineSpacing;
            float lineRight = lineLeft + lineWidth;
            /**
             * 这里设置4倍是因为上面患有 一个圆形的指示器
             */
            RectF rectF = new RectF(lineLeft,4 * indicatorRadius,lineRight,mLineHeight);
            paint.setColor(ColorUtils.getColor(startColor,endColor,getRadio(i)));
            canvas.drawRoundRect(rectF,lineWidth / 2,lineWidth /2,paint);

            /**
             * 绘制刻度线下的数字
             */
            if (i % 10 == 0){
                paint.setColor(ColorUtils.getColor(startColor,endColor,getRadio(i)));
                canvas.drawText(i + "",lineLeft  - textPaint.measureText("" + i) / 2,mLineHeight + 20 + textHeight,textPaint);
            }

            /**
             * 绘制指示器
             */
            int indicatorX = indicatorRadius + 200;
            int indicatorY = indicatorRadius;

            indicatorColor = ColorUtils.getColor(startColor, endColor, Math.abs((float) (offsetStart + movedX) / (float) (lineSpacing * ((endNum - startNum) / unitNum))));
            paint.setColor(indicatorColor);
            canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        maxLineHeight = height *2 /3;
        midLineHeight = maxLineHeight *4 /5;
        minLineHeight = maxLineHeight * 3/5;
    }

    /**
     * 判断滑动了多少根线的宽度
     * @return
     */
    int getSelectedNum() {
        return (int) (Math.abs((offsetStart + movedX)) / lineSpacing);
    }

    private float getRadio(int i){
        return i / (float)((endNum - startNum)/unitNum);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 强制终止在某一个值
                 */
                scroller.forceFinished(true);
                downX = event.getX();
                movedX = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                movedX = event.getX() - downX;
                Log.e("zzf", "offsetStart==>" + offsetStart);
                Log.e("zzf", "movedX==>" + movedX);
                Log.e("zzf", "offsetStart + movedX==>" + (offsetStart + movedX));
                /**
                 * 屏幕的坐标系是左上角，往左滑为负，往右滑为正
                 * offsetStart为起始位置，始终为负
                 * offsetStart + movedX = 0表示起始位置，当>0的时候则要回归到起始位置
                 * ((endNum - startNum)/unitNum)*lineSpacing表示尺子的最大长度
                 * < -((endNum - startNum)/unitNum)*lineSpacing表示超过最大距离
                 * 所以此时则要回到最大位置
                 */
                if (offsetStart + movedX > 0){
                    offsetStart = 0;
                    movedX = 0;
                }else if (offsetStart + movedX < -((endNum - startNum)/unitNum)*lineSpacing){
                    offsetStart = -((endNum - startNum)/unitNum)*lineSpacing;
                    movedX = 0;
                }
                if (listener != null){
                    listener.onNumSelect(getSelectedNum());
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (offsetStart + movedX <= 0 && offsetStart + movedX >= -((endNum - startNum) / unitNum) * lineSpacing){
                    offsetStart = offsetStart + movedX;
                    movedX = 0;
                    /**
                     * 取整
                     */
                    offsetStart = ((int) (offsetStart / lineSpacing)) * lineSpacing;
                }else if (offsetStart + movedX > 0){
                    offsetStart = 0;
                    movedX = 0;
                }else {
                    offsetStart = -((endNum - startNum) / unitNum) * lineSpacing;
                    movedX = 0;
                }
                if (listener != null){
                    listener.onNumSelect(getSelectedNum());
                }
                //计算当前手指放开的滑动速率
                velocityTracker.computeCurrentVelocity(500);
                /**
                 * 得到X轴方向的速度
                 */
                float velocityX = velocityTracker.getXVelocity();
                if (Math.abs(velocityX) > minVelocityX){
                    /**
                     * 惯性滑动
                     */
                    scroller.fling(0, 0, (int) velocityX, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                }
                postInvalidate();
                break;
        }
        return true;
    }

    /**
     * Scroller与computeScroll的区别
     * Scroller：Scroller只是个计算器，提供插值计算，让滚动过程具有动画属性，但它并不是UI，也不是辅助UI滑动，反而是单纯地为滑动提供计算。
     * computeScroll：computeScroll也不是来让ViewGroup滑动的，真正让ViewGroup滑动的是scrollTo,scrollBy。computeScroll的作用是计算ViewGroup如何滑动。
     * 而computeScroll是通过draw来调用的。
     */

    @Override
    public void computeScroll() {
        super.computeScroll();
        /**
         * 判断mScroller滚动是否完成
         * computeScrollOffset返回false，则滚动滑动
         * 返回true,则继续调用scrollTo/scrollBy,然后继续调用postInvalidate()/Invalidate()进行重绘
         * 而computeScrollOffset返回false  or true 是根据变量mFinished俩决定
         *
         */
        if (scroller.computeScrollOffset()){
            if (scroller.getCurrX() == scroller.getFinalX()){
                //表示停止
                if (offsetStart + movedX <= 0 && offsetStart + movedX >= -((endNum - startNum) / unitNum) * lineSpacing){
                    offsetStart = offsetStart + movedX;
                    movedX = 0;
                    /**
                     * 取整
                     */
                    offsetStart = ((int) (offsetStart / lineSpacing)) * lineSpacing;
                }else if (offsetStart + movedX > 0){
                    offsetStart = 0;
                    movedX = 0;
                }else {
                    offsetStart = -((endNum - startNum) / unitNum) * lineSpacing;
                    movedX = 0;
                }
            }else {
                //表示没有停止，继续惯性滑动
                movedX = scroller.getCurrX()-scroller.getStartX();
                /**
                 * 边界控制
                 */
                if (offsetStart +movedX >= 0){
                    movedX = 0;
                    offsetStart = 0;
                    scroller.forceFinished(true);
                }else if (offsetStart + movedX <= -((endNum - startNum) / unitNum) * lineSpacing){
                    movedX = 0;
                    offsetStart = -((endNum - startNum) / unitNum) * lineSpacing;
                    scroller.forceFinished(true);
                }
            }
        }else {
            /**
             * 表示处于一直滑动中
             */
            if (offsetStart +movedX >= 0){
                movedX = 0;
                offsetStart = 0;
            }else if (offsetStart + movedX <= -((endNum - startNum) / unitNum) * lineSpacing){
                movedX = 0;
                offsetStart = -((endNum - startNum) / unitNum) * lineSpacing;
            }
        }
        if (listener != null){
            listener.onNumSelect(getSelectedNum());
        }
        postInvalidate();
    }
}
