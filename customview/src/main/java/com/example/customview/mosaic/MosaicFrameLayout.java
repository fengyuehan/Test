package com.example.customview.mosaic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.customview.doodle.IMGImage;
import com.example.customview.doodle.IMGPath;
import com.example.customview.doodle.Pen;


/**
 * 马赛克，跟涂鸦差不多
 */
public class MosaicFrameLayout extends FrameLayout {

    private String TAG = MosaicFrameLayout.class.getSimpleName();
    private IMGImage mImage = new IMGImage();
    private Paint mDoodlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 马赛克画刷
    private Paint mMosaicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 钢笔包括所绘制的路径
    private Pen mPen = new Pen();

    public MosaicFrameLayout(Context context) {
        this(context, null, 0);
    }

    public MosaicFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MosaicFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    {
        // 涂鸦画刷
        mDoodlePaint.setStyle(Paint.Style.STROKE);
        mDoodlePaint.setStrokeWidth(IMGPath.BASE_DOODLE_WIDTH);
        mDoodlePaint.setColor(Color.RED);
        mDoodlePaint.setPathEffect(new CornerPathEffect(IMGPath.BASE_DOODLE_WIDTH));
        mDoodlePaint.setStrokeCap(Paint.Cap.ROUND);
        mDoodlePaint.setStrokeJoin(Paint.Join.ROUND);

        // 马赛克画刷
        mMosaicPaint.setStyle(Paint.Style.STROKE);
        mMosaicPaint.setStrokeWidth(IMGPath.BASE_MOSAIC_WIDTH);
        mMosaicPaint.setColor(Color.BLACK);
        mMosaicPaint.setPathEffect(new CornerPathEffect(IMGPath.BASE_MOSAIC_WIDTH));
        mMosaicPaint.setStrokeCap(Paint.Cap.ROUND);
        mMosaicPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void onDrawImages(Canvas canvas) {
        // 图片
        mImage.onDrawImage(canvas);

        // 马赛克
        if (!mImage.isMosaicEmpty() || (!mPen.isEmpty())) {
            int count = mImage.onDrawMosaicsPath(canvas);

            mDoodlePaint.setStrokeWidth(IMGPath.BASE_MOSAIC_WIDTH);
            canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            canvas.drawPath(mPen.getPath(), mDoodlePaint);
            canvas.restore();

            mImage.onDrawMosaic(canvas, count);
            canvas.save();
            canvas.restore();
        }
    }

    /**
     * 处理触屏事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTouch(event);
    }

    /**
     * 处理触屏事件.详情
     */
    boolean onTouch(MotionEvent event) {
        return onTouchPath(event);
    }

    /**
     * 画笔线
     */
    private boolean onTouchPath(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 钢笔初始化
                return onPathBegin(event);
            case MotionEvent.ACTION_MOVE:
                // 画线
                return onPathMove(event);
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 画线完成,绘制路径加入到绘制列表
                return mPen.isIdentity(event.getPointerId(0)) && onPathDone();
        }
        return false;
    }

    /**
     * 钢笔初始化
     */
    private boolean onPathBegin(MotionEvent event) {
        mPen.reset(event.getX(), event.getY());
        mPen.setIdentity(event.getPointerId(0));
        return true;
    }

    /**
     * 画线
     */
    private boolean onPathMove(MotionEvent event) {
        if (mPen.isIdentity(event.getPointerId(0))) {
            mPen.lineTo(event.getX(), event.getY());
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * 画线完成,绘制路径加入到绘制列表
     */
    private boolean onPathDone() {
        if (mPen.isEmpty()) {
            return false;
        }
        mImage.addPathMosaics(mPen.toPath(), getScrollX(), getScrollY());
        mPen.reset();
        invalidate();
        return true;
    }

}
