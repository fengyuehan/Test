package com.example.customview.ImageAuthentication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.customview.R;

/**
 * author : zhangzf
 * date   : 2021/3/8
 * desc   :
 */
public class ImageAuthenticationView extends androidx.appcompat.widget.AppCompatImageView {
    private AuthenticationListener mListener;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 验证的图像
     */
    private Bitmap mBitmap;
    /**
     * 验证滑块的高度
     */
    private int mSliderHeight;

    /**
     * 验证滑块的宽度
     */
    private int mSliderWidth;

    /**
     * 验证滑块宽占用整体图片大小的比例,默认1/12
     */
    private int mUnitWidthScale;
    /**
     * 验证滑块高度占用整体图片大小的比例,默认1/10
     */
    private int mUnitHeightScale;
    /**
     * 随机生成滑块的X坐标
     */
    private int mUnitRandomX;
    /**
     * 随机生成滑块的Y坐标
     */
    private int mUnitRandomY;
    /***
     * 滑块移动的距离
     */
    private float mUnitMoveDistance = 0;
    /***
     * 滑块图像
     */
    private Bitmap mUnitBp;
    /**
     * 验证位置图像
     */
    private Bitmap mShowBp;
    /**
     * 背景阴影图像
     */
    private Bitmap mShadeBp;
    /**
     * 是否需要旋转
     **/
    private boolean needRotate;
    /**
     * 旋转的角度
     */
    private int rotate;
    /**
     * 判断是否完成的偏差量，默认为10
     */
    public int DEFAULT_DEVIATE;
    /**
     * 判断是否重新绘制图像
     */
    private boolean isReSet = true;

    public void setListener(AuthenticationListener mListener) {
        this.mListener = mListener;
    }

    public ImageAuthenticationView(Context context) {
        this(context,null);
    }

