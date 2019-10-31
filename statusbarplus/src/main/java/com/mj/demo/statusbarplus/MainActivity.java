package com.mj.demo.statusbarplus;

import android.graphics.Color;
import android.os.Build;
import androidx.core.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager bannerViewPager;
    private int bannerImgs[] = {R.drawable.turn_one_img, R.drawable.trun_two_img, R.drawable.trun_three_img};
    private LinearLayout banner_rect;
    private BannerPagerAdapter bannerPagerAdapter;
    private int lastPosition = 0;
    private ObservableScrollView mySl;
    private RelativeLayout rootView;
    private ImageView statusIv;
    private boolean isChangeBar = false;//防止滑动时频繁重置操作
    private boolean isSolidColor = false;//防止滑动到顶部，颜色继续切换
    private String[] colors = {"#EECB1907", "#EEF2374C", "#EEFAD0A0"};
    private String currentColor = "#CCCB1907";
    private MyBendBcgView myBendBcgView;
    private int solidTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invadeStatusBar();
        setContentView(R.layout.activity_main);
        bannerEvent();
    }

    private void bannerEvent() {
        bannerViewPager = findViewById(R.id.banner_view_pager);
        banner_rect = findViewById(R.id.banner_point);
        initPoints();
        rootView = findViewById(R.id.root_view);
        bannerPagerAdapter = new BannerPagerAdapter(bannerImgs, this, R.layout.banner_viepager);
        banner_rect.getChildAt(lastPosition).setEnabled(true);
        bannerViewPager.addOnPageChangeListener(this);
        //设置是否自动轮播
        bannerPagerAdapter.setAutoBanner(true);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        bannerViewPager.setCurrentItem(bannerImgs.length * 1000);
        bannerViewPager.setOffscreenPageLimit(2);
        //滑动
        statusIv = findViewById(R.id.status_iv);
        myBendBcgView = findViewById(R.id.my_bend_bcg_view);
        myBendBcgView.post(new Runnable() {
            @Override
            public void run() {
                solidTop = myBendBcgView.getSolidTop();
            }
        });
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) myBendBcgView.getLayoutParams();
        final int diffHeight = layoutParams.height - solidTop;
        mySl = findViewById(R.id.my_sl);
        mySl.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView observableScrollView, int x, int y, int oldx, int oldy) {
                if (diffHeight > y && y >= 0) {
                    myBendBcgView.reDraw(y);
                    Log.e("MyTag", "我上升了" + layoutParams.height + "," + layoutParams.topMargin + "," + solidTop + "," + y);
                }
                if (y > bannerViewPager.getBottom() && isChangeBar) {
                    rootView.setBackgroundColor(Color.WHITE);
                    statusIv.setImageResource(R.drawable.status1_img);
                    isChangeBar = !isChangeBar;
                    isSolidColor = true;
                    //设置状态栏文字颜色及图标为深色
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else if (y <= bannerViewPager.getBottom() && !isChangeBar) {
                    isChangeBar = !isChangeBar;
                    isSolidColor = false;
                    rootView.setBackgroundColor(Color.parseColor(currentColor));
                    statusIv.setImageResource(R.drawable.status_img);
                    //设置状态栏文字颜色及图标为浅色
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            }
        });
    }

    private void initPoints() {
        for (int i = 0; i < bannerImgs.length; i++) {
            View pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.rect_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ChangePxFromDp.dp2px(this, 10), ChangePxFromDp.dp2px(this, 5));
            pointView.setEnabled(false); //默认都是暗色的
            banner_rect.addView(pointView, layoutParams);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerPagerAdapter.cancelAutoBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerPagerAdapter.startAutoBanner(this, bannerViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changePagerData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void changePagerData(int position) {
        int newPosition = position % banner_rect.getChildCount();
        banner_rect.getChildAt(lastPosition).setEnabled(false);
        banner_rect.getChildAt(newPosition).setEnabled(true);
        currentColor = colors[newPosition];
        if (!isSolidColor) {
            rootView.setBackgroundColor(Color.parseColor(currentColor));
        }
        // 记录之前的位置
        lastPosition = newPosition;
    }

    //初始化状态栏
    private void invadeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
