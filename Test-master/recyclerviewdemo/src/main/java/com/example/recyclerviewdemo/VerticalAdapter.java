package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class VerticalAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {
    public VerticalAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.item_vertical,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        RecyclerView recyclerView = helper.getView(R.id.rv_item);
        SubVerticalAdapter subVerticalAdapter = new SubVerticalAdapter(item.getSubTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(subVerticalAdapter);
    }
}
