package com.example.hilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private Button button;
    /**
     * Dagger does not support injection into private fields
     *     private Truck truck;
     *
     * 用Inject注入的前面的修饰符不能为private
     */
    @Inject
    public Truck truck;

    @Inject
    Car car;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MultiActivity.class));
            }
        });
        truck.drliver();
        car.car();


    }
}