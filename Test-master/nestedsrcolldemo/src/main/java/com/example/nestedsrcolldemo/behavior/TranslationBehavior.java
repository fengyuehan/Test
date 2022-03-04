package com.example.nestedsrcolldemo.behavior;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * author : zhangzf
 * date   : 2021/5/24
 * desc   :
 */
public class TranslationBehavior extends FloatingActionButton.Behavior {

    //必须添加这个构造函数，不然会崩了

    /**
     * parseBehavior()：c = clazz.getConstructor(CONSTRUCTOR_PARAMS);
     * static final Class<?>[] CONSTRUCTOR_PARAMS = new Class<?>[] {
     *             Context.class,
     *             AttributeSet.class
     *     };
     *  需要解析这两个参数的构造函数
     * @param context
     * @param attrs
     */

    public TranslationBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull  CoordinatorLayout coordinatorLayout, @NonNull  FloatingActionButton child, @NonNull  View directTargetChild, @NonNull  View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    private boolean isOut = false;
    @Override
    public void onNestedScroll(@NonNull  CoordinatorLayout coordinatorLayout, @NonNull  FloatingActionButton child, @NonNull  View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        if (dyConsumed > 0){
            if (!isOut){
                int mY = ((CoordinatorLayout.LayoutParams)child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
                child.animate().translationY(mY).setDuration(1000).start();
                isOut = true;
            }
        }else {
            if (isOut){
                child.animate().translationY(0).setDuration(1000).start();
                isOut = false;
            }

        }
    }
}
