package com.example.animation.Interpolator;


import android.util.Log;
import android.view.animation.Interpolator;

public class DecelerateAccelerateInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        Log.e("zzf","input :"+ input);
        float result;
        if (input < 0.5){
            result = (float) ((Math.sin(Math.PI * input)) /2);
        }else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return result;
    }
}
