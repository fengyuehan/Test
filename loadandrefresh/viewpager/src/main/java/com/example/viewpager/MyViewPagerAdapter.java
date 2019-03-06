package com.example.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> mList;

    public MyViewPagerAdapter(List<View> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    //来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    //当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，
    // 我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int newPosition = position % mList.size();
        container.addView(mList.get(newPosition));
        return mList.get(newPosition);
    }

    //PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int newPosition = position % mList.size();
        container.removeView(mList.get(newPosition));
    }
}
