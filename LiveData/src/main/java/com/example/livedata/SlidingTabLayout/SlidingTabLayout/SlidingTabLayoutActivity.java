package com.example.livedata.SlidingTabLayout.SlidingTabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import androidx.core.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livedata.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class SlidingTabLayoutActivity extends AppCompatActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments;
    String[] mList = {" 按日查询 ", " 按月查询 "," 按年查询 ", " 自定义 "};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mSlidingTabLayout = findViewById(R.id.stl);
        mViewPager = findViewById(R.id.vp);
        initFragments();
        mSlidingTabLayout.setViewPager(mViewPager,mList,this,fragments);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        MarketFragment marketFragment = new MarketFragment();
        fragments.add(marketFragment);
        MineFragment mineFragment = new MineFragment();
        fragments.add(mineFragment);
        SupportFragment supportFragment = new SupportFragment();
        fragments.add(supportFragment);
    }
}
