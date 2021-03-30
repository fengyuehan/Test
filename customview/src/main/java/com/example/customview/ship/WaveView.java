package com.example.customview.ship;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.customview.R;

/**
 * author : zhangzf
 * date   : 2021/3/22
 * desc   :
 */
public class WaveView extends View {

    private Paint mPaint;
    private Path mPath;
    // 水波长度
    private int waveLength = 800;
    // 水波高度
    private int waveHeight = 150;
    private int mHeight;
    private int halfWaveLength = waveLength / 2;
    private float mDeltaX;
    private Bitmap mBitMap;
    private PathMeasure mPathMeasure;
    private Matrix mMatrix;
    private float mDistance;
    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.sea_blue));
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        mMatrix = new Matrix();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        mBitMap = BitmapFactory.decodeResource(getResources(),R.mipmap.ship);
    }

    /**
     * onMeasure()→onSizeChanged()→onLayout()→onMeasure()→onLayout()→onDraw()
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        startAnim();
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(animation -> {
            mDeltaX = waveLength * ((float) animation.getAnimatedValue());

            mDistance = (getWidth() + waveLength + halfWaveLength) * ((float)animation.getAnimatedValue());
            postInvalidate();
        });
        animator.setDuration(13000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
        drawBitmap(canvas);
    }

    /**
     * PathMeasure有个getMatrix方法，通过这个方法可以获取指定长度的位置坐标及该点Matrix。
     * distance：距离Path起点的长度
     * matrix：根据flags封装好的矩阵
     * flags：选择哪些内容会传入到matrix中，可选值有POSITION_MATRIX_FLAG(位置)和ANGENT_MATRIX_FLAG(正切)。
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        mPathMeasure.setPath(mPath, false);
        mMatrix.reset();
        mPathMeasure.getMatrix(mDistance, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        mMatrix.preTranslate(- mBitMap.getWidth() / 2, - mBitMap.getHeight());
        canvas.drawBitmap(mBitMap,mMatrix,mPaint);
    }

    private void drawWave(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(0 - mDeltaX,mHeight / 2);
        Log.e("zzf","getWidth() = " + getWidth() + " ; getHeight() = " + getHeight());
        /**
         * 绘制的时候，下一个绘制的起点是上一个绘制的终点
         */
        for (int i = 0; i <= getWidth() + waveLength; i += waveLength) {
            mPath.rQuadTo(halfWaveLength / 2, waveHeight, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength / 2, -waveHeight, halfWaveLength, 0);
        }

        mPath.lineTo(getWidth() + waveLength, getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
