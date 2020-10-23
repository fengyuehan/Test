package com.example.jetpack.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpack.R;
import com.example.jetpack.livedata.MyViewModel;

public class LiveDataActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private MyViewModel myViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        textView = findViewById(R.id.tv_num);
        button = findViewById(R.id.btn);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = myViewModel.increaseNumber();
                myViewModel.getInfo().setValue("info = " + x);
            }
        });
        myViewModel.getInfo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
    }
}
