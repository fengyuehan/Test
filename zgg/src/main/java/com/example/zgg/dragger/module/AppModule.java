package com.example.zgg.dragger.module;

import com.example.zgg.MyApplication;
import com.example.zgg.dragger.ContextLife;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private MyApplication application;
    public AppModule(MyApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    MyApplication provideApplicationContext() {
        return application;
    }


}
