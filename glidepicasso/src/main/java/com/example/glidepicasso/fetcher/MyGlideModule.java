package com.example.glidepicasso.fetcher;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.example.glidepicasso.extension.GifResourceDecoder;
import com.hash.study.gif.FrameSequenceDrawable;

import java.io.InputStream;

/**
 * author : zhangzf
 * date   : 2021/3/30
 * desc   :
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class,new OkHttpGlideUrlLoader.Factory());
        Log.e("zzf","注入组件成功");
        registry.append(Registry.BUCKET_GIF,
                InputStream.class,
                FrameSequenceDrawable.class,
                new GifResourceDecoder(glide.getBitmapPool())
        );
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
