package com.example.banner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LayoutInflaterActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout,constraintLayout1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

        constraintLayout = findViewById(R.id.container);

        /*View view = LayoutInflater.from(this).inflate(R.layout.inflate_layout,constraintLayout,true);
        constraintLayout1 = view.findViewById(R.id.constraintLayout);*/
        View view1 = View.inflate(this,R.layout.inflate_layout,null);
        constraintLayout1 = view1.findViewById(R.id.constraintLayout);        //constraintLayout.addView(view);
    }
}
