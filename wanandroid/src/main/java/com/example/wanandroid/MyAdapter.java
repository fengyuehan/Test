package com.example.wanandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {

    public MyAdapter(@Nullable List<Bean> data) {
        super(R.layout.item_rv,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Bean item) {
        helper.setText(R.id.tv,item.getName());
    }
}
