package com.example.customview.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LockView extends View {
    private Paint mPaint;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int SMALL_RADIUS = 60;
    private static final int BIGGER_RADIUS = 120;
    private static final String GRAYCOLOR = "#ffd5dbe8";
    private static final String BLUECOLOR = "#ff508cee";
    private static final float LINE_WIDTH = 10.0f;
    private static final float LINE_NORMAL = 1.0f;
    private int width;
    private int height;
    private Circle[] mCircles;
    private Listener mListener;
    private float mNextX, mNextY;
    private Path mPath;
    private String mRightPsw;
    private StringBuilder mInputPsw;


    public LockView(Context context) {
        this(context, null);
        init();
    }

    public LockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor(GRAYCOLOR));
        mPath = new Path();
        mInputPsw = new StringBuilder();
        mCircles = new Circle[9];
        /**
         * 设置每个圆心的坐标
         */
        int contentWidth = width - 2 * BIGGER_RADIUS;
        int contentHeight = height - 2 * BIGGER_RADIUS;
        for (int i = 0; i < mCircles.length; i++) {
            mCircles[i] = new Circle();
            mCircles[i].x = BIGGER_RADIUS + (i % 3) * contentWidth / 2;
            mCircles[i].y = BIGGER_RADIUS + (i / 3) * contentHeight / 2;
            mCircles[i].isClicked = false;
            mCircles[i].innderRadius = SMALL_RADIUS;
            mCircles[i].outterRadius = BIGGER_RADIUS;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getSize(widthMeasureSpec, WIDTH);
        height = getSize(heightMeasureSpec, HEIGHT);
        Log.e("zzf",width + ":" + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCircles.length; i++) {
            Circle circle = mCircles[i];
            if (circle.isClicked) {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.parseColor(BLUECOLOR));
                mPaint.setStrokeWidth(LINE_NORMAL);
                /**
                 * 画大圆
                 */
                canvas.drawCircle(circle.x, circle.y, circle.outterRadius, mPaint);
            } else {
                mPaint.setColor(Color.parseColor(GRAYCOLOR));
            }
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(LINE_NORMAL);
            /**
             * 画小圆
             */
            canvas.drawCircle(circle.x, circle.y, circle.innderRadius, mPaint);
        }

        /**
         * 经过的点连线
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(LINE_WIDTH);
        mPaint.setColor(Color.parseColor(BLUECOLOR));
        canvas.drawPath(mPath, mPaint);
        canvas.save();
    }


    private int getSize(int SizeInfoMeasureSpec, int specSize) {
        int mode = MeasureSpec.getMode(SizeInfoMeasureSpec);
        int size = specSize;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                size = MeasureSpec.getSize(SizeInfoMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                size = Math.min(size, MeasureSpec.getSize(specSize));
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                size = specSize;
                break;
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int index = getClickIndex(event.getX(), event.getY());
                if (index >=0 && index <= mCircles.length){
                    getContent(index);
                    mPath.moveTo(mCircles[index].x,mCircles[index].y);
                    mCircles[index].isClicked = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                Log.e("zzf",x + ":" + y);
                int mIndex = getClickIndex(x,y);
                if (!mCircles[mIndex].isClicked){
                    if (mIndex >=0 && mIndex <= mCircles.length){
                        mCircles[mIndex].isClicked = true;
                        getContent(mIndex);
                        if (getClickIndex(mNextX,mNextY) > 0){
                            mPath.lineTo(mCircles[mIndex].x,mCircles[mIndex].y);
                        }else {
                            /**
                             * 用参数中指定的点代替原来最后的一个点
                             */
                            mPath.setLastPoint(mCircles[mIndex].x,mCircles[mIndex].y);
                        }
                    }else {
                        mNextX = x;
                        mNextY = y;
                        Log.e("zzf","mNextX = " + mNextX + ":" + "mNextY = "+ mNextY);
                        mPath.setLastPoint(mNextX,mNextY);
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isPswOk()){
                    mListener.onInputOK();
                }else {
                    mListener.onInputError();
                }
                uninit();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void uninit() {
        if(null != mCircles){
            for(int i=0;i<mCircles.length;i++){
                mCircles[i].isClicked = false;
            }
            invalidate();
        }
        if(null != mPath){
            mPath.reset();
        }
        mInputPsw.delete(0,mInputPsw.length());
    }

    /**
     * 判断密码是否正确
     */

    private boolean isPswOk(){
        Log.e("zzf","mInputPsw = " +mInputPsw);
        if (mInputPsw == null || mRightPsw == null){
            return false;
        }else if (!mRightPsw.equals(mInputPsw)){
            return false;
        }else {
            return true;
        }
    }
    /**
     *  获取圆点上面的数字
     * @param index
     */
    private void getContent(int index) {
        int content= index + 1;
        if (mInputPsw.length() == 0){
            mInputPsw.append(content );
        }else {
            char lastCharacter = mInputPsw.charAt(mInputPsw.length()-1);
            int iLastValue = Character.getNumericValue(lastCharacter);
            if(iLastValue != content){
                mInputPsw.append(content);
            }
        }
    }

    private int getClickIndex(float x, float y) {
        for (int i = 0; i < mCircles.length; i++) {
            Circle circle = mCircles[i];
            if (x > circle.x - circle.outterRadius
                    && x < circle.x + circle.outterRadius
                    && y > circle.y - circle.outterRadius
                    && y < circle.y + circle.outterRadius
            ) {
                return i;
            }
        }
        return -1;
    }

    public void setRightPsw(String mRightPsw) {
        this.mRightPsw = mRightPsw;
    }

    public Listener getmListener() {
        return mListener;
    }

    public void setmListener(Listener mListener) {
        this.mListener = mListener;
    }
}
