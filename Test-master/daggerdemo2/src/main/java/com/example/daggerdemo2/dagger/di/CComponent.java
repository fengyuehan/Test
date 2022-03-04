package com.example.daggerdemo2.dagger.di;

import com.example.daggerdemo2.DaggerActivity;
import com.example.daggerdemo2.MainActivity;
import com.example.daggerdemo2.dagger.UtilModule;
import com.example.daggerdemo2.dagger.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = UtilModule.class)
public interface CComponent {
    void inject(DaggerActivity daggerActivity);
}
