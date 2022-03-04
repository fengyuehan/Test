package com.example.jetpack.hilt

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@EntryPoint
@InstallIn(ActivityRetainedComponent::class)
interface MyEntryPoint {
    fun getRetrofit() :Retrofit
}