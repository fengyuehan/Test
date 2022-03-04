package com.example.changeskin.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.changeskin.MyAdapter;
import com.example.changeskin.R;
import com.example.changeskin.fragment.DiscoveryFragment;
import com.example.changeskin.fragment.HomeFragment;
import com.example.changeskin.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SkinActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private List<Fragment> mFragments;
    private MyAdapter myAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_skin);
        initToolBar(false);
        initView();
        initFragment();
        initViewPager();
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnv_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.bnv_discovery:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.bnv_mine:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    private void initViewPager() {
        myAdapter = new MyAdapter(this,mFragments);
        viewPager2.setAdapter(myAdapter);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.setSelectedItemId(position);
                setTitle(bottomNavigationView.getMenu().getItem(position).getTitle());
            }
        });
        viewPager2.setCurrentItem(0);

    }

    private void initFragment() {
        mFragments.add(new HomeFragment());
        mFragments.add(new DiscoveryFragment());
        mFragments.add(new MineFragment());
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bnv_menu);
        viewPager2 = findViewById(R.id.vp_container);
        mFragments = new ArrayList<>();
    }


}
