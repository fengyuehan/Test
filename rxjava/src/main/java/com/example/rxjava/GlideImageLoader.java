package com.example.rxjava;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author zzf
 * @date 2019/7/30/030
 * 描述：
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load((String) path)
                .into(imageView);
        /*Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);*/
    }
}
