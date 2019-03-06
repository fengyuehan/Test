package com.example.wanandroid.model.interceptor;

import com.example.wanandroid.model.Api.ApiConfig;
import com.example.wanandroid.model.constant.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//修改HTTP的头部
public class BaseUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        HttpUrl oldUrl = originRequest.url();
        Request.Builder builder = originRequest.newBuilder();
        List<String> urlNameList = originRequest.headers("baseUrl");
        if (urlNameList != null && urlNameList.size() > 0){
            builder.removeHeader("baseUrl");
            String urlName = urlNameList.get(0);
            HttpUrl baseURL = null;
            if (urlName.equals(Constant.WANANDROID)){
                baseURL = HttpUrl.parse(ApiConfig.BASE_URL);
            }else if (urlName.equals(Constant.DOUYU)){
                baseURL = HttpUrl.parse(ApiConfig.DOUYU_URL);
            }else if (urlName.equals(Constant.GETLIVE)){
                baseURL = HttpUrl.parse(ApiConfig.GETLIVE_URL);
            }else if (urlName.equals(Constant.GANK_API)){
                baseURL = HttpUrl.parse(ApiConfig.GANK_API);
            }else if (urlName.equals(Constant.MUSIC_BANNER)){
                baseURL = HttpUrl.parse(ApiConfig.MUSIC_BANNER);
            }else if (urlName.equals(Constant.DOUBAN_API)){
                baseURL = HttpUrl.parse(ApiConfig.DOUYU_URL);
            }

            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())
                    .host(baseURL.host())
                    .port(baseURL.port())
                    .build();

            Request newQuest = builder.url(newHttpUrl).build();
            return chain.proceed(newQuest);
        }else {
            return chain.proceed(originRequest);
        }
    }
}
