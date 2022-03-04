package com.example.customview.downMenu;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.customview.R;

import java.util.List;
import java.util.Map;

/**
 * author : zhangzf
 * date   : 2021/5/25
 * desc   :
 */
public class ListMenuAdapter extends BaseMenuAdapter {
    private String[] mItems = {"类型","品牌","价格","更多"};
    private List<String> mTabs;
    private Context mContext;
    private Map<String,Object> mContentDataMap;
    private ImageView imageView;
    private TextView textView;

    public ListMenuAdapter (Context context,List<String> mTabs,Map<String,Object> map){
        this.mContext = context;
        this.mTabs = mTabs;
        this.mContentDataMap = map;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getTabView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab,parent,false);
        initView(view,position);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(View view, int position) {
        textView = view.findViewById(R.id.tv_tab_name);
        imageView = view.findViewById(R.id.iv_tab_pic);
        textView.setText(mTabs.get(position));
        imageView.setSelected(false);
        textView.setTextColor(mContext.getColor(R.color.color_33));
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        View view = null;
        switch (mTabs.get(position)){

        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void menuClose(View view) {
        textView.setTextColor(mContext.getColor(R.color.color_33));
        imageView.setSelected(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void menuOpen(View view) {
        textView.setTextColor(mContext.getColor(R.color.colorAccent));
        imageView.setSelected(true);
    }
}
