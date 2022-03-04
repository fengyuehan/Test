package com.example.lettersidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zzf
 * @date 2019/7/23/023
 * 描述：
 */
public class MyView extends View {
    private String text;
    private Rect mRect;
    private Paint textPaint,ciclePaint;
    private int[] colors = {R.color.color_ffc365,R.color.color_7fcaf5,R.color.color_78e1e7,
            R.color.color_e491f9,R.color.color_6b9ff7,R.color.color_f78481};

    //hashcode是唯一的，能保证每次刷新不会变
    int color = colors[text.hashCode() % 6];
    public MyView(Context context) {
        this(context,null);
        initView();
    }

    public MyView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(2.5f);

        ciclePaint = new Paint();
        ciclePaint.setAntiAlias(true);
        ciclePaint.setColor(color);
        ciclePaint.setStyle(Paint.Style.FILL);

        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(text)) {
            //int color = colors[(int) (Math.random() * 26)];


            // 画圆
            ciclePaint.setColor(color);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, ciclePaint);
            // 写字
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(getWidth() * 0.4f);
            textPaint.setStrokeWidth(2.5f);
            textPaint.getTextBounds(text, 0, 1, mRect);
            // 垂直居中
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
            // 左右居中
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, getWidth() / 2, baseline, textPaint);
        }
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }


}
