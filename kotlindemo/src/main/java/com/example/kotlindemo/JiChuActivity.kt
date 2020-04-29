package com.example.kotlindemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ji_chu.*

class JiChuActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ji_chu)
        init()
        tv_until.text = sum1(1,2).toString()
        tv_until1.text = sun2(1,2).invoke().toString()
        returnDemo_1()
        returnDemo_2()
        returnDemo_3()
        returnDemo_4()
    }

    /**
     * 隐式的标签，即该标签与接收该Lambda的函数同名
     */
    private fun returnDemo_4() {
        Log.e("zzf","--------------------------")
        val array = arrayOf(1,2,3,4,5)
        array.forEach  {
            if (it % 2== 0 ){
                return@forEach
            }
            Log.e("zzf",it.toString())
        }
    }

    /**
     * 加上标签，也是相当于continue
     * 标签可以理解为记录Lambda表达式的指令执行入口
     */
    private fun returnDemo_3() {
        Log.e("zzf","--------------------------")
        val array = arrayOf(1,2,3,4,5)
        array.forEach  hera@{
            if (it % 2== 0 ){
                return@hera
            }
            Log.e("zzf",it.toString())
        }
    }

    /**
     * 匿名函数相当于continue
     */
    private fun returnDemo_2() {
        val array = arrayOf(1,2,3,4,5)
        Log.e("zzf","--------------------------")
        array.forEach (
                fun(i:Int){
                    if ( i % 2 == 0){
                        return
                    }

                    Log.e("zzf",i.toString())
                }
        )
    }

    /**
     * 这种类似于break
     */
    private fun returnDemo_1() {
        Log.e("zzf","--------------------------")
         val array = arrayOf(1,2,3,4,5)
        array.forEach {
            if (it % 2== 0 ){
                return
            }
            Log.e("zzf",it.toString())
        }
    }
    /**
     * 加了大括号的必须调用invoke（）
     */
    private fun sun2(i: Int, i1: Int): () -> Int {
        return {i + i1}
    }


    private fun sum1(i: Int,j:Int): Int? {
        return (i + j)
    }

    private fun init() {
        /**
         * until与..的区别
         * until不包含5
         * ..包含5
         */
        for (index in 0..5){
            Log.e("zzf",index.toString())
        }
        Log.e("zzf","------------------------------------------")
        for (index in 0 until 5){
            Log.e("zzf",index.toString())
        }
    }

}