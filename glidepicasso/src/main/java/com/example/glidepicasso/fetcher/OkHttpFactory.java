package com.example.glidepicasso.fetcher;

import com.bumptech.glide.load.model.GlideUrl;

import okhttp3.OkHttpClient;

/**
 * author : zhangzf
 * date   : 2021/3/30
 * desc   :
 */
public interface OkHttpFactory {
    OkHttpClient build();
}
