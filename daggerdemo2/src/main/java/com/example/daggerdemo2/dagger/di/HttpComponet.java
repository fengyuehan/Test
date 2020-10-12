package com.example.daggerdemo2.dagger.di;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {HttpModule.class})
public interface HttpComponet {
    OkHttpClient getOkHttpClient();
}
