package com.example.materialdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.materialdesign.CoordinatorLayout.CoordinatorLayoutSimpleActivity;
import com.example.materialdesign.FloatingActionButton.NavigationViewActivity;
import com.example.materialdesign.toolbar.ToolBarSimpleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_toolbar,btn_corrdinator,btn_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_toolbar = findViewById(R.id.btn_toolbar);
        btn_corrdinator = findViewById(R.id.btn_corrdinator);
        btn_navigation = findViewById(R.id.btn_navigation);
        btn_navigation.setOnClickListener(this);
        btn_toolbar.setOnClickListener(this);
        btn_corrdinator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toolbar:
                startActivity(new Intent(MainActivity.this, ToolBarSimpleActivity.class));
                break;
            case R.id.btn_corrdinator:
                startActivity(new Intent(MainActivity.this, CoordinatorLayoutSimpleActivity.class));
                break;
            case R.id.btn_navigation:
                startActivity(new Intent(MainActivity.this, NavigationViewActivity.class));
                break;
        }
    }
}
