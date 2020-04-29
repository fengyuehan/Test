package com.example.banner

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var str:String = "http://immocoin.vip/file/img_information/8.png"
    var list = mutableListOf<DataBean>()
    var imageLoader:ImageLoader = ImageLoader.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initBanner()
    }

    private fun initBanner() {
        banner.setImageLoader(object : com.youth.banner.loader.ImageLoader(){
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                imageLoader.displayImage((path as DataBean).url,imageView)
            }
        })
        banner.start()
    }

    private fun initView() {
        for (index in  0 until 3){
            var  dataBean:DataBean = DataBean()
            dataBean.url = str
            list.add(dataBean)
        }
    }
}
