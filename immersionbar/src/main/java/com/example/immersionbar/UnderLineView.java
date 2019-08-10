package com.example.immersionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zzf
 * @date 2019/8/5/005
 * 描述：
 */
public class UnderLineView extends View {
    private Paint mPaint;

    public UnderLineView(Context context) {
        this(context,null);
    }

    public UnderLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getWidth()/2,0,0,0,mPaint);
        canvas.drawLine(getWidth()/2,0,getWidth(),0,mPaint);
    }
}
