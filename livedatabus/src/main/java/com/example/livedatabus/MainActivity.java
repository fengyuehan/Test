package com.example.livedatabus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.livedatabus.define.BusActivity;
import com.example.livedatabus.define.BusBean;
import com.example.livedatabus.define.LiveDataBus;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class MainActivity extends AppCompatActivity {
    private Button button,button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn2);
        button2 = findViewById(R.id.btn3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BusBean busBean = new BusBean();
                busBean.setName("zzf");
                busBean.setType("对面收消息了");*/
                LiveDataBus.getInstance().with("MainActivity",String.class).setValue("zzf");
                //Toast.makeText(MainActivity.this,"消息发送完成",Toast.LENGTH_LONG).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BusActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BusBean busBean = new BusBean();
                busBean.setName("zzf");
                busBean.setType("对面收消息了");
                LiveEventBus.get("LiveEventBusActivity",BusBean.class).post(busBean);*/
                startActivity(new Intent(MainActivity.this,LiveEventBusActivity.class));
            }
        });
        LiveDataBus.getInstance().with("zzf",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });
        LiveEventBus.get("LiveEventBusActivity", BusBean.class).observe(this, new Observer<BusBean>() {
            @Override
            public void onChanged(BusBean busBean) {
                Toast.makeText(MainActivity.this,busBean.getName() +"----"+ busBean.getType(),Toast.LENGTH_SHORT).show();
                //textView.setText(busBean.getName() +"----"+ busBean.getType());
            }
        });

    }
}
