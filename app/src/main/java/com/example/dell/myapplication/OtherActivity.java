package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : zhangzf
 * date   : 2021/6/10
 * desc   :
 */
public class OtherActivity extends AppCompatActivity {
    private TextView tv_back,tv_jump,tv_jump1;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        tv_back = findViewById(R.id.tv_back);
        tv_jump = findViewById(R.id.tv_jump);
        tv_jump1 = findViewById(R.id.tv_jump1);

        tv_jump1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data","返回值成功");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zzf","onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","onPause");
    }

}
