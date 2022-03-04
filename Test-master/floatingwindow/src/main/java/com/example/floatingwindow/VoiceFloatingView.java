package com.example.floatingwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;


/**
 * 功能：
 * 内部实现悬浮窗功能
 * 实现拖动时判断左右方向自动沾边效果
 * 实现沾边处圆角转直角，非沾边时直角转圆角
 */
public class VoiceFloatingView extends View {

    /**
     * 默认的宽高
     */
    private int mDefaultWidth,mDefaultHeight;

    /**
     * 实际的宽高
     */
    private int mWidth,mHeight;
    /**
     * 当前View绘制相关
     */
    private Paint mPaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mPorterDuffXfermode;
    private Direction mDirection = Direction.right;
    private int mOrientation;
    /**
     * 屏幕的宽高
     */
    private int mWidthPixels;
    private int mHeightPixels;

    /**
     * 悬浮窗管理相关
     */
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean mIsShow;
    /**
     * 滑动的距离
     */
    private float moveX,moveY;

    public VoiceFloatingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        /**
         * 表示窗口类型
         * 1-99：应用类型的窗口 Activity,Dialog
         * 1000-1999：子窗口  PopWindow,Window必须要一个父窗口
         * 2000以上：系统窗口  Toast
         *
         * FIRST_SYSTEM_WINDOW = 2000
         * TYPE_APPLICATION_OVERLAY = FIRST_SYSTEM_WINDOW + 38
         * TYPE_PHONE              = FIRST_SYSTEM_WINDOW+2
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.gravity = Gravity.START|Gravity.TOP;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        /**
         * WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
         * 如果没有设置FLAG_NOT_FOCUSABLE，那么屏幕上弹窗之外的地方不能点击。如果设置了FLAG_NOT_FOCUSABLE，
         * 那么屏幕上弹窗之外的地方能够点击、但是弹窗上的EditText无法输入、键盘也不会弹出来。
         *
         * WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
         * 如果设置了FLAG_NOT_TOUCH_MODAL，那么屏幕上弹窗之外的地方能够点击、弹窗上的EditText也可以输入、键盘能够弹出来
         */
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        /**
         * PorterDuff.Mode:用于图形合成时的图像过渡模式计算,功16种共模式
         * CLEAR:
         * SRC:源图像
         * DST:目的图像
         * SRC_OVER:源图像覆盖在目的图像上面
         * DST_OVER:目的图像覆盖在源图像上面
         * SRC_IN:原图像与目的图像的重合部分，原图像的颜色
         * DST_IN:原图像与目的图像的重合部分，目的图像的颜色
         * SRC_OUT:与原图像交集以外目的图像
         * DST_OUT:与源图像交集以外的源图像
         * SRC_ATOP:交集的部分在目的图像的上面（先画的在后画的上面）
         * DST_ATOP:交集部分在源图像上面（后画的在先画的上面）
         * XOR :两个图像交集以外的部分
         * DARKEN:交集部分为暗色
         * LIGHTEN：交集部分为亮色
         * MULTIPLY：交集部分
         * SCREEN：
         */
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
        mBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.b8e)).getBitmap();
        mDefaultHeight = 210;
        mDefaultWidth = 210;

        /**
         * 记录当前屏幕的方向和屏幕宽度
         */
        recordsScreenWidth();

    }

    /**
     * getSize（Point），getRectSize（Rect）和getMetrics（DisplayMetrics）。
     * 用上面的得到的宽高要小于实际的宽高，只是应用实际显示区域，不包括状态栏什么的
     * getRealSize（Point），getRealMetrics（DisplayMetrics）
     * 实际显示区域指定包含系统装饰的内容的显示部分
     */
    private void recordsScreenWidth() {
        mOrientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics =  new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mWidthPixels = displayMetrics.widthPixels;
        mHeightPixels = displayMetrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureSize(mDefaultWidth,widthMeasureSpec);
        mHeight = measureSize(mDefaultHeight,heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        int gap = 20;
        int radius = 30;
        //画透明色圆角背景
        mPaint.setColor(Color.parseColor("#D9E1E1E1"));
        canvas.drawRoundRect(0,0,mWidth,mHeight,radius,radius,mPaint);
        /**
         * 根据最后停留的地方绘制多一层直角矩形，覆盖圆角
         */
        switch (mDirection){
            default:
            case right:
                mPaint.setXfermode(mPorterDuffXfermode);
                canvas.drawRoundRect(mWidth/2,0,mWidth,mHeight,0,0,mPaint);
                break;
            case left:
                mPaint.setXfermode(mPorterDuffXfermode);
                canvas.drawRoundRect(0,0,mWidth/2,mHeight,0,0,mPaint);
                break;
            case move:
                break;
        }
        mPaint.setXfermode(null);
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(gap,gap,mWidth-gap,mHeight-gap,radius,radius,mPaint);
        /**
         * 绘制图片
         */
        canvas.drawBitmap(mBitmap,(mWidth - mBitmap.getWidth())/2,(mHeight - mBitmap.getHeight())/2,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mWindowManager != null){
            if (getResources().getConfiguration().orientation != mOrientation){
                recordsScreenWidth();
            }
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    /**
                     * getX:表示触摸点距离自身左边界的距离
                     * getRawX:表示触摸点距离屏幕左边界的距离
                     */
                    moveX = event.getRawX();
                    moveY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float nowX = event.getRawX();
                    float nowY = event.getRawY();
                    float x = nowX - moveX;
                    float y = nowY - moveY;
                    moveX = nowX;
                    moveY = nowY;
                    mLayoutParams.x = (int) (mLayoutParams.x + x);
                    mLayoutParams.y = (int) (mLayoutParams.y + y);
                    /**
                     * 控制宽高范围
                     */
                    if (mLayoutParams.x < 0){
                        mLayoutParams.x = 0;
                    }
                    /*if (mLayoutParams.x > mWidthPixels){
                        mLayoutParams.x = mWidthPixels - mWidth;
                    }*/
                    if (mLayoutParams.y < 0){
                        mLayoutParams.y = 0;
                    }
                    /*if (mLayoutParams.y > mHeightPixels){
                        mLayoutParams.y = mHeightPixels - mHeight;
                    }*/
                    if (mDirection != Direction.move){
                        mDirection = Direction.move;
                        invalidate();
                    }
                    mWindowManager.updateViewLayout(this,mLayoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    handleDirection((int) event.getRawX(), (int) event.getRawY());
                    invalidate();
                    mWindowManager.updateViewLayout(this, mLayoutParams);
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * show
     */
    public void show() {
        if (!mIsShow) {
            if (mLayoutParams.x == 0 && mLayoutParams.y == 0 && mDirection == Direction.right) {
                mLayoutParams.x = mWidthPixels - mDefaultWidth;
                mLayoutParams.y = 0;
            }
            if (mDirection == Direction.move) {
                handleDirection(mLayoutParams.x, mLayoutParams.y);
            }
            mWindowManager.addView(this, mLayoutParams);
            mIsShow = true;
        }
    }

    /**
     * 调整悬浮窗位置
     * 根据提供坐标自动判断粘边
     */
    public void updateViewLayout(int x, int y) {
        if (mIsShow) {
            handleDirection(x, y);
            invalidate();
            mLayoutParams.y = y;
            mWindowManager.updateViewLayout(this, mLayoutParams);
        }
    }

    /**
     * dismiss
     */
    public void dismiss() {
        if (mIsShow) {
            mWindowManager.removeView(this);
            mIsShow = false;
        }
    }

    /**
     * 判定所处方向
     */
    private void handleDirection(int x, int y) {
        if (x > (mWidthPixels / 2)) {
            mDirection = Direction.right;
            mLayoutParams.x = mWidthPixels - getMeasuredWidth();
        } else {
            mDirection = Direction.left;
            mLayoutParams.x = 0;
        }
    }

    /**
     * 计算宽高
     */
    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
        //EXACTLY	当前的尺寸就是当前View应该取的尺寸
        //AT_MOST	当前尺寸是当前View能取的最大尺寸
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    /**
     * 方向
     */
    public enum Direction {
        /**
         * 左、右、移动
         */
        left,
        right,
        move
    }
}
