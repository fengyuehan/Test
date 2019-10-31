package com.mj.demo.statusbarplus;

import android.app.Activity;
import android.content.Context;
import androidx.core.view.PagerAdapter;
import androidx.core.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class BannerPagerAdapter extends PagerAdapter {
    private int[] imageViewlist;
    private Context mContext;
    private int layoutViewpager;
    private boolean isAutoBanner = true;
    private boolean flag_banner = true;
    private boolean isRunning = true;
    private Timer timer;

    public void setAutoBanner(boolean autoBanner) {
        isAutoBanner = autoBanner;
    }

    public BannerPagerAdapter(int imageViewlist[], Context mContext, int layoutViewpager) {
        this.imageViewlist = imageViewlist;
        this.mContext = mContext;
        this.layoutViewpager = layoutViewpager;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(mContext).inflate(layoutViewpager, null);
        ImageView banner_img = view.findViewById(R.id.banner_img);
        banner_img.setImageResource(imageViewlist[position % imageViewlist.length]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public void cancelAutoBanner() {
        if (isAutoBanner) {
            timer.cancel();
            flag_banner = false;
        }
    }


    public void startAutoBanner(final Activity activity, final ViewPager bannerViewPager) {
        if (isAutoBanner) {
            flag_banner = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isRunning)
                        return;
                    try {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final int current = bannerViewPager.getCurrentItem();
                                bannerViewPager.setCurrentItem(current + 1);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 3000, 3000);
        }
    }
}
