package com.example.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : zhangzf
 * date   : 2021/5/6
 * desc   :
 */
public class InvalidateActivity extends AppCompatActivity {
    private Button button;
    private CustomEmptyView customEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalidate);
        button = findViewById(R.id.btn);
        customEmptyView = findViewById(R.id.cev);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.invalidate();
                //button.requestLayout();
                customEmptyView.requestLayout();
            }
        });
    }
}
