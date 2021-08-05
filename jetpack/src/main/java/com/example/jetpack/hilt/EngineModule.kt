package com.example.jetpack.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class EngineModule {
    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine):Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: BindElectricEngine):Engine
}