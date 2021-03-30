package com.example.glidepicasso.fetcher;

import android.util.Log;

import com.bumptech.glide.load.model.GlideUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * author : zhangzf
 * date   : 2021/3/30
 * desc   :
 */
public class DefaultOkHttpFactory implements OkHttpFactory {

    @Override
    public OkHttpClient build() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        Log.e("zzf","创建了OkHttpClient实例");
        return builder.build();
    }
}
