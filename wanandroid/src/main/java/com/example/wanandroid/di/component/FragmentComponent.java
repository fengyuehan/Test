package com.example.wanandroid.di.component;

import android.app.Activity;

import com.example.wanandroid.di.module.FragmentModule;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
}
