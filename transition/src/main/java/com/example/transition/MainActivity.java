package com.example.transition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView home_transition1,home_transition2,home_transition3,home_transition4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        home_transition1 = findViewById(R.id.home_transition1);
        home_transition2 = findViewById(R.id.home_transition2);
        home_transition3 = findViewById(R.id.home_transition3);
        home_transition4 = findViewById(R.id.home_transition4);
        home_transition1.setOnClickListener(this);
        home_transition2.setOnClickListener(this);
        home_transition3.setOnClickListener(this);
        home_transition4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_transition1:
                startActivity(new Intent(MainActivity.this,TransitionOneActivity.class));
                break;
            case R.id.home_transition2:
                startActivity(new Intent(MainActivity.this,TransitionTwoActivity.class));
                break;
            case R.id.home_transition3:
                startActivity(new Intent(MainActivity.this,TransitionThreeActivity.class));
                break;
            case R.id.home_transition4:
                startActivity(new Intent(MainActivity.this,MotionActivity.class));
                break;
        }
    }
}
