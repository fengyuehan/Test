package com.example.camerademo;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LuZhiActivity extends AppCompatActivity {
    private Camera mCamera;
    private CameraPreview mPreview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mCamera = getCameraInstance();

    }

    private Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }


}
