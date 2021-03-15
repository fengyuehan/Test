package com.example.hilt;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */

@Module
@InstallIn(ActivityComponent.class)
public abstract class EngineModule {
    @bindGasEngine
    @Binds
    public abstract Engine bindGasEngine(GasEngine gasEngine);
    /*@bindElectricEngine
    @Binds
    public abstract Engine bindElectricEngine(ElectricEngine electricEngine);*/
}
