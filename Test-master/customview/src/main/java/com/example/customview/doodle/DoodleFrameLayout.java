package com.example.customview.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;


/**
 * 涂鸦
 * 同样是通过onTouch切口切入
 * 每次的event都是不同id触发，在每一次的触发onTouchEvent都重新用钢笔Pen存储path,onDraw绘画的时候会根据path来生成相应路径
 * 要加多一个特殊做法的是，在每次onTouchEvent结束后，重新根据坐标重新生成一次path（详情看onPathDone）
 * 因为随着每次扩大缩小，整个长宽会改变，所以我们绘画时，生成的路径也能随着长宽改变
 */
public class DoodleFrameLayout extends FrameLayout {

    private String TAG = DoodleFrameLayout.class.getSimpleName();
    private IMGImage mImage = new IMGImage();
    // 画笔
    private Paint mDoodlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 钢笔包括所绘制的路径
    private Pen mPen = new Pen();

    public DoodleFrameLayout(Context context) {
        this(context, null, 0);
    }

    public DoodleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    }

    private void onDrawImages(Canvas canvas) {
        // 图片
        mImage.onDrawImage(canvas);

        // 最后根据全部路径涂鸦
        mImage.onDrawDoodles(canvas);

        mDoodlePaint.setColor(mPen.getColor());
        mDoodlePaint.setStrokeWidth(IMGPath.BASE_DOODLE_WIDTH * mImage.getScale());
        canvas.save();
        canvas.translate(getScrollX(), getScrollY());
        canvas.drawPath(mPen.getPath(), mDoodlePaint);
        canvas.restore();
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
        mImage.addPath(mPen.toPath(), getScrollX(), getScrollY());
        mPen.reset();
        invalidate();
        return true;
    }

}
