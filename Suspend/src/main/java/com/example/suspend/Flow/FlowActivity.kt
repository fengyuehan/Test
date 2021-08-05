package com.example.suspend.Flow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.suspend.*

class FlowActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
    }

    fun startSingleNetworkCallActivity(view: View) {
        startActivity(Intent(this@FlowActivity, SingleNetworkCallActivity::class.java))
    }

    fun startSeriesNetworkCallsActivity(view: View){
        startActivity(Intent(this@FlowActivity,SeriesNetworkCallsActivity::class.java))
    }

    fun startParallelNetworkCallsActivity(view: View){
        startActivity(Intent(this@FlowActivity, ParallelNetworkCallsActivity::class.java))
    }

    fun startRoomDatabaseActivity(view: View){
        startActivity(Intent(this@FlowActivity,RoomDBActivity::class.java))
    }

    fun startCatchActivity(view: View){
        startActivity(Intent(this@FlowActivity,CatchActivity::class.java))
    }
}