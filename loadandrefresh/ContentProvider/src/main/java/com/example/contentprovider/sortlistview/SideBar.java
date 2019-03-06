package com.example.contentprovider.sortlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };

    private int choose = -1;
    private Paint mPaint;
    private TextView mTextView;

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    public SideBar(Context context) {
        this(context,null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        //* Typeface.DEFAULT //常规字体类型
        //
        //  * Typeface.DEFAULT_BOLD //黑体字体类型
        //
        //  * Typeface.MONOSPACE //等宽字体类型
        //
        //  * Typeface.SANS_SERIF //sans serif字体类型
        //
        //  * Typeface.SERIF //serif字体类型
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);//设置粗体
        mPaint.setTextSize(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        int singleHeight = height / b.length;

        for (int i = 0;i<b.length;i++){
            if (i == choose){
                mPaint.setColor(Color.parseColor("#3399ff"));
                mPaint.setFakeBoldText(true);
            }
            float xPos = width/2 - mPaint.measureText(b[i])/2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i],xPos,yPos,mPaint);
            mPaint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);

        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (mTextView != null){
                    mTextView.setVisibility(INVISIBLE);
                }
                break;
            default:
                setBackgroundDrawable(new ColorDrawable(0x00FF5500));
                if (oldChoose != c){
                    if (c>=0 && c < b.length){
                        if (listener != null){
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextView != null){
                            mTextView.setText(b[c]);
                            mTextView.setVisibility(VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener{
        void onTouchingLetterChanged(String s);
    }
}
