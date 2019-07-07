package com.example.zgg.dragger.scope;

import com.example.zgg.MyApplication;
import com.example.zgg.dragger.ContextLife;
import com.example.zgg.dragger.module.AppModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    MyApplication getContext();
}
