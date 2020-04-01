package com.example.daggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daggerdemo.bean.Cola;
import com.example.daggerdemo.bean.Dicos;
import com.example.daggerdemo.di.component.DaggerColaComponent;
import com.example.daggerdemo.di.model.DicosModule;


import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @com.example.daggerdemo.di.qualifier.Cola
    @Inject
    Cola cola;

    @com.example.daggerdemo.di.qualifier.Dicos
    @Inject
    Dicos dicos;

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerColaComponent.builder().dicosModule(new DicosModule()).build().inject(this);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.btn);
        textView.setText(cola.getName() + "---" + dicos.returnDicos());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ModelActivity.class));
            }
        });
    }
}
