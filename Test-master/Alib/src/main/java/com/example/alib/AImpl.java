package com.example.alib;

import android.util.Log;

import com.example.ainterfacelib.AInterface;

public class AImpl implements AInterface {
    @Override
    public String getname() {
        return "AImpl";
    }

    public AImpl(){
        Log.e("zzf","AImpl");
    }
}
