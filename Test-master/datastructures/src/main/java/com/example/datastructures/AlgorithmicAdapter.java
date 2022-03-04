package com.example.datastructures;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @ClassName AlgorithmicAdapter
 * @Description TODO
 * @Author user
 * @Date 2019/9/6
 * @Version 1.0
 */
public class AlgorithmicAdapter extends BaseQuickAdapter<AlgorithmicBean, BaseViewHolder> {
    public AlgorithmicAdapter(@Nullable List<AlgorithmicBean> data) {
        super(R.layout.item_algorithmic,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlgorithmicBean item) {
        helper.setText(R.id.tv_name,item.getName());
    }
}
