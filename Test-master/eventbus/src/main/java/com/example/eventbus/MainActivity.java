package com.example.eventbus;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventbus.conmon.CommonActivity;
import com.example.eventbus.conmon.MessageEvent;
import com.example.eventbus.sticky.StickyActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    private Button btn,btn1;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        //采用建造者模式。当有多项配置时，会采用这种模式
        btn = findViewById(R.id.btn_reeive_message);
        btn1 = findViewById(R.id.btn_send_message);
        tv = findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommonActivity.class));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StickyActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void messageEventBus1(MessageEvent event){
        tv.setText("name" + event.getName()+"----"+"age" + event.getAge());
        Toast.makeText(this, "name" + event.getName()+"----"+"age" + event.getAge(), Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        tv.setText("name" + event.getName()+"----"+"age" + event.getAge());
        Toast.makeText(this, "name" + event.getName()+"----"+"age" + event.getAge(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
