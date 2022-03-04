package com.example.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLinearLayout extends LinearLayout {
    private final String TAG = "zzf";
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("zzf", "=================ViewGrop dispatchTouchEvent Action: "
                + Util.getAction(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("zzf", "=================ViewGrop onInterceptTouchEvent Action: "
                + Util.getAction(ev));
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("zzf", "=================ViewGrop onTouchEvent Action: "
                + Util.getAction(event));
        return false;
    }
}
