package com.example.customview;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TextActivity extends AppCompatActivity {
    private VerticalTextIndicatorView verticalTextIndicatorView;
    private List<String> list;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        verticalTextIndicatorView = findViewById(R.id.vtiv);
        list = new ArrayList<>();
        initData();
        verticalTextIndicatorView.setTextList(list);
        verticalTextIndicatorView.setListener(new VerticalTextIndicatorView.OnTextSelectListener() {
            @Override
            public void onNumSelect(String selectedText) {
                Toast.makeText(TextActivity.this,selectedText,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData() {
        list.add("更多");
        list.add("录像");
        list.add("拍照");
        list.add("美肤");
    }
}