    public ImageAuthenticationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageAuthenticationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageAuthenticationView);
        mSliderWidth = ta.getDimensionPixelOffset(R.styleable.ImageAuthenticationView_unitHeight, 0);
        mSliderHeight = ta.getDimensionPixelOffset(R.styleable.ImageAuthenticationView_unitHeight, 0);
        mUnitHeightScale = ta.getInteger(R.styleable.ImageAuthenticationView_unitHeightScale, 10);
        mUnitWidthScale = ta.getInteger(R.styleable.ImageAuthenticationView_unitWidthScale, 12);
        Drawable showBp = ta.getDrawable(R.styleable.ImageAuthenticationView_unitShowSrc);
        mShowBp = drawableToBitamp(showBp);
        Drawable shadeBp = ta.getDrawable(R.styleable.ImageAuthenticationView_unitShadeSrc);
        mShadeBp = drawableToBitamp(shadeBp);
        needRotate = ta.getBoolean(R.styleable.ImageAuthenticationView_needRotate, true);
        DEFAULT_DEVIATE = ta.getInteger(R.styleable.ImageAuthenticationView_deviate, 10);
        ta.recycle();
        //初始化
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //是否需要旋转
        if (needRotate) {
            rotate = (int) (Math.random() * 3) * 90;
        } else {
            rotate = 0;
        }
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if(drawable == null){
            return null;
        }

        if(drawable instanceof BitmapDrawable){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isReSet){
            mBitmap = getBaseBitmap();
            if (mSliderWidth == 0){
                mSliderWidth = mBitmap.getWidth() / mUnitWidthScale;
            }
            if (mSliderHeight == 0){
                mSliderHeight = mBitmap.getHeight() / mUnitHeightScale;
            }
            initXY();
            mUnitBp = Bitmap.createBitmap(mBitmap,mUnitRandomX,mUnitRandomY,mSliderWidth,mSliderHeight);
        }
        isReSet = false;
        canvas.drawBitmap(drawTagetBitmap(),mUnitRandomX,mUnitRandomY,mPaint);
        canvas.drawBitmap(drawResultBitmap(mUnitBp), mUnitMoveDistance, mUnitRandomY, mPaint);
    }

    private Bitmap drawResultBitmap(Bitmap bitmap) {
        // 绘制图片
        Bitmap shadeB;
        if (null != mShadeBp) {
            shadeB = handleBitmap(mShadeBp, mSliderWidth, mSliderHeight);
        } else {
            shadeB = handleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.puzzle_shade), mSliderWidth, mSliderHeight);
        }
        // 如果需要旋转图片,进行旋转,旋转后为了和画布大小保持一致,避免出现图像显示不全,需要重新缩放比例
        if (needRotate) {
            shadeB = handleBitmap(rotateBitmap(rotate, shadeB), mSliderWidth, mSliderHeight);
        }
        Bitmap resultBmp = Bitmap.createBitmap(mSliderWidth, mSliderHeight,
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(resultBmp);
        canvas.drawBitmap(shadeB, new Rect(0, 0, mSliderWidth, mSliderHeight),
                new Rect(0, 0, mSliderWidth, mSliderHeight), paint);
        // 选择交集去上层图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(bitmap, new Rect(0, 0, mSliderWidth, mSliderHeight),
                new Rect(0, 0, mSliderWidth, mSliderHeight), paint);
        return resultBmp;
    }

    /**
     * 绘制阴影部分
     * @return
     */
    private Bitmap drawTagetBitmap() {
        Bitmap bitmap;
        if (mShowBp != null){
            bitmap = handleBitmap(mShowBp,mSliderWidth,mSliderHeight);
        }else {
            bitmap = handleBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.puzzle_show),mSliderWidth,mSliderHeight);
        }
        if (needRotate){
            bitmap = handleBitmap(rotateBitmap(rotate,bitmap),mSliderWidth,mSliderHeight);
        }
        return bitmap;
    }

    private Bitmap rotateBitmap(int rotate, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    private Bitmap handleBitmap(Bitmap bitmap, int mSliderWidth, int mSliderHeight) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float sx = (float) mSliderWidth / w;
        float sy = (float) mSliderHeight / h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, w,
                h, matrix, true);
        return resizeBmp;
    }

    private void initXY() {
        mUnitRandomX = (int) (Math.random()*(mBitmap.getWidth() - mSliderWidth));
        mUnitRandomY = (int) (Math.random() * (mBitmap.getHeight() - mSliderHeight));
        // 防止生成的位置距离太近
        if (mUnitRandomX <= mBitmap.getWidth() / 2) {
            mUnitRandomX = mUnitRandomX + mBitmap.getWidth() / 4;
        }
        // 防止生成的X坐标截图时导致异常
        if (mUnitRandomX + mSliderWidth > getWidth()) {
            initXY();
            return;
        }
    }

    private Bitmap getBaseBitmap() {
        Bitmap b = drawableToBitamp(getDrawable());
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        scaleX = getWidth() * 1.0f / b.getWidth();
        scaleY = getHeight() * 1.0f / b.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX,scaleY);
        Bitmap bitmap = Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(),matrix,true);
        return bitmap;
    }

    /**
     * 验证是否拼接成功
     */
    public void testPuzzle() {
        if (Math.abs(mUnitMoveDistance - mUnitRandomX) <= DEFAULT_DEVIATE) {
            if (null != mListener) {
                mListener.onSuccess();
            }
        } else {
            if (null != mListener) {
                mListener.onFail();
            }
        }
    }

    /**
     * 滑块移动距离
     *
     * @param distance
     */
    public void setUnitMoveDistance(float distance) {
        mUnitMoveDistance = distance;
        // 防止滑块滑出图片
        if (mUnitMoveDistance > mBitmap.getWidth() - mSliderWidth) {
            mUnitMoveDistance = mBitmap.getWidth() - mSliderWidth;
        }
        invalidate();
    }

    /**
     * 重置
     */
    public void reSet() {
        isReSet = true;
        mUnitMoveDistance = 0;
        if (needRotate) {
            rotate = (int) (Math.random() * 3) * 90;
        } else {
            rotate = 0;
        }
        invalidate();
    }

    /**
     * 获取每次滑动的平均偏移值
     *
     * @return
     */
    public float getAverageDistance(int max) {
        return (float) (mBitmap.getWidth() - mSliderWidth) / max;
    }

}
