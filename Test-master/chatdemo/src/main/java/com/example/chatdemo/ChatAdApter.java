package com.example.chatdemo;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ChatAdApter extends BaseQuickAdapter<ChatBean, BaseViewHolder> {
    public ChatAdApter(@Nullable List<ChatBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {

    }
}
