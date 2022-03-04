package com.example.innerclass;

import android.util.Log;

/**
 * author : zhangzf
 * date   : 2021/1/6
 * desc   :
 */
public class Person2 {
    public static Person2 p = new Person2();

    static{
        Log.e("zzf","1");
    }

    {
        Log.e("zzf","2");
    }

    public Person2(){
        Log.e("zzf","3");
    }
}
