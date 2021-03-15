package com.example.hilt;

import android.util.Log;

import javax.inject.Inject;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */
public class Truck {
    @Inject
    public Truck(){

    }

    public void drliver(){
        Log.e("zzf","Truck");
    }
}
