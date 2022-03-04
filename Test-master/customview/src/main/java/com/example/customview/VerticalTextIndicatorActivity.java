package com.example.customview;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class VerticalTextIndicatorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> list;
    private LinearLayoutManager linearLayoutManager;
    private TextAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        list = new ArrayList<>();
        initData();
        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TextAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("zzf",dy+"");
            }
        });
    }

    private void initData() {
        list.add("更多");
        list.add("录像");
        list.add("拍照");
        list.add("美肤");
    }

    public static void moveToPosition(RecyclerView rv, int position) {
        if (rv != null && rv.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
            int topPosition = layoutManager.findFirstVisibleItemPosition();
            int bottomPosition = layoutManager.findLastVisibleItemPosition();
            int itemHeight = rv.getChildAt(topPosition).getHeight();
            int minVisibleViewCount = rv.getHeight() / itemHeight;
            if (position < topPosition) {
                rv.smoothScrollBy(0, -(topPosition - position) * itemHeight);
            } else if (position > bottomPosition) {
                rv.smoothScrollBy(0, (position - bottomPosition + minVisibleViewCount - 1) * itemHeight);
            } else {
                int movePosition = position - topPosition;
                int top = rv.getChildAt(movePosition).getTop();
                rv.smoothScrollBy(0, top);
            }
        }
    }
}
