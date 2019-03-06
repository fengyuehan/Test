package com.example.wanandroid.model.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wanandroid.base.app.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//添加Cookie
public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        String cookie = sharedPreferences.getString("Cookie","");
        builder.addHeader("Cookie",cookie);
        return chain.proceed(builder.build());
    }
}
