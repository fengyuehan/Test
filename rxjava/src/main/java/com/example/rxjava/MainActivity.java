package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.backpress.BackPressActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Banner banner;
    private Button mButton,button1,button2;
    private ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        banner = findViewById(R.id.banner);
        mButton = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn_background);
        button2 = findViewById(R.id.btn_buffer);
        initBanner();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                io.reactivex.Observable.interval(1,TimeUnit.SECONDS)
                        .takeUntil(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                Log.e("zzf",aLong+"");
                                return aLong >= 10;
                            }
                        })
                        /*.takeWhile(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                Log.e("zzf",aLong+"");
                                return aLong <= 10;
                            }
                        })*/
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                final Object object = new Object();
                                final int count = (int) (10 - aLong);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        synchronized (object){
                                            textView.setText(count+ "");
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BackPressActivity.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BackgroundActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BufferActivity.class));
            }
        });
    }

    private void initBanner() {
        List<String> mImageList = new ArrayList<>();
        List<String> mTitleList = new ArrayList<>();
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        mTitleList.add("好好学习");
        mTitleList.add("天天向上");
        mTitleList.add("热爱劳动");
        mTitleList.add("不搞对象");

        //设置样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(mImageList);
        //设置动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合
        banner.setBannerTitles(mTitleList);
        //设置自动轮播
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                /*Intent intent = new Intent(getActivity(),X5WebViewActivity.class);
                intent.putExtra("mUrl",mUrlList.get(position));
                intent.putExtra("mTitle",mTitleList.get(position));
                startActivity(intent);*/

            }
        });
        banner.start();
    }
}
