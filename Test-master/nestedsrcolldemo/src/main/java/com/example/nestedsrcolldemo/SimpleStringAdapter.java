package com.example.nestedsrcolldemo;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author : zhangzf
 * date   : 2021/5/17
 * desc   :
 */
public class SimpleStringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SimpleStringAdapter(@Nullable  List<String> data) {
        super(R.layout.item_single_text,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);
    }
}
