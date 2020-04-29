package com.example.dayandnightchange;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ReflectActivity extends AppCompatActivity implements OnThemeChangeListener{
    Button button;
    RelativeLayout rl;
    TextView tv;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        ThemeManager.registerThemeChangeListener(this);
        button = findViewById(R.id.btn);
        rl = findViewById(R.id.rl);
        tv = findViewById(R.id.tv);
        actionBar = getSupportActionBar();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeManager.setThemeMode(ThemeManager.getmThemeMode() == ThemeManager.ThemeMode.DAY ? ThemeManager.ThemeMode.NIGHT: ThemeManager.ThemeMode.DAY);
            }
        });
    }

    @Override
    public void onThemeChanged() {
        initTheme();
    }

    private void initTheme() {
        tv.setTextColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this,R.color.textColor)));
        rl.setBackgroundColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this,R.color.backgroundColor)));
        button.setTextColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this,R.color.textColor)));
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(ThemeManager.getCurrentThemeRes(this,R.color.colorPrimary))));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(ThemeManager.getCurrentThemeRes(this,R.color.colorPrimary)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeChangeListener(this);
    }
}
