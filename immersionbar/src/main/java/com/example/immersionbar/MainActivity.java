package com.example.immersionbar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {
    private Button button;

    @Override
    protected void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DefineViewActivity.class));
            }
        });
    }

    @Override
    protected void initView() {
        button = findViewById(R.id.btn);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
