package com.example.motionlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null
    private var btn5: Button? = null
    private var btn6: Button? = null
    private var btn7: Button? = null
    private var btn8: Button? = null
    private var btn9: Button? = null
    private var btn10: Button? = null
    private var btn11: Button? = null
    private var btn12: Button? = null
    private var btn13: Button? = null
    private var btn14: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        btn1 = findViewById(R.id.bt_coor8)
        btn2 = findViewById(R.id.bt_coor9)
        btn3 = findViewById(R.id.bt_coor10)
        btn4 = findViewById(R.id.bt_coor11)
        btn5 = findViewById(R.id.bt_badge)
        btn6 = findViewById(R.id.bt_image)
        btn7 = findViewById(R.id.bt_Keyset)
        btn8 = findViewById(R.id.bt_coor)
        btn9 = findViewById(R.id.bt_coor2)
        btn10 = findViewById(R.id.bt_coor3)
        btn11 = findViewById(R.id.bt_coor4)
        btn12 = findViewById(R.id.bt_coor5)
        btn13 = findViewById(R.id.bt_coor6)
        btn14 = findViewById(R.id.bt_coor7)
        btn1?.setOnClickListener{

        }

    }
}