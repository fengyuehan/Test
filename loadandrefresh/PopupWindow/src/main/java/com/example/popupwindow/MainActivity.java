package com.example.popupwindow;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button button1,button2;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.btn_start);
        button2 = findViewById(R.id.btn_stop);
        imageView = findViewById(R.id.iv);

        if(animationDrawable ==null){
            imageView.setImageResource(R.drawable.loading_amin);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!animationDrawable.isRunning()){
                    animationDrawable.start();
                }*/
                animationDrawable.start();
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable.isRunning()){
                    animationDrawable.stop();
                }
            }
        });
    }
}
