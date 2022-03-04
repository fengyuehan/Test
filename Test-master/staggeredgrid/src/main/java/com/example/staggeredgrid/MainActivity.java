package com.example.staggeredgrid;

import android.graphics.Color;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItem;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);
        /*mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));*/
        mSwipeRefreshLayout.setProgressViewOffset(true,50,250);//50 是圈圈出现的位置距离顶端的偏移量，250 是小圈圈转动时距离顶端的偏移量。
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<>();
                        for (int i = 0; i< 5; i++){
                            newDatas.add("new item" + i);
                        }
                        mMyAdapter.addItem(newDatas);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "更新了5条数据", Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mMyAdapter.getItemCount()&& !isRefresh){
                    isRefresh = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> newDatas = new ArrayList<>();
                            for (int i = 0; i < 10; i++){
                                newDatas.add("more item" + i);
                            }
                            mMyAdapter.addMoreItem(newDatas);
                            Toast.makeText(MainActivity.this, "上拉加载了10条数据", Toast.LENGTH_SHORT).show();
                            isRefresh = false;
                        }
                    },2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rv);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED);
        mMyAdapter = new MyAdapter(this);

    }

}
