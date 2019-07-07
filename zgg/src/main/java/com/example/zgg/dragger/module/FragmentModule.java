package com.example.zgg.dragger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.zgg.dragger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
