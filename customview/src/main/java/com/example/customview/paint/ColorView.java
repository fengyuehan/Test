package com.example.customview.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ColorView extends View {
    /**
     * paint设置颜色方式有三种
     * 1、setColor（）
     * 2、setARGB()
     * 3、setShader()
     */

    public Paint paint1,paint2,paint3;

    public ColorView(Context context) {
        this(context,null);
        init();
    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setColor(Color.parseColor("#009688"));
        paint1.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(Color.parseColor("#FF9800"));
        paint2.setAntiAlias(true);
        paint3 = new Paint();
        paint3.setARGB(100, 255, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(30,30,230,230,paint1);
        canvas.drawLine(30,250,100,400,paint2);
        canvas.drawCircle(80,500,50,paint3);
    }
}
