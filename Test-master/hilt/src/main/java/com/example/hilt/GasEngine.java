package com.example.hilt;

import android.util.Log;

import javax.inject.Inject;


/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */
public class GasEngine implements Engine {
    @Inject
    public GasEngine(){

    }

    /**
     * @Provides methods must return a value
     *
     * provides修饰的必须有返回值
     */
    @Override
    public void start() {
        Log.e("zzf","Gas engine start");
    }


    @Override
    public void shutdown() {
        Log.e("zzf","Gas engine shutdown");
    }
}
