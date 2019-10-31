package com.example.datastructures;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MainActivity
 * @Description TODO
 * @Author user
 * @Date 2019/9/6
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private String[] mData;
    private RecyclerView mRecyclerView;
    private AlgorithmicAdapter mAlgorithmicAdapter;
    private List<AlgorithmicBean> mAlgorithmicData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mAlgorithmicData = new ArrayList<>();
        mData = new String[]{
                "二维数组中的查找"
        };

        for (int i = 0; i < mData.length; i++){
            AlgorithmicBean mAlgorithmicBean = new AlgorithmicBean();
            mAlgorithmicBean.setName(mData[i]);
            mAlgorithmicData.add(mAlgorithmicBean);
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAlgorithmicAdapter = new AlgorithmicAdapter(mAlgorithmicData);
        mRecyclerView.setAdapter(mAlgorithmicAdapter);
        mAlgorithmicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,ArraySearchActivity.class));
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                }
            }
        });
    }
}
