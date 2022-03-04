package com.example.livedatabus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.livedatabus.define.BusBean;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class LiveEventBusActivity extends AppCompatActivity {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.btn);
        /*LiveEventBus.get("LiveEventBusActivity", BusBean.class).observe(this, new Observer<BusBean>() {
            @Override
            public void onChanged(BusBean busBean) {
                textView.setText(busBean.getName() +"----"+ busBean.getType());
            }
        });*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusBean busBean = new BusBean();
                busBean.setName("zzf");
                busBean.setType("对面收消息了");
                LiveEventBus.get("LiveEventBusActivity",BusBean.class).post(busBean);
                startActivity(new Intent(LiveEventBusActivity.this,MainActivity.class));
            }
        });
    }
}
