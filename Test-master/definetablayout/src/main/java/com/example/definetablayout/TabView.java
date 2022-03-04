package com.example.definetablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TabView extends LinearLayout {
    private TextView mTextView;
    private ImageView mImageView;
    private int mTabImg;
    private String mTabName;
    
    public TabView(Context context) {
        this(context,null);
        init(context,null);
    }
    
    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init(context,attrs);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        View tabView = View.inflate(context,R.layout.tabview,null);
        mTextView = tabView.findViewById(R.id.tv);
        mImageView = tabView.findViewById(R.id.iv);
        getAttrs(attrs);
        setView();
    }

    private void setView() {
        setGravity(Gravity.CENTER);
        setGravity(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        mImageView.setImageResource(mTabImg);
        mTextView.setText(mTabName);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedValue = getResources().obtainAttributes(attrs,R.styleable.TabView);
        mTabImg = typedValue.getResourceId(R.styleable.TabView_src,R.drawable.selector_tab);
        mTabName = typedValue.getString(R.styleable.TabView_text);
        typedValue.recycle();
    }

    public void setSelect(boolean isSelected){
        mImageView.setSelected(isSelected);
        mTextView.setSelected(isSelected);
    }
}
