package com.mj.demo.statusbarplus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import androidx.core.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyBendBcgView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private ViewPager mViewPager;
    private int mLeft;//左部到拐点
    private int mTop;//上部倒拐点
    private Path mPath;
    private int solidTop;//最开始的上部拐点
    private int nowTop = 0;

    public MyBendBcgView(Context context) {
        this(context, null);
    }

    public MyBendBcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    private void initValue() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, nowTop);
        mPath.lineTo(mLeft, mTop + nowTop);
        mPath.lineTo(mWidth - mLeft, mTop + nowTop);
        mPath.lineTo(mWidth, nowTop);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void reDraw(int top) {
        nowTop = top;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewPager = (ViewPager) ((RelativeLayout) getParent()).getChildAt(1);
        mLeft = ((ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams()).leftMargin;
        mTop = mViewPager.getLayoutParams().height / 20;
        solidTop = mTop;
    }

    public int getSolidTop() {
        return solidTop;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
