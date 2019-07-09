package com.example.keyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.keyboard.ruler.RulerView;

public class RulerActivity extends AppCompatActivity {
    private RulerView rulerView;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
        rulerView = findViewById(R.id.rv);
        textView = findViewById(R.id.tv);
        rulerView.setFmChanel(Double.parseDouble(95.5+""));
        rulerView.setOnMoveActionListener(new RulerView.OnMoveActionListener() {
            @Override
            public void onMove(double x) {
                textView.setText(x + "");
            }
        });
    }
}
