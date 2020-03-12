package com.example.roomdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.roomdemo.R;
import com.example.roomdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mActivityMainBinding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_room:
                startActivity(new Intent(this,RoomActivity.class));
                break;
            case R.id.btn_liveData:
                startActivity(new Intent(this,LiveDataActivity.class));
                break;
            case R.id.btn_viewModel:

                break;
            case R.id.btn_with_lifecycles:
                startActivity(new Intent(this,LifecyclesActivity.class));
                break;
            case R.id.btn_with_livedata:

                break;
            case R.id.btn_with_rxjava:
                startActivity(new Intent(this,WithRxJavaActivity.class));
                break;
        }
    }
}
