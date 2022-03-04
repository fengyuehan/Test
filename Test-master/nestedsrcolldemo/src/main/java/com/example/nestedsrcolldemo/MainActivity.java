package com.example.nestedsrcolldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_nested_scrolling_tradition,btn_nested_scrolling,btn_nested_scrolling2,btn_nested_scrolling2Demo,btn_coordinator_layout,btn_coor_with_appbar,btn_coor_with_appbar_with_coll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_nested_scrolling_tradition = findViewById(R.id.btn_nested_scrolling_tradition);
        btn_nested_scrolling = findViewById(R.id.btn_nested_scrolling);
        btn_nested_scrolling2 = findViewById(R.id.btn_nested_scrolling2);
        btn_nested_scrolling2Demo = findViewById(R.id.btn_nested_scrolling2Demo);
        btn_coordinator_layout = findViewById(R.id.btn_coordinator_layout);
        btn_coor_with_appbar = findViewById(R.id.btn_coor_with_appbar);
        btn_coor_with_appbar_with_coll = findViewById(R.id.btn_coor_with_appbar_with_coll);
        btn_nested_scrolling.setOnClickListener(this);
        btn_nested_scrolling2.setOnClickListener(this);
        btn_nested_scrolling2Demo.setOnClickListener(this);
        btn_coordinator_layout.setOnClickListener(this);
        btn_coor_with_appbar.setOnClickListener(this);
        btn_coor_with_appbar_with_coll.setOnClickListener(this);
        btn_nested_scrolling_tradition.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nested_scrolling_tradition:
                /**
                 * 这种方法会有卡顿的感觉
                 */
                startActivity(new Intent(MainActivity.this,NestedTraditionActivity.class));
                break;
        }

    }
}