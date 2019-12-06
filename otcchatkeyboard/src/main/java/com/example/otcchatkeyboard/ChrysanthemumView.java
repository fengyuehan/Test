package com.example.otcchatkeyboard;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @ClassName ChrysanthemumView
 * @Description TODO
 * @Author user
 * @Date 2019/8/16
 * @Version 1.0
 */
public class ChrysanthemumView extends View {
    /**
     * 线圆角及宽度
     */
    private int mLineBold;
    /**
     * 线条开始颜色 默认白色
     */
    private int mStartColor = Color.parseColor("#FFFFFF");
    /**
     * 线条结束颜色 默认灰色
     */
    private int mEndColor = Color.parseColor("#9B9B9B");
    /**
     * view的宽度 高度
     */
    private int mWidth;
    /**
     * view的高度
     */
    private int mHeight;
    /**
     * 线条长度
     */
    private int mLineLength;
    /**
     * 线条个数 默认12条
     */
    private int mLineCount = 12;
    /**
     * 背景画笔
     */
    private Paint mBgPaint;
    /**
     * 渐变颜色
     */
    private int[] mColors;
    /**
     * 动画是否已开启
     */
    private boolean isAnimationStart;
    /**
     * 开始index
     */
    private int mStartIndex;
    /**
     * 动画
     */
    private ValueAnimator mValueAnimator;

    public ChrysanthemumView(Context context) {
        this(context, null);
    }

    public ChrysanthemumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChrysanthemumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttrs(context, attrs);
        initPaint();
        initColor();
    }

    /**
     * 初始化颜色
     */
    private void initColor() {
        // 渐变色计算类
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        // 初始化对应空间
        mColors = new int[mLineCount];
        // 获取对应的线颜色 此处由于是白色起头 黑色结尾所以需要反过来计算 即线的数量到0的数量递减 对应的ValueAnimator 是从0到线的数量-1递增
        for (int i = mLineCount; i > 0; i--) {
            float alpha = (float) i / mLineCount;
            mColors[mLineCount - i] = (int) argbEvaluator.evaluate(alpha, mStartColor, mEndColor);
        }
    }

    /**
     * 加载自定义的属性
     */
    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChrysanthemumView);
        mStartColor = array.getColor(R.styleable.ChrysanthemumView_startColor, mStartColor);
        mEndColor = array.getColor(R.styleable.ChrysanthemumView_endColor, mEndColor);
        mLineCount = array.getInt(R.styleable.ChrysanthemumView_lineCount, mLineCount);
        //TypedArray回收
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mBgPaint = new Paint();
        //使得画笔更加圆滑
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeJoin(Paint.Join.ROUND);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取view的宽度 默认40dp
        mWidth = getViewSize(dp2px(getContext(), 40), widthMeasureSpec);
        // 获取view的高度 默认40dp
        mHeight = getViewSize(dp2px(getContext(), 40), heightMeasureSpec);
        // 使宽高保持一致
        mHeight = mWidth = Math.min(mWidth, mHeight);
        // 获取线的长度
        mLineLength = mWidth / 5;
        // 获取线圆角及宽度
        mLineBold = mWidth / mLineCount;
        // 设置线的圆角及宽度
        mBgPaint.setStrokeWidth(mLineBold);
        setMeasuredDimension(mWidth,mHeight);
    }

    /**
     * 测量模式       表示意思
     * UNSPECIFIED  父容器没有对当前View有任何限制，当前View可以任意取尺寸
     * EXACTLY      当前的尺寸就是当前View应该取的尺寸
     * AT_MOST      当前尺寸是当前View能取的最大尺寸
     *
     * @param defaultSize 默认大小
     * @param measureSpec 包含测量模式和宽高信息
     * @return 返回View的宽高大小
     */
    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        //获取测量模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取大小
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY:
                //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
            default:
        }
        return viewSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取半径
        int r = mWidth / 2;
        // 绘制前先旋转一个角度，使最顶上开始位置颜色与开始颜色匹配
        canvas.rotate(360f / mLineCount, r, r);
        for (int i = 0; i < mLineCount; i++) {
            // 获取颜色下标
            int index = (mStartIndex + i) % mLineCount;
            // 设置颜色
            mBgPaint.setColor(mColors[index]);
            // 绘制线条 mLineBold >> 1 == mLineBold / 2 使居中显示
            canvas.drawLine(r, mLineBold >> 1, r, (mLineBold >> 1) + mLineLength, mBgPaint);
            // 旋转角度
            canvas.rotate(360f / mLineCount, r, r);
        }
    }

    /**
     * 开始动画
     *
     * @param duration 动画时间
     */
    public void startAnimation(int duration) {

        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofInt(mLineCount, 0);
            mValueAnimator.setDuration(duration);
            mValueAnimator.setTarget(0);
            mValueAnimator.setRepeatCount(-1);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 此处会回调3次 需要去除后面的两次回调
                    if (mStartIndex != (int) animation.getAnimatedValue()) {
                        mStartIndex = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                }
            });
        }
        mValueAnimator.start();
        isAnimationStart = true;
    }

    /**
     * 开始动画 时间为1800毫秒一次
     */
    public void startAnimation() {
        startAnimation(1800);
    }

    /**
     * 结束动画
     */
    public void stopAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            isAnimationStart = false;
        }
    }

    /**
     * 是否在动画中
     *
     * @return 是为 true 否则 false
     */
    public boolean isAnimationStart() {
        return isAnimationStart;
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpVal   dpVal
     * @return px
     */

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 防止内存溢出 未结束动画并退出页面时，需使用此函数，或手动释放此view
     */
    public void detachView() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
            isAnimationStart = false;
        }
    }

}
