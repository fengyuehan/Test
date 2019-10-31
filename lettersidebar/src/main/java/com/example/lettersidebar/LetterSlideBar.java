package com.example.lettersidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author zzf
 * @date 2019/7/25/025
 * 描述：
 */
public class LetterSlideBar extends View {
    private Context mContext;
    //画笔
    private Paint mPaint;
    private String[] mLetters = {"#","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //手指当前触摸的字母
    private String mTouchLetter;
    //手指是否触摸
    private boolean mCurrentIsTouch;
    // 设置触摸监听
    private SlideBarTouchListener mTouchListener;

    public void setOnSideBarTouchListener(SlideBarTouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    public LetterSlideBar(Context context) {
        this(context,null);mContext = context;

        initView();
    }

    public LetterSlideBar(Context context,AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

    }
    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(CommonUtil.sp2px(this.getContext(), 11));
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float singleHeight = getHeight() / mLetters.length;
        for (int i = 0; i < mLetters.length; i++){
            String letter = mLetters[i];
            Rect rect = new Rect();
            mPaint.getTextBounds(letter,0,letter.length(),rect);
            float measureTextWidth = rect.width();
            //获取内容的宽度
            int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            float x = getPaddingLeft() + (contentWidth - measureTextWidth)/2;

            //计算基线的位置
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float baseLine = singleHeight / 2 + (singleHeight * i ) + (fontMetrics.bottom - fontMetrics.top)/2 -fontMetrics.bottom;

            if (mLetters[i].equals(mTouchLetter) && mCurrentIsTouch) {
                mPaint.setTextSize(CommonUtil.sp2px(mContext, 20));
                mPaint.setColor(Color.RED);
                canvas.drawText(letter, x, baseLine, mPaint);
            } else {
                mPaint.setTextSize(CommonUtil.sp2px(mContext, 11));
                mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_33));
                canvas.drawText(letter, x, baseLine, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentMoveY = event.getY();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0 ){
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length -1){
                    currentPosition = mLetters.length -1;
                }

                mTouchLetter = mLetters[currentPosition];
                mCurrentIsTouch = true;
                if (mTouchListener != null){
                    mTouchListener.onTouch(mTouchLetter,true);
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentIsTouch = false;
                if (mTouchListener != null){
                    mTouchListener.onTouch(mTouchLetter,false);
                }
                break;
        }
        invalidate();
        return true;
    }
}
