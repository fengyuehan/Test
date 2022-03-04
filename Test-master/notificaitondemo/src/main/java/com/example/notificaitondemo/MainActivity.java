package com.example.notificaitondemo;

import android.app.NotificationManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_simple,btn_action,btn_remote_input,btn_big_picture_style,btn_big_text_style,btn_inbox_style,
            btn_media_style,btn_messaging_style,btn_progress,btn_custom_heads_up,btn_custom,btn_clear_all;
    private NotificationManager mNM;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        btn_simple.setOnClickListener(this);
        btn_action.setOnClickListener(this);
        btn_remote_input.setOnClickListener(this);
        btn_big_picture_style.setOnClickListener(this);
        btn_big_text_style.setOnClickListener(this);
        btn_inbox_style.setOnClickListener(this);
        btn_media_style.setOnClickListener(this);
        btn_messaging_style.setOnClickListener(this);
        btn_progress.setOnClickListener(this);
        btn_custom_heads_up.setOnClickListener(this);
        btn_custom.setOnClickListener(this);
        btn_clear_all.setOnClickListener(this);
    }

    private void initView() {
        btn_simple = findViewById(R.id.btn_simple);
        btn_action = findViewById(R.id.btn_action);
        btn_remote_input = findViewById(R.id.btn_remote_input);
        btn_big_picture_style = findViewById(R.id.btn_big_picture_style);
        btn_big_text_style = findViewById(R.id.btn_big_text_style);
        btn_inbox_style = findViewById(R.id.btn_inbox_style);
        btn_media_style = findViewById(R.id.btn_media_style);
        btn_messaging_style = findViewById(R.id.btn_messaging_style);
        btn_progress = findViewById(R.id.btn_progress);
        btn_custom_heads_up = findViewById(R.id.btn_custom_heads_up);
        btn_custom = findViewById(R.id.btn_custom);
        btn_clear_all = findViewById(R.id.btn_clear_all);
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mContext = this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simple:

                break;
            case R.id.btn_action:

                break;
            case R.id.btn_remote_input:

                break;
            case R.id.btn_big_picture_style:

                break;
            case R.id.btn_big_text_style:

                break;
            case R.id.btn_inbox_style:

                break;
            case R.id.btn_media_style:

                break;
            case R.id.btn_messaging_style:

                break;
            case R.id.btn_progress:

                break;
            case R.id.btn_custom_heads_up:

                break;
            case R.id.btn_custom:

                break;
            case R.id.btn_clear_all:
                Notificaitons.getInstance().clearAllNotification(mNM);
                break;
        }
    }
}
