package com.example.customview.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyView extends LinearLayout {
    private int baseLineX = 100;
    private int baseLineY = 400;
    private Paint mPaint;
    private String text = "您好";

    public MyView(Context context) {
        this(context,null);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLineY,1000,baseLineY,mPaint);

        /**
         * 基线的计算
         */
        /**
         * ascent: 系统建议的，绘制单个字符时，字符应当的最高高度所在线
         * descent:系统建议的，绘制单个字符时，字符应当的最低高度所在线
         * top: 可绘制的最高高度所在线
         * bottom: 可绘制的最低高度所在线
         *
         */
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float ascent = baseLineY + fontMetrics.ascent;
        float descent = baseLineY + fontMetrics.descent;
        float top = baseLineY + fontMetrics.top;
        float bottom = baseLineY + fontMetrics.bottom;


        //画top
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 1000, top, mPaint);

        //画ascent
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 1000, ascent, mPaint);

        //画descent
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, descent, 1000, descent, mPaint);

        //画bottom
        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 1000, bottom, mPaint);

        /**
         * 画text所占的区域
         */
        /**
         * 字符串所占的宽度
         */
        int width = (int) mPaint.measureText(text);
        /**
         * 字符串所占的高度
         */
        int height = (int) (bottom - top);
        Rect rect = new Rect(baseLineX,(int) top,baseLineX + width,(int)bottom);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rect,mPaint);

        /**
         * 画最小矩形
         */
        Rect minRect = new Rect();
        /**
         * 获取最小矩形
         */
        mPaint.getTextBounds(text,0,text.length(),minRect);
        /**
         * 这两个宽高与上面的宽高不一样，这个是包裹文字的最小矩形的宽高，比上面的两个数值小
         */
        int minwWidth = minRect.width();
        int minHeight = minRect.height();
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        minRect.left = baseLineX + minRect.left;
        minRect.right = baseLineX + minRect.right;
        mPaint.setColor(Color.RED);
        canvas.drawRect(minRect,mPaint);

        /**
         * draw的时候是一层一层的叠加的，先画的在最下面，后画的在最上面
         */

        /**
         * drawText与画线不一样，他是从baseLineX，baseLineY为x,y的值，然后在这条baseLine的上面开始画
         * 相当于baseLine为0，在这条线的上面为负，下面为正。
         */
        mPaint.setColor(Color.WHITE);
        canvas.drawText(text,baseLineX,baseLineY,mPaint);


        /**
         * 根据定点画文字
         */

        int drawTextTop = 600;
        /**
         * 因为top = baseLineY + fontMetrics.top，
         * 所以baseLineY = top - fontMetrics.top;
         */
        int baseLineY1 = (int) (drawTextTop - fontMetrics.top);

        mPaint.setColor(Color.RED);
        canvas.drawText(text,baseLineX,baseLineY1,mPaint);

        Paint.FontMetrics fontMetrics1 = mPaint.getFontMetrics();
        float ascent1 = baseLineY1 + fontMetrics1.ascent;
        float descent1 = baseLineY1 + fontMetrics1.descent;
        float top1 = baseLineY1 + fontMetrics1.top;
        float bottom1 = baseLineY1 + fontMetrics1.bottom;
        //画top
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top1, 1000, top1, mPaint);

        //画ascent
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent1, 1000, ascent1, mPaint);

        //画descent
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, descent1, 1000, descent1, mPaint);

        //画bottom
        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom1, 1000, bottom1, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLineY1,1000,baseLineY1,mPaint);

        /**
         * 给定中心线
         */
        int center = 1000;
        Paint.FontMetrics fontMetrics2 = mPaint.getFontMetrics();
        int baseLine = (int) (center + (fontMetrics2.bottom - fontMetrics2.top)/2 - fontMetrics2.bottom);
        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLine,1000,baseLine,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawLine(baseLineX,center,1000,center,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(text,baseLineX,baseLine,mPaint);
    }
}
