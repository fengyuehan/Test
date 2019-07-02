package com.example.definetablayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class NavigationBar extends FrameLayout implements View.OnClickListener {
    private TabView tabView1,tabView2,tabView3;
    private OnTabSelectedListener mOnTabSelectedListener;
    private int mLastSelectedPosition = -1;

    public NavigationBar(@NonNull Context context) {
        this(context,null);
        init(context);
    }

    public NavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init(context);
    }

    public NavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context,R.layout.navigation_bar,null);
        tabView1 = view.findViewById(R.id.tv1);
        tabView2 = view.findViewById(R.id.tv2);
        tabView3 = view.findViewById(R.id.tv3);
        tabView1.setOnClickListener(this);
        tabView2.setOnClickListener(this);
        tabView3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv1:
                mOnTabSelectedListener.onTabSelected(0);
                break;
            case R.id.tv2:
                mOnTabSelectedListener.onTabSelected(1);
                break;
            case R.id.tv3:
                mOnTabSelectedListener.onTabSelected(2);
                break;
                default:
                    break;
        }
    }


    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    public void setCurrentTab(int position){
        if (mLastSelectedPosition != position){
            getTabView(position).setSelect(true);
            getTabView(mLastSelectedPosition).setSelect(false);
            mLastSelectedPosition = position;
        }
    }

    private TabView getTabView(int position) {
        if (position == 0){
            return tabView1;
        }else if (position == 1){
            return tabView2;
        }else {
            return tabView3;
        }
    }
}
