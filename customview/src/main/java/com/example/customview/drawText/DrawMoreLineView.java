package com.example.customview.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawMoreLineView extends View {
    private Paint mPaint;
    private int width;
    private int height;
    String[] strs={"床前明月光，","疑是地上霜。","举头望明月，","低头思故乡。"};
    public DrawMoreLineView(Context context) {
        this(context,null);
        init(context);
    }

    public DrawMoreLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        //mPaint.setTextAlign(Paint.Align.CENTER);

        /**
         * 则每一行文字的高度为
         */
        //float height = bottom - top;
        float baseLine = height/3 + (bottom - top)/2 - bottom;

        for (int i = 0; i < strs.length;i++){
            float offset = i * (bottom - top);
            canvas.drawText(strs[i],width/3,baseLine + offset,mPaint);
        }

    }
}
