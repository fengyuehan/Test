package com.example.zgg.http;

import android.provider.ContactsContract;

import com.androidbase.okhttp.OkHttpUtils;
import com.example.zgg.Api.Api;
import com.example.zgg.MyApplication;
import com.example.zgg.config.Constants;
import com.example.zgg.http.utils.HttpsUtil;
import com.example.zgg.intercepter.LogIntercepter;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private Retrofit mRetrofit;
    private HashMap<Class, Object> mServiceHashMap = new HashMap<>();
    private ConcurrentHashMap<Class, Object> cachedApis = new ConcurrentHashMap<>();
    private PersistentCookieJar mPersistentCookieJar;

    public HttpManager(){
        mPersistentCookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(MyApplication.getInstance()));
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                //错误重连
                .retryOnConnectionFailure(true)
                //连接超时
                .connectTimeout(15,TimeUnit.SECONDS)
                //读写超时
                .readTimeout(45,TimeUnit.SECONDS)
                .writeTimeout(55,TimeUnit.SECONDS)
                //添加日志拦截器
                .addInterceptor(new LogIntercepter())
                ////支持自动持久化cookie和自动添加cookie
                .cookieJar(mPersistentCookieJar)
                .sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServiceHashMap.put(Api.class,mRetrofit);
    }

    public <T> T getService(Class<T> clz){
        Object obj = cachedApis.get(clz);
        if (obj != null){
            return (T) obj;
        }else {
            Retrofit retrofit = (Retrofit) mServiceHashMap.get(clz);
            if (retrofit != null){
                T service = retrofit.create(clz);
                cachedApis.put(clz,service);
                return service;
            }else {
                return null;
            }
        }
    }

}
