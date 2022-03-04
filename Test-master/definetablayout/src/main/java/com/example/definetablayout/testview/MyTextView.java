package com.example.definetablayout.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextView extends View {
    /**
     * 创建实例的时候调用
     * @param context
     */
    public MyTextView(Context context) {
        super(context);
    }

    /**
     * 在布局里面使用的时候调用
     * @param context
     * @param attrs
     */
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 在布局里面使用，且含有style
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 共有三种模式
         * EXACTLY ：对应固定的宽高值,match_parent
         * AT_MOST：warp_parent,
         * UNSPECIFIED:ScrollView与ListView结合冲突
         */
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
