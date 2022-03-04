package com.example.customview.viewgroup;

import android.view.View;
import android.view.ViewGroup;

/**
 * author : zhangzf
 * date   : 2021/5/21
 * desc   :
 */
public abstract class BaseAdapter {

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);
}
