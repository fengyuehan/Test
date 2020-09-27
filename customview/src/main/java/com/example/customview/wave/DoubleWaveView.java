package com.example.customview.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class DoubleWaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int width, height, peekValue;
    private ValueAnimator mAnimator;

    private int dx;

    public DoubleWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        post(() -> {
            initAnimation();
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        peekValue = height / 4 * 3;
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = 400;
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.max(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                // result = mTextBound.width();
                result = 400;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.max(result, val) : result;
        return result;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mAnimator != null && !mAnimator.isRunning()) {
            mAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPaint.setColor(Color.parseColor("#4879FF"));
        mPath.moveTo(-width + dx, height / 2);
        for (int i = 0; i < 2; i++) {
            mPath.rQuadTo(width / 4, peekValue, width / 2, 0);
            mPath.rQuadTo(width / 4, -peekValue, width / 2, 0);
        }
        mPath.lineTo(width, 0);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setColor(Color.parseColor("#60ffffff"));
        mPath.moveTo(-width + dx, peekValue);
        for (int i = 0; i < 2; i++) {
            mPath.rQuadTo(width / 4, -peekValue, width / 2, 0);
            mPath.rQuadTo(width / 4, peekValue, width / 2, 0);
        }
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void initAnimation() {
        width = getMeasuredWidth();
        if (width == 0 || mAnimator != null) {
            return;
        }
        mAnimator = ValueAnimator.ofInt(0, width);
        mAnimator.setDuration(3000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(animation -> {
            dx = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        mAnimator.start();
    }
}
