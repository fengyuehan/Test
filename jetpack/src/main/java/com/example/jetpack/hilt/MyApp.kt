package com.example.jetpack.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp :Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object{
        lateinit var mApplication: MyApp
    }

}