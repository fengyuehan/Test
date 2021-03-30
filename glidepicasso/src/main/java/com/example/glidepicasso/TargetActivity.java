package com.example.glidepicasso;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.glidepicasso.fetcher.GlideApp;


/**
 * author : zhangzf
 * date   : 2021/3/26
 * desc   :
 */
public class TargetActivity extends AppCompatActivity {
    private ImageView view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /**
         * preload()可以替换into()法的另外一个方法，和into()不同的是，preload()方法只加载图片，而不显示图片，是一种图片预加载的功能，使真正显示图片的时候不需要从网络获取，提高图片的显示速度。
         *
         */
        GlideApp.with(this)
                .load("")
                .preload();
        /**
         * downloadOnly(int width, int height)方法，该方法主要完成图片加载不显示，与preload功能相似，同时他提供了一个获取图片缓存路径的方法，该方法是阻塞方法，如果图片没有下载成功，会阻塞，因此一般使用get()方法需要在子线程中调用，同时get()内部也会检查是否在子线程，否则抛异常。
         * downloadOnly(Y target)不同的是不需要再子线程中运行。
         */
        GlideApp.with(this)
                .downloadOnly()
                .load("")
                .into(view);

        GlideApp.with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(view);
    }
}
