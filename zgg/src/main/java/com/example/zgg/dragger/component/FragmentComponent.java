package com.example.zgg.dragger.component;

import android.app.Activity;

import com.example.zgg.dragger.module.FragmentModule;
import com.example.zgg.dragger.scope.ApplicationComponent;
import com.example.zgg.dragger.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

}
