package com.example.daggerdemo2.dagger;

import android.util.Log;

import com.example.daggerdemo2.Gson;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    private OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json;charset = utf-8");

    private String url;
    /**
     * @Inject 在一个类中不能同时注解两个构造函数，不然会报下面的错误
     * Types may only contain one @Inject constructor
     */
    //@Inject
    public ApiService(){

    }

    /**
     * 在一个类中，其构造函数中有多少个参数，我们需要在module中提供对应的方法
     *
     * 比如：
     *      @Provides
     *     public String url(){
     *         return "Http://baidu.com";
     *     }
     *
     */

    public ApiService(OkHttpClient okHttpClient,String url){
        this.url = url;
        this.mOkHttpClient = okHttpClient;
        Log.e("zzf","------------ApiService的有参构造函数-----------");
    }

    public void register(){
        Log.e("zzf","------ApiService-----register----------");

        RequestBody requestBody = RequestBody.create(JSON,"");
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
