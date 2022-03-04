package com.example.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView extends View {
    private String TAG = "zzf";
    int height; 
    int width;

    private Paint paint;
    private Rect rect;
    public MyView(Context context) {
        super(context);
        Log.e("TAG","MyView");
        initView();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("TAG","MyView ：" + attrs);
        initView();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        rect = new Rect(0, 0, width, height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("TAG","onMeasure");
        String specMode_width = "";
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        switch (specModeWidth) {
            case MeasureSpec.EXACTLY:
                specMode_width = "EXACTLY";
                break;
            case MeasureSpec.AT_MOST:
                specMode_width = "AT_MOST";
                break;
            case MeasureSpec.UNSPECIFIED:
                specMode_width = "UNSPECIFIED";
                break;
        }
        //高度度
        String specMode_height = "";
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        switch (specModeHeight) {
            case MeasureSpec.UNSPECIFIED:
                specMode_height = "UNSPECIFIED";
                break;
            case MeasureSpec.AT_MOST:
                specMode_height = "AT_MOST";
                break;
            case MeasureSpec.EXACTLY:
                specMode_height = "EXACTLY";
                break;
        }
        Log.e("TAG", "specMode_width = " + specMode_width + " , specMode_height = " +
                specMode_height);
        Log.e("TAG", "specSize_width = " + MeasureSpec.getSize(widthMeasureSpec) + ", specSize_height = " + MeasureSpec.getSize(heightMeasureSpec));

       /* width =  MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);*/
        /*height = getHeight();
        width = getWidth();*/
        //Log.e("TAG","height =" + height+";" + "width =" + width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("TAG","onSizeChanged");
        height = w;
        width = h;
        Log.e("TAG","height1 =" + height+";" + "width1 =" + width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TAG","onDraw");

        canvas.drawRect(rect, paint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("TAG","onFinishInflate");
    }
}
