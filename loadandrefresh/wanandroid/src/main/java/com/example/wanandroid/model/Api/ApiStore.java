package com.example.wanandroid.model.Api;

import android.annotation.SuppressLint;

import com.example.wanandroid.base.app.MyApplication;
import com.example.wanandroid.model.Api.ApiConfig;
import com.example.wanandroid.model.Api.NullOnEmptyConverterFactory;
import com.example.wanandroid.model.interceptor.AddCookiesInterceptor;
import com.example.wanandroid.model.interceptor.BaseUrlInterceptor;
import com.example.wanandroid.model.interceptor.HttpLoggingInterceptor;
import com.example.wanandroid.model.interceptor.ReceivedCookiesInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiStore {

    private static Retrofit retrofit;
    private static String baseUrl = ApiConfig.BASE_URL;

    static {
        createProxy();
    }

    public static <T> T createApi(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private static void createProxy() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create();

        File cacheFile = new File(MyApplication.getInstance().getCacheDir(),"cache");
        Cache cache = new Cache(cacheFile,1024*1024*100);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder1 = original.newBuilder();
                        Request request = builder1.build();
                        return chain.proceed(request);
                    }
                })
                .sslSocketFactory(getSSLSocketFactory())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .cache(cache)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(builder.build())
                .build();

    }

    @SuppressLint("TrustAllX509TrustManager")
    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        chain[0].checkValidity();
                    } catch (Exception e) {
                        throw new CertificateException("Certificate not valid or trusted.");
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            return null;
        }
    }
}
