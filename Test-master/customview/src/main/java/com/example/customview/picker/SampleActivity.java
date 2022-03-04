package com.example.customview.picker;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.customview.R;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
    private ScrollPickerView mScrollPickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);
        initView();
        initData();
    }


    private void initView() {
        mScrollPickerView = findViewById(R.id.scroll_picker_view);
        mScrollPickerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        List<MyData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MyData myData = new MyData();
            myData.id = i;
            myData.text = "我是第" + i + "个item";
            list.add(myData);
        }

        ScrollPickerAdapter.ScrollPickerAdapterBuilder<MyData> builder =
                new ScrollPickerAdapter.ScrollPickerAdapterBuilder<MyData>(this)
                        .setDataList(list)
                        .selectedItemOffset(1)
                        .visibleItemNumber(4)
                        .setItemViewProvider(new MyViewProvider())
                        .setOnScrolledListener(new ScrollPickerAdapter.OnScrollListener() {
                            @Override
                            public void onScrolled(View currentItemView) {
                                MyData myData = (MyData) currentItemView.getTag();
                                Log.e("zzf",myData.text);
                    }
                });
        ScrollPickerAdapter mScrollPickerAdapter = builder.build();
        mScrollPickerView.setAdapter(mScrollPickerAdapter);
    }
}
