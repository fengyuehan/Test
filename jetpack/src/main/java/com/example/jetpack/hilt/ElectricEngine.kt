package com.example.jetpack.hilt

import javax.inject.Inject

class ElectricEngine @Inject constructor():Engine {
    override fun start() {
        println("Electric engine start.")
    }

    override fun shutdown() {
        println("Electric engine shutdown.")
    }
}