package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity,JiChuActivity::class.java))
            }
        })
        btn_array.setOnClickListener { object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity,ArrayActivity::class.java))
            }
        } }
        btn_list.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity,ListActivity::class.java))
            }
        })
        btn_map.setOnClickListener { object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity,MainActivity::class.java))
            }

        } }
    }
}
