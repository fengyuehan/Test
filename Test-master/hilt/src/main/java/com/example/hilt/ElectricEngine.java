package com.example.hilt;

import android.util.Log;

import javax.inject.Inject;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */
public class ElectricEngine implements Engine {

    @Inject
    public ElectricEngine(){

    }
    @Override
    public void start() {
        Log.e("zzf","Electric engine start");
    }

    @Override
    public void shutdown() {
        Log.e("zzf","Electric engine shutdown");
    }
}
