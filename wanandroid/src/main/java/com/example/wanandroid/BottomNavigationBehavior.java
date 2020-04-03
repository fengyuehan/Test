package com.example.wanandroid;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<View> {
    private ObjectAnimator outAnimator, inAnimator;

    public BottomNavigationBehavior(){
        super();
    }
    public BottomNavigationBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    //垂直滑动
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0){
            //上滑隐藏
            if (outAnimator == null){
                outAnimator = ObjectAnimator.ofFloat(child,"translationY",0,child.getHeight());
                outAnimator.setDuration(200);
                //outAnimator.setInterpolator(new LinearInterpolator());
            }
            if (!outAnimator.isRunning() && child.getTranslationY() <= 0){
                outAnimator.start();
            }
        }else {
            //下滑显示
            if (inAnimator == null){
                inAnimator = ObjectAnimator.ofFloat(child,"translationY",child.getHeight(),0);
                inAnimator.setDuration(200);
                //outAnimator.setInterpolator(new LinearInterpolator());
            }
            if (!inAnimator.isRunning() && child.getTranslationY() > 0/*> child.getHeight()*/){
                inAnimator.start();
            }
        }
    }
}
