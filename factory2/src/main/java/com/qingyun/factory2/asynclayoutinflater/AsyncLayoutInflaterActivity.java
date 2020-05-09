package com.qingyun.factory2.asynclayoutinflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import com.qingyun.factory2.R;

public class AsyncLayoutInflaterActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncLayoutInflater(this).inflate(R.layout.activity_asynclayoutinflater, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                setContentView(view);
                initView(view);

            }
        });

    }

    private void initView(View view) {
        textView = view.findViewById(R.id.tv);
        textView.setText("ni  hao");
    }
}
