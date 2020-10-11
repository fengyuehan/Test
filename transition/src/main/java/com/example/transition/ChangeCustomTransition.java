package com.example.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.ViewGroup;

import androidx.transition.Transition;
import androidx.transition.TransitionValues;


public class ChangeCustomTransition extends Transition {

    private static String PROPNAME_CUSTOM_RATIO = "xiaweizi:ChangeCustom:ratio";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues == null || !(transitionValues.view instanceof TransitionView)) return;
        TransitionView view = (TransitionView) transitionValues.view;
        transitionValues.values.put(PROPNAME_CUSTOM_RATIO, view.getRatio());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        if (!(endValues.view instanceof TransitionView)) {
            return super.createAnimator(sceneRoot, startValues, endValues);
        }
        final TransitionView endView = (TransitionView) endValues.view;
        final float startRatio = (float) startValues.values.get(PROPNAME_CUSTOM_RATIO);
        final float endRatio = (float) endValues.values.get(PROPNAME_CUSTOM_RATIO);
        ObjectAnimator animator = ObjectAnimator.ofFloat(endView, "ratio", startRatio, endRatio);
        animator.setDuration(300);
        return animator;
    }
}
