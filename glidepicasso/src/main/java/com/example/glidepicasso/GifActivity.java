package com.example.glidepicasso;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.glidepicasso.extension.GlideApp;

public class GifActivity extends AppCompatActivity {
    private Button showGif;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        imageView = findViewById(R.id.display);
        showGif = findViewById(R.id.showGif);

        showGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*GlideApp.with(GifActivity.this)
                        .asGif2()
                        .load(R.mipmap.a11)
                        .into(imageView);*/
            }
        });
    }
}
