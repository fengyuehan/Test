package com.example.glidepicasso;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * author : zhangzf
 * date   : 2021/3/26
 * desc   :
 */
public class MyViewTarget extends ViewTarget<CustomView, Drawable> {
    public MyViewTarget(CustomView customView) {
        super(customView);
    }
    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

    }
}
