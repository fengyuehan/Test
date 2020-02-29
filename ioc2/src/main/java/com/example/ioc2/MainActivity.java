package com.example.ioc2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

@MyContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.button1)
    Button button;
    @BindView(R.id.button2)
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        button.setText("haha");
        button1.setText("hehe");
    }
}
