package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jetpack.databinding.DataBindingActivity;
import com.example.jetpack.hilt.TestActivity;
import com.example.jetpack.ktpaging.KtPagingActivity;
import com.example.jetpack.lifecycle.LifecycleActivity;
import com.example.jetpack.livedata.LiveDataActivity;
import com.example.jetpack.navigation.NavigationActivity;
import com.example.jetpack.paging3.PagingActivity;
import com.example.jetpack.room.RoomActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_lifecycle,btn_livedata,btn_navigation,btn_databinding,btn_room,btn_paging,btn_kt_paging,btn_kt_hilt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
    }

    private void initListener() {
        btn_lifecycle.setOnClickListener(this);
        btn_livedata.setOnClickListener(this);
        btn_navigation.setOnClickListener(this);
        btn_databinding.setOnClickListener(this);
        btn_room.setOnClickListener(this);
        btn_paging.setOnClickListener(this);
        btn_kt_paging.setOnClickListener(this);
        btn_kt_hilt.setOnClickListener(this);
    }

    private void init() {
        btn_lifecycle = findViewById(R.id.btn_lifecycle);
        btn_livedata = findViewById(R.id.btn_livedata);
        btn_navigation  = findViewById(R.id.btn_navigation);
        btn_databinding = findViewById(R.id.btn_databinding);
        btn_room = findViewById(R.id.btn_room);
        btn_paging = findViewById(R.id.btn_paging);
        btn_kt_paging = findViewById(R.id.btn_kt_paging);
        btn_kt_hilt = findViewById(R.id.btn_kt_hilt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_lifecycle:
                startActivity(new Intent(MainActivity.this, LifecycleActivity.class));
                break;
            case R.id.btn_livedata:
                startActivity(new Intent(MainActivity.this, LiveDataActivity.class));
                break;
            case R.id.btn_navigation:
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                break;
            case R.id.btn_databinding:
                startActivity(new Intent(MainActivity.this, DataBindingActivity.class));
                break;
            case R.id.btn_room:
                startActivity(new Intent(MainActivity.this, RoomActivity.class));
                break;
            case R.id.btn_paging:
                startActivity(new Intent(MainActivity.this, PagingActivity.class));
                break;
            case R.id.btn_kt_paging:
                startActivity(new Intent(MainActivity.this, KtPagingActivity.class));
                break;
            case R.id.btn_kt_hilt:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
        }
    }
}
