package com.example.zgg;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.androidbase.dagger.component.AppComponent;
import com.androidbase.dagger.component.DaggerAppComponent;
import com.androidbase.dagger.module.AppModule;
import com.example.zgg.http.HttpManager;
import com.example.zgg.http.utils.SharedPreferencesUtil;
import com.tencent.smtt.sdk.QbSdk;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private static Context mContext;
    private HttpManager mHttpManger;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        mHttpManger = new HttpManager();
        //优化application，有两种方式
        //1、开启一个子线程
        //2、开启一个服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtil.getInstance(mContext, "WanAndroid");
                LoadWebX5();
            }
        }).start();
    }

    private void LoadWebX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getInstance(), cb);
    }

    public static <T> T apiService(Class<T> clz){
        return getInstance().mHttpManger.getService(clz);
    }

    public static synchronized MyApplication getInstance(){
        return instance;
    }

    public static AppComponent getAppConponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }
}
