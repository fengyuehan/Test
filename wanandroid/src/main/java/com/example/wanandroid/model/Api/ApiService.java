package com.example.wanandroid.model.Api;

import com.example.wanandroid.ui.Bean.BaseResp;
import com.example.wanandroid.ui.Bean.SearchHot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    /**
     * 搜索热词
     */
    @Headers({"baseUrl:normal"})
    @GET("hotkey/json")
    Observable<BaseResp<List<SearchHot>>> getSearchHot();

    /**
     * 常用网站
     */
    @Headers({"baseUrl:normal"})
    @GET("friend/json")
    Observable<BaseResp<List<SearchHot>>> getHotWeb();
}
