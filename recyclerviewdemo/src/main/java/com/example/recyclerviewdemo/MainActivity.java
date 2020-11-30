package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewdemo.recyclerPool.RecyclerViewPoolActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_horizontal,btn_vertical,btn_recycler_pool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
    }

    private void initListener() {
        btn_horizontal.setOnClickListener(this);
        btn_vertical.setOnClickListener(this);
        btn_recycler_pool.setOnClickListener(this);
    }

    private void init() {
        btn_horizontal = findViewById(R.id.btn_horizontal);
        btn_vertical = findViewById(R.id.btn_vertical);
        btn_recycler_pool = findViewById(R.id.btn_recycler_pool);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_horizontal:
                startActivity(new Intent(MainActivity.this,HorizontalActivity.class));
                break;
            case R.id.btn_vertical:
                startActivity(new Intent(MainActivity.this,VerticalActivity.class));
                break;
            case R.id.btn_recycler_pool:
                startActivity(new Intent(MainActivity.this, RecyclerViewPoolActivity.class));
                break;
        }
    }
}
