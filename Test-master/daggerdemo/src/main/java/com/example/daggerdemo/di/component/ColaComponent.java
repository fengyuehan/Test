package com.example.daggerdemo.di.component;

import com.example.daggerdemo.MainActivity;
import com.example.daggerdemo.bean.Cola;
import com.example.daggerdemo.di.model.DicosModule;

import dagger.Component;

@Component(modules = DicosModule.class)
public interface ColaComponent {
    void inject(MainActivity mainActivity);
}
