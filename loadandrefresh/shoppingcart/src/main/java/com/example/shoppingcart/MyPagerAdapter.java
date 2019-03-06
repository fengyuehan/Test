package com.example.shoppingcart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] titles;
    public MyPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = FragmentFactory.createFragment(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
