package com.example.hilt;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * author : zhangzf
 * date   : 2021/3/15
 * desc   :
 */

@Module
@InstallIn(ActivityComponent.class)
public class NetModule {

    @Provides
    public OkHttpClient provideOkhttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("http://baidu.com")
                .client(okHttpClient)
                .build();
    }
}
