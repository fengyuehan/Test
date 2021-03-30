package com.example.customview.doodle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


/**
 * 这是个最基本的绘制图片的类
 */
public class IMGImage {

    private String TAG = IMGImage.class.getSimpleName();
    public Bitmap mImage, mMosaicImage;
    public Paint mPaint,mMosaicPaint;

    /**
     * 完整图片边框
     */
    public RectF mFrame = new RectF();

    // region 裁剪

    /**
     * 是否取消动画
     */
    private boolean isAnimCanceled = false;

    /**
     * 是否冻结的
     */
    private boolean isFreezing = false;

    /**
     * 裁剪图片边框（显示的图片区域）
     */
    private RectF mClipFrame = new RectF();

    /**
     * 裁剪窗口
     */
    private IMGClipWindow mClipWin = new IMGClipWindow();

    private float mRotate = 0, mTargetRotate = 0;

    private IMGMode mMode = IMGMode.NONE;

    private RectF mBackupClipFrame = new RectF();

    private float mBackupClipRotate = 0;

    // endregion 裁剪


    public void setMode(IMGMode mode) {
        Log.d(TAG, "setMode");

        if (this.mMode == mode) return;

        if (mode == IMGMode.CLIP) {
            setFreezing(true);
        }

        this.mMode = mode;

        if (mMode == IMGMode.CLIP) {

            // 备份裁剪前Clip 区域
            mBackupClipRotate = getRotate();
            mBackupClipFrame.set(mClipFrame);

            float scale = 1 / getScale();
            M.setTranslate(-mFrame.left, -mFrame.top);
            M.postScale(scale, scale);
            M.mapRect(mBackupClipFrame);

            // 重置裁剪区域
            mClipWin.reset(mClipFrame, getTargetRotate());
        } else {

            if (mMode == IMGMode.MOSAIC) {
                makeMosaicBitmap();
            }

            mClipWin.setClipping(false);
        }
    }

    /**
     * 矩阵
     */
    public Matrix M = new Matrix();

    /**
     * 可视区域，无Scroll 偏移区域
     */
    private RectF mWindow = new RectF();

    /**
     * 涂鸦路径
     */
    private List<IMGPath> mDoodles = new ArrayList<>();
    /**
     * 马赛克路径
     */
    private List<IMGPath> mMosaics = new ArrayList<>();

