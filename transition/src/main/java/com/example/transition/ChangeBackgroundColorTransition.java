package com.example.transition;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.transition.Transition;
import androidx.transition.TransitionValues;


public class ChangeBackgroundColorTransition extends Transition {

    private static String PROPNAME_COLOR = "xiaweizi:ChangeBackgroundColor:color";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues == null) return;
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_COLOR, view.getBackground());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View endView = endValues.view;
        ColorDrawable startColorDrawable = (ColorDrawable) startValues.values.get(PROPNAME_COLOR);
        ColorDrawable endColorDrawable = (ColorDrawable) endValues.values.get(PROPNAME_COLOR);
        if (startColorDrawable == null || endColorDrawable == null) return super.createAnimator(sceneRoot, startValues, endValues);
        final int startColor = startColorDrawable.getColor();
        final int endColor = endColorDrawable.getColor();
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                endView.setBackgroundColor(animatedValue);
            }
        });
        return animator;
    }
}
