package com.example.livedata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.livedata.CityPicker.CityPickerActivity;
import com.example.livedata.CommonTabLayout.CommonTabLayoutActivity;
import com.example.livedata.SlidingTabLayout.SlidingTabLayout.SlidingTabLayoutActivity;
import com.example.livedata.livedataAndLifecycle.TestActivity;

public class MainActivity extends AppCompatActivity {

    private Button button,btn_stl,btn_ctl,btn_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        btn_stl = findViewById(R.id.btn_stl);
        //btn_ctl = findViewById(R.id.btn_ctl);
        btn_city = findViewById(R.id.btn_city);
        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityPickerActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        btn_stl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SlidingTabLayoutActivity.class));
            }
        });

        /*btn_ctl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommonTabLayoutActivity.class));
            }
        });*/
    }
}
