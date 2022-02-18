package com.example.daggerdemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daggerdemo.bean.Dicos;

import javax.inject.Inject;

public class ModelActivity extends AppCompatActivity {
    TextView textView;
    @Inject
    Dicos dicos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        textView = findViewById(R.id.tv);

        textView.setText(dicos.returnDicos());
    }
}
