package com.example.viewpager;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.viewpager.fragment.FirstFragment;
import com.example.viewpager.fragment.SecondFragment;
import com.example.viewpager.fragment.ThreeFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class TablayoutActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CommonTabLayout mCommonTabLayout;
    private List<Fragment> fragments;
    String[] name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        initView();
        initData();
    }

    private void initData() {
        initFragment();
        initTab();
        initViewPager();
        initListener();
    }

    private void initListener() {
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCommonTabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }



    private void initViewPager() {
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),fragments,name));
        mViewPager.setCurrentItem(0);
    }

    private void initTab() {
        name = getResources().getStringArray(R.array.wallet_main_tab);
        int[] selectIcon = {R.drawable.tab_contact_select,R.drawable.tab_home_select,R.drawable.tab_more_select};
        int[] unSelectIcon = {R.drawable.tab_contact_unselect,R.drawable.tab_home_unselect,R.drawable.tab_more_unselect};
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < name.length; i++){
            tabEntities.add(new TabEntity(name[i],selectIcon[i],unSelectIcon[i]));
        }
        mCommonTabLayout.setTabData(tabEntities);
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThreeFragment());
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp);
        mCommonTabLayout = findViewById(R.id.ctl);
    }
}
