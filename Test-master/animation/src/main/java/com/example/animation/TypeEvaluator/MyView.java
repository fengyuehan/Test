package com.example.animation.TypeEvaluator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.animation.Interpolator.DecelerateAccelerateInterpolator;

public class MyView extends View {

    public static final float RADIUS = 70f;
    private Point currentPoint;
    private Paint mPaint;

    public MyView(Context context) {
        this(context,null);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
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
        if (currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);

            Point startPoint = new Point(RADIUS,RADIUS);
            Point endPoint = new Point(700,1000);
            ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
            animator.setDuration(2000);
            //animator.setInterpolator(new DecelerateAccelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();
                    Log.e("zzf","currentPoint :" + currentPoint.getX() + "----------" + currentPoint.getY());
                    invalidate();
                }
            });
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setStartDelay(1000);
            animator.start();
        }else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }
    }
}
