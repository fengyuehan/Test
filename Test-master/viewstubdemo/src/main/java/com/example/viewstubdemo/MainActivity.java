package com.example.viewstubdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_vs_showView,btn_vs_changeHint,btn_vs_hideView;
    private TextView tv_vsContent;
    private ViewStub viewstub_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
    }

    private void initListener() {
        btn_vs_showView.setOnClickListener(this);
        btn_vs_changeHint.setOnClickListener(this);
        btn_vs_hideView.setOnClickListener(this);
    }

    private void init() {
        btn_vs_showView = findViewById(R.id.btn_vs_showView);
        btn_vs_changeHint = findViewById(R.id.btn_vs_changeHint);
        btn_vs_hideView = findViewById(R.id.btn_vs_hideView);
        viewstub_test = findViewById(R.id.viewstub_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_vs_showView:
                showViewStub();
                break;
            case R.id.btn_vs_changeHint:
                viewstub_test.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_vs_hideView:
                if (tv_vsContent != null){
                    tv_vsContent.setText("大家好才是真的好");
                }
                break;
        }
    }

    private void showViewStub() {
        try {
            View view = viewstub_test.inflate();
            tv_vsContent = view.findViewById(R.id.tv_vsContent);
        }catch (Exception e){
            viewstub_test.setVisibility(View.VISIBLE);
        }finally {
            tv_vsContent.setText("你好");
        }

    }
}
