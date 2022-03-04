package com.example.aspectjdemo;

import android.util.Log;

public class DebugLog {
    private DebugLog(){

    }

    public static void log(String tag,String msg){
        Log.e(tag,msg);
    }
}
