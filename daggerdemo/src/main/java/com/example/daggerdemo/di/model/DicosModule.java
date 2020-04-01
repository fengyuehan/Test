package com.example.daggerdemo.di.model;

import com.example.daggerdemo.bean.Cola;
import com.example.daggerdemo.bean.Dicos;

import dagger.Module;
import dagger.Provides;

@Module
public class DicosModule {
    @com.example.daggerdemo.di.qualifier.Cola
    @Provides
    public Cola getCola(){
        return new Cola();
    }

    @com.example.daggerdemo.di.qualifier.Dicos
    @Provides
    public Dicos getDicos(){
        return new Dicos();
    }
}