    {
        // Doodle&Mosaic 's paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(IMGPath.BASE_DOODLE_WIDTH);
        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new CornerPathEffect(IMGPath.BASE_DOODLE_WIDTH));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }

        this.mImage = bitmap;

        // 清空马赛克图层
        if (mMosaicImage != null) {
            mMosaicImage.recycle();
        }
        this.mMosaicImage = null;

        makeMosaicBitmap();

        mFrame.set(0, 0, mImage.getWidth(), mImage.getHeight());
    }

    public void onDrawImage(Canvas canvas) {
        // 在mFrame该边框绘制图片
        canvas.drawBitmap(mImage, null, mFrame, null);
    }

    /**
     * 绘制马赛克路径
     */
    public int onDrawMosaicsPath(Canvas canvas) {
        Log.d(TAG, "onDrawMosaicsPath");
        // 所有状态都保存
        int layerCount = canvas.saveLayer(mFrame, null, Canvas.ALL_SAVE_FLAG);

        if (!isMosaicEmpty()) {
            canvas.save();
            float scale = getScale();
            canvas.translate(mFrame.left, mFrame.top);
            canvas.scale(scale, scale);
            for (IMGPath path : mMosaics) {
                path.onDrawMosaic(canvas, mPaint);
            }
            canvas.restore();
        }

        return layerCount;
    }

    public void onScaleBegin() {
    }

    public void onScaleEnd() {
    }

    public void onScale(float factor, float focusX, float focusY) {
        if (factor == 1f) return;

        // 针对这个有图片的边框进行比例缩放
        M.setScale(factor, factor, focusX, focusY);
        M.mapRect(mFrame);
    }

    /**
     * 涂鸦
     */
    public void onDrawDoodles(Canvas canvas) {
        canvas.save();
        float scale = getScale();
        canvas.translate(mFrame.left, mFrame.top);
        canvas.scale(scale, scale);
        for (IMGPath path : mDoodles) {
            path.onDrawDoodle(canvas, mPaint);
        }
        canvas.restore();
    }

    /**
     * 绘制马赛克
     */
    public void onDrawMosaic(Canvas canvas, int layerCount) {
        Log.d(TAG, "onDrawMosaic");
        canvas.drawBitmap(mMosaicImage, null, mFrame, mMosaicPaint);
        canvas.restoreToCount(layerCount);
    }

    /**
     * addPath方法详解：
     * M.setTranslate(sx, sy);
     * 矩阵平移到跟view的xy轴一样,注意，是getScrollX()和getScrolly()
     * <p>
     * M.postTranslate(-mFrame.left, -mFrame.top);
     * 如果按照getScrollX()直接绘制进手机屏幕上是会出格的，因为view能缩放到比手机屏幕还要大，那么就需要减掉mFrame的x和y，剩下的就是手机绘制的正确的点
     */
    public void addPath(IMGPath path, float sx, float sy) {
        if (path == null) return;

        path.setMode(IMGMode.DOODLE);

        float scale = 1f / getScale();
        M.setTranslate(sx, sy);
        M.postTranslate(-mFrame.left, -mFrame.top);
        M.postScale(scale, scale);
        // 矩阵变换
        path.transform(M);

        mDoodles.add(path);
    }

    /**
     * 方法跟addPath一样，只是区别加入到马赛克路径列表并且加粗了路径
     */
    public void addPathMosaics(IMGPath path, float sx, float sy) {
        if (path == null) return;

        path.setMode(IMGMode.MOSAIC);

        float scale = 1f / getScale();
        M.setTranslate(sx, sy);
        M.postTranslate(-mFrame.left, -mFrame.top);
        M.postScale(scale, scale);
        // 矩阵变换
        path.transform(M);

        path.setWidth(path.getWidth() * scale);
        mMosaics.add(path);
    }

    /**
     * 1 * view缩放后的宽度 / 图片固定宽度 = 缩放比例
     */
    public float getScale() {
        return 1f * mFrame.width() / mImage.getWidth();
    }

    public boolean isMosaicEmpty() {
        Log.d(TAG, "isMosaicEmpty");
        return mMosaics.isEmpty();
    }

    public RectF getClipFrame() {
        Log.d(TAG, "getClipFrame");
        return mClipFrame;
    }

    /**
     * 创建同样的马赛克图和马赛克画笔
     */
    private void makeMosaicBitmap() {
        Log.d(TAG, "makeMosaicBitmap");
        if (mMosaicImage != null || mImage == null) {
            return;
        }

        // 原图的宽高相除64
        int w = Math.round(mImage.getWidth() / 64f);
        int h = Math.round(mImage.getHeight() / 64f);

        // 取最大值，即不能小于8
        w = Math.max(w, 8);
        h = Math.max(h, 8);

        // 马赛克画刷，注意是SRC_IN，刷子刷后就显示相应的马赛克层了
        if (mMosaicPaint == null) {
            mMosaicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMosaicPaint.setFilterBitmap(false);
            mMosaicPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }

        // 创建马赛克图
        mMosaicImage = Bitmap.createScaledBitmap(mImage, w, h, false);
    }

    // region 裁剪

    public void onDrawClip(Canvas canvas, float scrollX, float scrollY) {
        Log.d(TAG, "onDrawClip");
        if (mMode == IMGMode.CLIP) {
            mClipWin.onDraw(canvas);
        }
    }

    /**
     * 这是只考虑矩阵区域的测试
     */
    public void onDrawClipTest(Canvas canvas, float scrollX, float scrollY) {
        Log.d(TAG, "onDrawClip");
        if (mMode == IMGMode.CLIP) {
            mClipWin.onDrawTest(canvas);
        }
    }

    public float getRotate() {
        Log.d(TAG, "getRotate");
        return mRotate;
    }

    public float getTargetRotate() {
        Log.d(TAG, "getTargetRotate");
        return mTargetRotate;
    }

    /**
     * 是否冻结的
     */
    public boolean isFreezing() {
        Log.d(TAG, "isFreezing");
        return isFreezing;
    }

    private void setFreezing(boolean freezing) {
        Log.d(TAG, "setFreezing");
        if (freezing != isFreezing) {
            rotateStickers(freezing ? -getRotate() : getTargetRotate());
            isFreezing = freezing;
        }
    }

    private void rotateStickers(float rotate) {
        Log.d(TAG, "rotateStickers");
        M.setRotate(rotate, mClipFrame.centerX(), mClipFrame.centerY());
    }

    public void onHomingStart(boolean isRotate) {
        Log.d(TAG, "onHomingStart");
        isAnimCanceled = false;
    }

    // endregion 裁剪

}
