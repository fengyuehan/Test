package com.example.glidepicasso.fetcher;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.LogTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author : zhangzf
 * date   : 2021/3/30
 * desc   :
 */
public class OkHttpFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "zzf";
    private GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;
    private volatile boolean isCancelled;
    private DefaultOkHttpFactory connectionFactory;
    static DefaultOkHttpFactory DEFAULT_CONNECTION_FACTORY = new DefaultOkHttpFactory();

    public OkHttpFetcher(GlideUrl url) {
        this(url,DEFAULT_CONNECTION_FACTORY);
    }

    OkHttpFetcher(GlideUrl url,DefaultOkHttpFactory factory){
        this.url = url;
        this.connectionFactory = factory;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        Request.Builder builder = new Request.Builder().url(url.toStringUrl());
        for (Map.Entry<String,String> headerEntry : url.getHeaders().entrySet()){
            String key = headerEntry.getKey();
            builder.addHeader(key,headerEntry.getValue());
        }
        try {
            Request request = builder.build();
            if (isCancelled){
                return;
            }
            Response response = connectionFactory.build().newCall(request).execute();
           responseBody = response.body();
           if (!response.isSuccessful() || responseBody == null){
               throw new IOException("Request failed with code: " + response.code());
           }
            stream = ContentLengthInputStream.obtain(responseBody.byteStream(),responseBody.contentLength());
            callback.onDataReady(stream);
            Log.d(TAG, "success to load data for url");
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Failed to load data for url", e);
            }
            callback.onLoadFailed(e);
        }finally {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Finished http url fetcher fetch in ");
            }
        }
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
            if (responseBody != null) {
                responseBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
