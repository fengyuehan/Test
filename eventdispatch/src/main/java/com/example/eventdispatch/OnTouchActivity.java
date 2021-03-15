package com.example.eventdispatch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : zhangzf
 * date   : 2021/3/4
 * desc   :
 */
public class OnTouchActivity extends AppCompatActivity {
    private MyButton my_button;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        my_button = findViewById(R.id.my_button);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","---------onClick1---------");
            }
        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("zzf","----------onLongClick1----------");
                return false;
            }
        });
        my_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","---------onClick---------");
            }
        });

        my_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("zzf","----------onLongClick----------");
                return false;
            }
        });

        my_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("zzf","----------onTouch----------");
                return false;
            }
        });
    }
}
