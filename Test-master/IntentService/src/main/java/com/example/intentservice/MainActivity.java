package com.example.intentservice;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intentservice.bindservice.BindServiceActivity;
import com.example.intentservice.handleThread.HandleThreadActivity;
import com.example.intentservice.intentservice.IntentServiceActivity;
import com.example.intentservice.service.SimpleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start_service,btn_bind_service,btn_intent_service,btn_handle_thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

    }

    private void initListener() {
        btn_start_service.setOnClickListener(this);
        btn_bind_service.setOnClickListener(this);
        btn_intent_service.setOnClickListener(this);
        btn_handle_thread.setOnClickListener(this);
    }

    private void initView() {
        btn_start_service = findViewById(R.id.btn_start_service);
        btn_bind_service = findViewById(R.id.btn_bind_service);
        btn_intent_service = findViewById(R.id.btn_intent_service);
        btn_handle_thread = findViewById(R.id.btn_handle_thread);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                startActivity(new Intent(MainActivity.this,SimpleActivity.class));
                break;
            case R.id.btn_bind_service:
                startActivity(new Intent(MainActivity.this,BindServiceActivity.class));
                break;
            case R.id.btn_intent_service:
                startActivity(new Intent(MainActivity.this, IntentServiceActivity.class));
                break;
            case R.id.btn_handle_thread:
                startActivity(new Intent(MainActivity.this, HandleThreadActivity.class));
                break;
                default:
                    break;
        }
    }
}
