package com.example.glidepicasso;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * author : zhangzf
 * date   : 2021/3/26
 * desc   :
 */
public class MySimpleTarget2 extends SimpleTarget<Drawable> {
    private ImageView view;

    public MySimpleTarget2(int width, int height, ImageView view){
        super(width,height);
        this.view = view;
    }
    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        view.setImageDrawable(resource);
    }
}
