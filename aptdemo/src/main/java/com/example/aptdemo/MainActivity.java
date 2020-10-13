package com.example.aptdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.annotations.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomButterknife.bind(this);
        button.setText("123");
    }
}

