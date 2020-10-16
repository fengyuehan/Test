package com.example.customview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TextAdapter(@Nullable List<String> data) {
        super(R.layout.item_text,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
