package com.example.customview.picker;

import android.graphics.Color;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customview.R;


public class MyViewProvider implements IViewProvider<MyData> {
    @LayoutRes
    @Override
    public int resLayout() {
        return R.layout.my_scroll_picker_item_layout;
    }

    @Override
    public void onBindView(@NonNull View view, @Nullable MyData itemData) {
        TextView tv = view.findViewById(R.id.tv_content);
        tv.setText(itemData == null ? null : itemData.text);
        view.setTag(itemData);
    }

    @Override
    public void updateView(@NonNull View itemView, boolean isSelected) {
        TextView tv = itemView.findViewById(R.id.tv_content);
        tv.setTextColor(Color.parseColor(isSelected ? "#589AAA" : "#342434"));
    }
}
