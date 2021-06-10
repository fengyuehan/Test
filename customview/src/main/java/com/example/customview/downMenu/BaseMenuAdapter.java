package com.example.customview.downMenu;

import android.view.View;
import android.view.ViewGroup;

/**
 * author : zhangzf
 * date   : 2021/5/25
 * desc   :
 */
public abstract class BaseMenuAdapter {
    public abstract int  getCount();
    //得到tab
    public abstract View getTabView(int position, ViewGroup parent);
    //得到弹出的View
    public abstract View getMenuView(int position,ViewGroup parent);
    //用于关闭的时候tab的字体颜色以及图片的变化
    public abstract void menuClose(View tabView);
    //用于打开的时候tab的字体颜色以及图片的变化
    public abstract void menuOpen(View tabView);
}
