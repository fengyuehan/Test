package com.example.smartrefreshlayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private Button button;
    String str = "M2CqWfANMtUPjrecL+0XxNJIKmloFPSI7SUzrCCfoZomgpuyGgnCILoiapjipiThOeyTqLscOFLCBSKcNBaXqoNBY5vqxo2J8SCOI0yraqcQvJ6RRjLca1FgT1kR+ciTLJ5yjZtbgplqC2Q3m/l/cVzzAHbuCBllR5yXQp8BEa4=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encode = URLEncoder.encode(str,"UTF-8");
                    Log.e("zzf","encode = "+encode);
                    String decode = URLDecoder.decode(encode,"UTF-8");
                    Log.e("zzf","decode = "+decode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
