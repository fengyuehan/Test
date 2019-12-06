package com.example.eventbus.sticky;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.eventbus.R;
import com.example.eventbus.conmon.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class StickyActivity extends AppCompatActivity {
    public Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        button = findViewById(R.id.btn_sticky);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setName("hehe");
                messageEvent.setAge(20);
                EventBus.getDefault().postSticky(messageEvent);

                finish();
            }
        });
    }
}
