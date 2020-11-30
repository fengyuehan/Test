package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SubVerticalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SubVerticalAdapter(@Nullable List<String> data) {
        super(R.layout.sub_item_vertical,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
