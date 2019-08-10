package com.example.otcchatkeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class OtcEditText extends EditText {
    public OtcEditText(Context context) {
        this(context, null);
    }

    public OtcEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OtcEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(oldh > 0 && mOnSizeChangedListener != null){
            mOnSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    public void setGravity(int gravity) {
        try {
            super.setGravity(gravity);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.setGravity(gravity);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        try {
            super.setText(text, type);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(text.toString());
        }
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(mOnBackKeyClickListener != null){
            mOnBackKeyClickListener.onBackKeyClick();
        }
        return super.dispatchKeyEventPreIme(event);
    }

    public interface OnBackKeyClickListener {
        void onBackKeyClick();
    }

    OnBackKeyClickListener mOnBackKeyClickListener;

    public void setOnBackKeyClickListener(OnBackKeyClickListener mOnBackKeyClickListener){
      this.mOnBackKeyClickListener = mOnBackKeyClickListener;
    }
    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }
    OnSizeChangedListener mOnSizeChangedListener;

    public void setOnSizeChangedListener(OnSizeChangedListener mOnSizeChangedListener) {
        this.mOnSizeChangedListener = mOnSizeChangedListener;
    }

}
