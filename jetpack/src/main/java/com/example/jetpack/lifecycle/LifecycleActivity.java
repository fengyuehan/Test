package com.example.jetpack.lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jetpack.R;
import com.example.jetpack.lifecycle.LifecycleObserverImpl;

public class LifecycleActivity extends AppCompatActivity {

    private LifecycleObserverImpl lifecycleObserver;
    private LifecycleHandler mLifecycleHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        lifecycleObserver = new LifecycleObserverImpl();
        getLifecycle().addObserver(lifecycleObserver);
        mLifecycleHandler = new LifecycleHandler(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(lifecycleObserver);
    }
}
