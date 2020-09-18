package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("age",1);
                /**
                 * 这里必须指明是哪个activity来进行跳转的
                 * 不然在onActivityResult就会出错
                 */
                LoginActivity.this.setResult(1,intent);
                LoginActivity.this.finish();
            }
        });
    }
}
