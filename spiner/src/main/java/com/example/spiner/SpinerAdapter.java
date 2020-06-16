package com.example.spiner;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SpinerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SpinerAdapter(@Nullable List<String> data) {
        super(R.layout.item_text,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
