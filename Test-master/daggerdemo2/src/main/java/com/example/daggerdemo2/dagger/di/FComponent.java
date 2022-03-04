package com.example.daggerdemo2.dagger.di;

import com.example.daggerdemo2.dagger.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = HttpModule.class)
public interface FComponent {
    CComponent getChildComponent();
}
