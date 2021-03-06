package com.example.dayandnightchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * 夜间模式实现的三种方式
     * 1、setTheme
     * 2、UiMode支持日间、夜间模式的切换
     * 3、通过资源id的映射
     */

    /**
     * setTheme模式是通过设置两套主题
     * 在setContentView()之前设置Theme
     * 然后recraate()
     */

    /**
     * UiMode
     * 需要设置两套color
     * 然后recraate()
     * 需要设置application
     */

    /**
     * 前两种是需要recraate()，会导致白屏或者黑屏的出现
     * 在回调的方法中需要设置每一项 UI 相关的属性值
     */
    private Button button,button1,button2;
    private int theme = R.style.AppTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            theme = savedInstanceState.getInt("theme");
            //必须在setContentView之前设置，不然不起效
            setTheme(theme);
        }
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn2);
        button2 = findViewById(R.id.btn3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //theme = (theme == R.style.AppTheme)? R.style.NightAppTheme : R.style.AppTheme;
                recreate();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UiModeActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReflectActivity.class));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
    }
}
