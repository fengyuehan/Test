package com.example.zgg.dragger.component;

import android.app.Activity;

import com.androidbase.dagger.component.AppComponent;
import com.example.zgg.dragger.module.ActivityModule;
import com.example.zgg.dragger.scope.ActivityScope;
import com.example.zgg.dragger.scope.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

}
