package com.example.shoppingcart;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentFactory {
    private static HashMap<Integer, Fragment> fragments;

    public static Fragment createFragment(int position) {
        fragments = new HashMap<>();
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new GoodsListFragment();
                    break;
                case 1:
                    fragment = new GoodsListFragment();
                    break;
                case 2:
                    fragment = new GoodsListFragment();
                    break;
                default:
                    break;
            }
        }
        return fragment;
    }
}
