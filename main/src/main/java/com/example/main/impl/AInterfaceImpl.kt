package com.example.main.impl

import android.util.Log
import com.example.main.api.AInterface

class AInterfaceImpl : AInterface {
    override fun test() {
        Log.i("zzf", "A3InterfaceImpl -> test")
    }
}