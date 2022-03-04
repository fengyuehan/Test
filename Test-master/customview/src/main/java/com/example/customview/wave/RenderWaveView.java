package com.example.customview.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RenderWaveView extends SurfaceView implements SurfaceHolder.Callback {
    private static Object lock = new Object();
    private Paint mPaint;
    /**
     * 三条线
     * mFirstPath：向上的线
     * mSecondPath：向下的线
     * mCenterPath：中间的线，振幅是前面两条线的1/5
     */
    private Path mFirstPath, mSecondPath, mCenterPath;
    /**
     * 采样点
     */
    private static final int SAMPLE_SIZE = 128;
    /**
     * 采样点X的坐标
     */
    private float[] mSimplePointX;
    /**
     * 采样点位置均匀映射到[-2,2]的X
     */
    private float[] mapX;
    /**
     * 画布宽高
     */
    private int width, height;
    /**
     * 画布中心的高度
     */
    private int mCenterHeight;
    /**
     * 振幅
     */
    private int mAmplitude;
    /**
     * 用于处理矩形的rectF
     */
    private final RectF rectF = new RectF();

    /**
     * 绘图交叉模式。放在成员变量避免每次重复创建。
     */
    private final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private final int backGroundColor = Color.rgb(24, 33, 41);
    private final int centerPathColor = Color.argb(64, 255, 255, 255);

    WaveThread waveThread;
    public RenderWaveView(Context context) {
        super(context);
        initView();
    }

    public RenderWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RenderWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mFirstPath = new Path();
        mSecondPath = new Path();
        mCenterPath = new Path();
        mPaint.setDither(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        waveThread = new WaveThread(holder);
        waveThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (lock){
            if (waveThread != null){
                waveThread.setRun(false);
            }
        }
    }

    class WaveThread extends Thread{
        SurfaceHolder surfaceHolder;
        private boolean isRunning;
        public WaveThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true){
                synchronized (lock){
                    while (true){
                        if (!isRunning){
                            return;
                        }
                        Canvas canvas = surfaceHolder.lockCanvas();
                        drawRender(canvas,startTime);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
        public void setRun(boolean isRun){
            isRunning = isRun;
        }
    }

    /**
     * Canvas：渲染器
     * @param canvas
     */
    private  void drawRender(Canvas canvas,long startTime){
        if (mSimplePointX == null) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            /**
             * 中心高度为高度的一半，即右移
             */
            mCenterHeight = height >> 1;
            /**
             * 振幅为宽度的1/8
             */
            mAmplitude = width >> 3;
            mSimplePointX = new float[SAMPLE_SIZE + 1];
            mapX = new float[SAMPLE_SIZE + 1];
            /**
             * 采样点的距离
             */
            float gap = width / SAMPLE_SIZE;
            float x;

            for (int i = 0; i <= SAMPLE_SIZE; i++) {
                x = i * gap;
                mSimplePointX[i] = x;
                /**
                 * 将x映射到[-2,2]的区间上
                 */
                mapX[i] = (x / width) * 4 - 2;
            }
        }

        /**
         * 重置所有的path并移动到起点
         * rewind():清除内容，但会保留相关的数据结构
         * reset():清除内容，重置到初始状态
         */
        mFirstPath.rewind();
        mSecondPath.rewind();
        mCenterPath.rewind();
        mFirstPath.moveTo(0, mCenterHeight);
        mSecondPath.moveTo(0, mCenterHeight);
        mCenterPath.moveTo(0, mCenterHeight);
        //当前时间的偏移量，通过该偏移量使得每次绘图都向右偏移，让画面动起来
        //如果希望速度快一点，可以调小分母
        float offset = (System.currentTimeMillis() - startTime) / 500;
        for (int i = 0; i <= SAMPLE_SIZE; i++) {
            float x1 = mSimplePointX[i];
            float y1 = (float) (mAmplitude * calcValue(mapX[i], offset));
            mFirstPath.lineTo(x1, mCenterHeight + y1);
            mSecondPath.lineTo(x1, mCenterHeight - y1);
            mCenterPath.lineTo(x1, mCenterHeight + y1 / 5f);
        }
        //连接所有路径到终点
        mFirstPath.lineTo(width, mCenterHeight);
        mSecondPath.lineTo(width, mCenterHeight);
        mCenterPath.lineTo(width, mCenterHeight);

        /**
         * 离屏缓存
         */
        int saveCount = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

        /**
         * 填充上下两条线条
         */
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        canvas.drawPath(mFirstPath, mPaint);
        canvas.drawPath(mSecondPath, mPaint);

        /**
         * 绘制渐变
         */
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(xfermode);

        /**
         *  背景渐变色
         */
        rectF.set(0, mCenterHeight + mAmplitude, width, mCenterHeight - mAmplitude);
        mPaint.setShader(new LinearGradient(0, mCenterHeight + mAmplitude, 0, mCenterHeight - mAmplitude, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP));
        canvas.drawRect(rectF, mPaint);

        /**
         * 清理
         */
        mPaint.setShader(null);
        mPaint.setXfermode(null);

        /**
         * 叠加layer
         */
        canvas.restoreToCount(saveCount);

        //绘制上弦线
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(mFirstPath, mPaint);

        //绘制下弦线
        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mSecondPath, mPaint);

        //绘制中间线
        mPaint.setColor(centerPathColor);
        canvas.drawPath(mCenterPath, mPaint);
    }

    private double calcValue(float mapX, float offset) {
        //offset %= 2;
        double sinFunc = Math.sin(0.75 * Math.PI * mapX - offset * Math.PI);
        double recessionFunc = Math.pow(4 / (4 + Math.pow(mapX, 4)), 2.5);
        return sinFunc * recessionFunc;
    }
}
