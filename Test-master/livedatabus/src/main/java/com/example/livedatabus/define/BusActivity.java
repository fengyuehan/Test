package com.example.livedatabus.define;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.livedatabus.R;

public class BusActivity extends AppCompatActivity {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.btn);
        Log.e("zzf","-------------");
       /* LiveDataBus.getInstance().with("MainActivity",BusBean.class).observe(this, new Observer<BusBean>() {
            @Override
            public void onChanged(BusBean busBean) {
                if (busBean != null){
                    Log.e("zzf",busBean.toString());
                    textView.setText(busBean.getName() + ":" + busBean.getType());
                    Toast.makeText(BusActivity.this,busBean.getName() + ":" + busBean.getType(),Toast.LENGTH_LONG).show();
                }
            }
        });*/
       LiveDataBus.getInstance().with("MainActivity",String.class).observe(this, new Observer<String>() {
           @Override
           public void onChanged(String s) {
               textView.setText(s);
               Toast.makeText(BusActivity.this,s,Toast.LENGTH_LONG).show();
           }
       });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.getInstance().with("zzf",String.class).postValue("msg");
            }
        });
    }
}
