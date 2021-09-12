package com.example.a2lib;

import android.util.Log;

import com.example.ainterfacelib.BInterface;

public class A2Impl implements BInterface {

    @Override
    public String getname() {
        return "A2Impl";
    }

    public A2Impl(){
        Log.e("zzf","A2Impl");
    }
}
