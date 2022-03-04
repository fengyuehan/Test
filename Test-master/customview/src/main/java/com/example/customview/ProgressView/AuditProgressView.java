package com.example.customview.ProgressView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class AuditProgressView extends View {
    /**
     *标记该步骤是否完成
     */
    private boolean mIsCurrentComplete;
    /**
     * 标记下一个步骤是否完成
     */
    private boolean mIsNextComplete;
    /**
     * 绘制图片
     */
    private Bitmap audit_drawable;
    /**
     * 绘制文字
     */
    private String text;
    /**
     * 宽高
     */
    private int width,height;
    private Paint mPaint;
    /**
     * 图片距离顶部的距离
     */
    private int paddingTop;
    /**
     * 有几个步骤
     */
    private int stepCount;
    // 是否是第一步 第一步不需要 画左边线条
    private boolean mIsFirstStep;
    // 是否是最后一步 最后一步 不需要画右边线条
    private boolean mIsLastStep;

    public AuditProgressView(Context context) {
        super(context);
    }

    public AuditProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AuditProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuditProgressView);
        mIsCurrentComplete = typedArray.getBoolean(R.styleable.AuditProgressView_apv_isCurrentComplete,false);
        mIsNextComplete = typedArray.getBoolean(R.styleable.AuditProgressView_apv_isNextComplete,false);
        mIsFirstStep = typedArray.getBoolean(R.styleable.AuditProgressView_apv_isFirstStep,false);
        mIsLastStep = typedArray.getBoolean(R.styleable.AuditProgressView_apv_isLastStep,false);
        stepCount = typedArray.getInteger(R.styleable.AuditProgressView_apv_stepCount,2);
        text = typedArray.getString(R.styleable.AuditProgressView_apv_text);
        typedArray.recycle();
        paddingTop = dp2px(getContext(),22);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY){
            width = getDisplayMetrics(getContext()).widthPixels / stepCount;
        }
        if (heightMode != MeasureSpec.EXACTLY){
            heigh = dp2px(getContext(),90);
        }
        setMeasuredDimension(width,heigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsCurrentComplete){
            audit_drawable = BitmapFactory.decodeResource(getResources(),R.drawable.ic_radio_button_unchecked_black_24dp);
        }else {
            audit_drawable = BitmapFactory.decodeResource(getResources(),R.drawable.ic_radio_button_checked_black_24dp);
        }

        width = getWidth();
        height = getHeight();

    }

    /**
     * 获取屏幕Metrics参数
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
