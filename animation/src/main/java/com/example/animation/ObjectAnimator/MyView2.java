package com.example.animation.ObjectAnimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView2 extends View {
    public static final float RADIUS = 70f;
    private Paint mPaint;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String mColor) {
        this.color = mColor;
        mPaint.setColor(Color.parseColor(mColor));
        invalidate();
        //requestLayout();
    }

    public MyView2(Context context) {
        this(context,null);
        init();
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(500,1500,RADIUS,mPaint);
    }
}
