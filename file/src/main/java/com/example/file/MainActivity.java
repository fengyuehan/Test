package com.example.file;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
            Log.d("ZZF", "--created --" + file.mkdirs());
        }
    }
}
