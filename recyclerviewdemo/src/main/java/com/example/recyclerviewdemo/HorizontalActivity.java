package com.example.recyclerviewdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemBean> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        recyclerView = findViewById(R.id.rv);
        list = new ArrayList<>();
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(list);
        recyclerView.setAdapter(horizontalAdapter);
    }

    private void initData() {
        for (int i = 0;i < 10;i++){
            ItemBean itemBean = new ItemBean();
            itemBean.setTitle("title1" + i);
            List<String> subList = new ArrayList<>();
            for (int j = 0; j < 10; j++){
                subList.add("inner title" + j);
            }
            itemBean.setSubTitle(subList);
            list.add(itemBean);
        }
    }
}
