package com.example.hilt;

import android.util.Log;

import javax.inject.Inject;

/**
 * author : zhangzf
 * date   : 2021/3/13
 * desc   :
 */
public class Car {
    @Inject
    Deliver deliver;

    @Inject
    public Car(Deliver deliver){
        this.deliver = deliver;
    }

    public void car(){
        Log.e("zzf","car");
        deliver.deliver();
    }
}
