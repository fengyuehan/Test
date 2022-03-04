package com.example.jetpack.hilt

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttp():OkHttpClient{

        /**
         *  maxAge和maxStale的区别在于：
            maxAge:没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
            maxStale:没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败
         */
        /**
         * 有网时候的缓存
         */
        /**
         * 有网时候的缓存
         */
        val netCacheInterceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()
                val response: Response = chain.proceed(request)
                val onlineCacheTime = 30 //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                return response.newBuilder()
                    .header("Cache-Control", "public, max-age=$onlineCacheTime")
                    .removeHeader("Pragma")
                    .build()
            }
        }
        /**
         * 没有网时候的缓存
         */
        /**
         * 没有网时候的缓存
         */
        val offlineCacheInterceptor: Interceptor = Interceptor { chain ->
            var request: Request = chain.request()
            if (!hasNetWork(MyApp.mApplication)) {
                val offlineCacheTime = 60 //离线的时候的缓存的过期时间
                request =
                    request.newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=$offlineCacheTime"
                        )
                        .build()
            }
            chain.proceed(request)
        }


        val cache =
            Cache(File(MyApp.mApplication.cacheDir, "httpCache"), 1024 * 1024 * 100)
        return OkHttpClient.Builder()
            .cache(cache)
                .addNetworkInterceptor(netCacheInterceptor)
                 .addInterceptor(offlineCacheInterceptor)
                .connectTimeout(20,TimeUnit.MILLISECONDS)
                .readTimeout(20,TimeUnit.MILLISECONDS)
                .writeTimeout(20,TimeUnit.MILLISECONDS)
                .build()
    }

    private fun hasNetWork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable
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