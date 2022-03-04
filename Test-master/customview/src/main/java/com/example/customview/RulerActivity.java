package com.example.customview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.ruler.RulerView;

public class RulerActivity extends AppCompatActivity {
    RulerView rulerView;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
        rulerView = findViewById(R.id.ruler_view);
        textView = findViewById(R.id.tvNum);
        rulerView.setOnNumSelectListener(new RulerView.OnNumSelectListener() {
            @Override
            public void onNumSelect(int selectedNum) {
                textView.setText(selectedNum + " cm");
                textView.setTextColor(rulerView.getIndicatorColor());
            }
        });
    }
}
