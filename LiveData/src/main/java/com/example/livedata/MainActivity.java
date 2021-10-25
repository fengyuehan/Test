package com.example.livedata;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.livedata.CityPicker.CityPickerActivity;
import com.example.livedata.CommonTabLayout.CommonTabLayoutActivity;
import com.example.livedata.SlidingTabLayout.SlidingTabLayout.SlidingTabLayoutActivity;
import com.example.livedata.livedataAndLifecycle.TestActivity;

public class MainActivity extends AppCompatActivity {

    private Button button,btn_stl,btn_ctl,btn_city,btn_test;
    private TestViewModule mTestViewModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        btn_stl = findViewById(R.id.btn_stl);
        //btn_ctl = findViewById(R.id.btn_ctl);
        btn_city = findViewById(R.id.btn_city);
        btn_test = findViewById(R.id.btn_test);
        mTestViewModule = new ViewModelProvider(this).get(TestViewModule.class);
        mTestViewModule.testLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(MainActivity.this, CommonTabLayoutActivity.class));
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestViewModule.getTestLiveData().setValue(3);
            }
        });
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
