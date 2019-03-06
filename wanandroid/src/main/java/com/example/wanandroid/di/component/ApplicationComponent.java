package com.example.wanandroid.di.component;

import android.content.Context;

import com.example.wanandroid.di.module.ApplicationModule;

import dagger.Component;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getApplication();
}
