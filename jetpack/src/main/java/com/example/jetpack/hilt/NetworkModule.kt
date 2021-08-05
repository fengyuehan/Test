package com.example.jetpack.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttp():OkHttpClient{
        return OkHttpClient.Builder()
                .connectTimeout(20,TimeUnit.MILLISECONDS)
                .readTimeout(20,TimeUnit.MILLISECONDS)
                .writeTimeout(20,TimeUnit.MILLISECONDS)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://guolin.tech/")
                .client(okHttpClient)
                .build()
    }
}