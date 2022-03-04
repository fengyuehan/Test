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
public class MySimpleTarget extends SimpleTarget<Drawable> {

    /**
     * ViewTarget内部的实现更加复杂，它会持有View的引用，并通过内部的SizeDeterminer计算View的宽高来提供给Glide作为参考，
     * SimpleTarget则不会去处理这些逻辑，我们需要手动的指定一个宽高
     *
     */
    private ImageView view;

    public MySimpleTarget(ImageView view){
        this.view = view;
    }
    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        view.setImageDrawable(resource);
    }
}
