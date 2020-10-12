package com.example.daggerdemo2.dagger;

import com.example.daggerdemo2.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {
    @Singleton
    @Provides
    public Gson getGson(){
        return new Gson();
    }
}
