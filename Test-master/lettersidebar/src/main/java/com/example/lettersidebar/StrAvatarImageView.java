package com.example.lettersidebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by yf on 2018/1/31.
 * 描述：字符头像
 */

@SuppressLint("AppCompatCustomView")
public class StrAvatarImageView extends RoundedImageView {

    /*private static final int[] colors = {
            0xff1abc9c, 0xff16a085, 0xfff1c40f, 0xfff39c12, 0xff2ecc71,
            0xff27ae60, 0xffe67e22, 0xffd35400, 0xff3498db, 0xff2980b9,
            0xffe74c3c, 0xffc0392b, 0xff9b59b6, 0xff8e44ad, 0xffbdc3c7,
            0xff34495e, 0xff2c3e50, 0xff95a5a6, 0xff7f8c8d, 0xffec87bf,
            0xffd870ad, 0xfff69785, 0xff9ba37e, 0xffb49255, 0xffb49255, 0xffa94136
    };*/

    private static final int[] colors = {
            0xff78e1e7, 0xffffc365, 0xff6b9ff7, 0xfff78481, 0xffe491f9,
            0xff7fcaf5
    };

    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    private Rect mRect;
    private String mText;

    public StrAvatarImageView(Context context) {
        super(context);
        initView();
    }

    public StrAvatarImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StrAvatarImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(CommonUtil.sp2px(getContext(), 14));
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mText)) {
            //int color = colors[(int) (Math.random() * 26)];
            int color = colors[mText.hashCode() % 6];
            // 画圆
            mBackgroundPaint.setColor(color);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mBackgroundPaint);
            // 写字
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextSize(getWidth() * 0.4f);
            mTextPaint.setStrokeWidth(2.5f);
            mTextPaint.getTextBounds(mText, 0, 1, mRect);
            // 垂直居中
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
            // 左右居中
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mText, getWidth() / 2, baseline, mTextPaint);
        }
    }

    /**
     * @param text 要写的字
     */
    public void setText(@NonNull String text) {
        /*if (text == null) {
            throw new NullPointerException("text内容不能为null");
        }*/
        mText = text;
        // 重绘
        invalidate();
    }
}
