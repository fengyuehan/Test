package com.example.jetpack.hilt

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.jetpack.R
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class TestActivity:AppCompatActivity() {

    @Inject
    lateinit var truck: Truck

    @Inject
    lateinit var retrofit: Retrofit

    val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        truckDeliverBtn.setOnClickListener {
            truck.deliver()
        }

        networkRequestBtn.setOnClickListener {
            val  apiService = retrofit.create(ApiService::class.java)
            lifecycleScope.launch(Dispatchers.IO) {
                val provinces = apiService.getProvinces()
                for (provice in provinces){
                    println(provice.name)
                }
            }
        }

        viewModelWorkBtn.setOnClickListener {
            viewModel.dowork()
        }

        val myEntryPoint = EntryPoints.get(this,MyEntryPoint::class.java)
        val retrofit1 = myEntryPoint.getRetrofit()
        println("${retrofit1}")
    }
}