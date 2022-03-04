package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HorizontalAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {
    public HorizontalAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.item_horizontal,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        RecyclerView recyclerView = helper.getView(R.id.rv_item);
        SubHorizontalAdapter subVerticalAdapter = new SubHorizontalAdapter(item.getSubTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(subVerticalAdapter);
    }
}
