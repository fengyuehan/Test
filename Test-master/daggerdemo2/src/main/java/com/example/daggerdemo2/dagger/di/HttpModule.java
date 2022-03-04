package com.example.daggerdemo2.dagger.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class HttpModule {

    /**
     * @Singleton 表示单例的注解
     * 但是它有一个缺点，只能在同一个activity里面保证单例，在不同的actiovity不能保持单例
     * 解决办法：
     *
     * 在Application的onCreate方法中进行Componet的初始化，这样就能保证application级别的范围
     * @return
     */
    @Singleton
    @Provides
    public OkHttpClient getOkHttpClient(){
        return new OkHttpClient().newBuilder().build();
    }

}
