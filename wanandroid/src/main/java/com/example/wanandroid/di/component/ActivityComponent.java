package com.example.wanandroid.di.component;

import android.app.Activity;

import com.example.wanandroid.di.module.ActivityModule;
import com.example.wanandroid.ui.hot.HotActivity;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(HotActivity hotActivity);
}
