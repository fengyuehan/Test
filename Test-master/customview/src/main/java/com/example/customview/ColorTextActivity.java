package com.example.customview;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.customview.textview.ColorTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2021/5/17
 * desc   :
 */
public class ColorTextActivity extends AppCompatActivity {
    private ColorTextView colorTextView1,colorTextView2,colorTextView3,colorTextView4;
    private ViewPager viewPager;
    private List<ColorTextView> list;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_text);
        list = new ArrayList<>();
        fragments = new ArrayList<>();
        colorTextView1 = findViewById(R.id.ctv_01);
        colorTextView2 = findViewById(R.id.ctv_02);
        colorTextView3 = findViewById(R.id.ctv_03);
        colorTextView4 = findViewById(R.id.ctv_04);
        viewPager = findViewById(R.id.vp);
        list.add(colorTextView1);
        list.add(colorTextView2);
        list.add(colorTextView3);
        list.add(colorTextView4);
        for (int i = 0; i< list.size();i++){
            fragments.add(ItemFragment.newInstance(list.get(i).getText().toString()));
        }
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),fragments, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorTextView left = list.get(position);
                left.setDirection(ColorTextView.Direction.LEFT_TO_RIGHT);
                left.setCurrentProgress(1-positionOffset);
                try {
                    ColorTextView right = list.get(position + 1);
                    right.setDirection(ColorTextView.Direction.RIGHT_TO_LEFT);
                    right.setCurrentProgress(positionOffset);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter{
        private List<Fragment> lists;
        public MyAdapter(@NonNull FragmentManager fm, List<Fragment> lists, int behavior) {
            super(fm, behavior);
            this.lists = lists;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return lists.size();
        }
    }
}
