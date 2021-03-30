package com.example.customview.guaguaka;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : zhangzf
 * date   : 2021/3/23
 * desc   :
 */
public class GuaGuaCard extends View implements View.OnTouchListener {

    private Paint mPaint;
    private Rect mRect;
    //把画的所有点都存起来
    private Path mPath;
    private PorterDuffXfermode mPorterDuffXfermode;

    public GuaGuaCard(Context context) {
        this(context, null);
    }

    public GuaGuaCard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        setOnTouchListener(this);
        //关闭硬件加速（遇到显示异常，比如全黑全白时设置）
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mRect = new Rect();
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(20));
        mPaint.setColor(Color.RED);
    }




    private float dp2px(int dp) {

        float density = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);

        return px;
    }


    private static final String TAG = "GuaGuaCard";

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {

            Log.e(TAG, "onTouch: action = MotionEvent.ACTION_DOWN");
            startX = event.getX();
            startY = event.getY();
            mPath.moveTo(startX, startY);
        } else if (action == MotionEvent.ACTION_MOVE) {

            Log.e(TAG, "onTouch: action = MotionEvent.ACTION_MOVE");

            endX = event.getX();
            endY = event.getY();
            mPath.lineTo(endX, endY);
            invalidate();

        } else if (action == MotionEvent.ACTION_UP) {

            Log.e(TAG, "onTouch: action = MotionEvent.ACTION_UP");
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e(TAG, "onDraw: -------------------startX = " + startX + "  endX = " + endX);

        mPaint.setXfermode(null);
        mRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
        mPaint.setColor(Color.parseColor("#FFCEE0E0"));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawPath(mPath, mPaint);
    }
}
