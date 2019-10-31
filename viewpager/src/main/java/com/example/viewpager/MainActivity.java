package com.example.viewpager;

import android.content.Intent;
import androidx.core.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Banner mBanner;
    private List<String> mTitles;
    private List<String> mPaths;
    //private int[] images = {R.drawable.guide1,R.drawable.guide2,R.drawable.guide3};
    String[] images= new String[] {
            "http://218.192.170.132/BS80.jpg",
            "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
            "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
            "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
            "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
            "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
            "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBanner = findViewById(R.id.banner);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TablayoutActivity.class));
            }
        });
        inutData();
        initBanner();

    }

    private void initBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题

        mBanner.setBannerTitles(mTitles);
        mBanner.setImageLoader(new MyLoader());
        //mBanner.setImages(mPaths);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(MainActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.setImages(Arrays.asList(images));
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setDelayTime(2000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        mBanner.start();
    }

    private void inutData() {
        mTitles = new ArrayList<>();
        mPaths = new ArrayList<>();

        mTitles.add("爱祖国");
        mTitles.add("爱人民");
        mTitles.add("爱社会");
        mTitles.add("爱学习");
        mTitles.add("爱劳动");
        mTitles.add("爱妹子");
        mTitles.add("爱党");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        mPaths.add("http://a3.att.hudong.com/72/76/01300000012339118647690465772.jpg");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }
}
