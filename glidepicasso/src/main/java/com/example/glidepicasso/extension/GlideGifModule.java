package com.example.glidepicasso.extension;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.hash.study.gif.FrameSequenceDrawable;

import java.io.InputStream;

/*@GlideModule
public class GlideGifModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context,glide,registry);
        registry.append(Registry.BUCKET_GIF,
                InputStream.class,
                FrameSequenceDrawable.class,
                new GifResourceDecoder(glide.getBitmapPool())
        );
    }
}*/
