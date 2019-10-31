package com.example.eventbus.conmon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.eventbus.R;

import org.greenrobot.eventbus.EventBus;

public class CommonActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        btn = findViewById(R.id.btn_send_message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setAge(15);
                messageEvent.setName("小明");
                EventBus.getDefault().post(messageEvent);

                finish();
            }
        });
    }
}
