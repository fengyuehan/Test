package com.example.zgg.intercepter;


import java.io.IOException;

import okio.Buffer;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by yf on 2017/12/5.
 * 描述：日志拦截器方便开发
 */

public class LogIntercepter implements Interceptor {
    private static final String TAG = LogIntercepter.class.getSimpleName();

    private static final String F_BREAK = " %n";
    private static final String F_URL = " %s";
    private static final String F_TIME = " in %.1fms";
    private static final String F_HEADERS = "%s";
    private static final String F_RESPONSE = F_BREAK + "Response: %d";
    private static final String F_BODY = "body: %s";

    private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
    private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
    private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
    private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
    private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        long t1 = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            return null;
        }
        long t2 = System.nanoTime();

        MediaType contentType = null;
        String bodyString = null;
        if (response.body() != null) {
            contentType = response.body().contentType();
            bodyString = response.body().string();
        }

        double time = (t2 - t1) / 1e6d;

        if (request.method().equals("GET")) {
            com.orhanobut.logger.Logger.e(String.format("GET " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), response.code(), response.headers(), stringifyResponseBody(bodyString)));
        } else if (request.method().equals("POST")) {
            com.orhanobut.logger.Logger.e(String.format("POST " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), stringifyRequestBody(request), response.code(), response.headers(), stringifyResponseBody(bodyString)));
        } else if (request.method().equals("PUT")) {
            com.orhanobut.logger.Logger.e(String.format("PUT " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), request.body().toString(), response.code(), response.headers(), stringifyResponseBody(bodyString)));
        } else if (request.method().equals("DELETE")) {
            com.orhanobut.logger.Logger.e(String.format("DELETE " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), response.code(), response.headers()));
        }

        if (response.body() != null) {
            ResponseBody body = ResponseBody.create(contentType, bodyString);
            return response.newBuilder().body(body).build();
        } else {
            return response;
        }
    }


    private static String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public String stringifyResponseBody(String responseBody) {
        return responseBody;
    }
}
