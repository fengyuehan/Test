package com.example.algorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.algorithm.niukewang.MinNumberInRotateArray;

public class MainActivity extends AppCompatActivity {
    int[] arr = {3,4,5,1,2};
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        textView.setText(MinNumberInRotateArray.minNumberInRotateArray(arr) + "");
    }
}
