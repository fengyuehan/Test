package com.example.hilt;

import android.util.Log;

import javax.inject.Inject;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */

public class Deliver {


    @Inject
    public Deliver(){

    }

    public void deliver(){
        Log.e("zzf","deliver");
    }
}
