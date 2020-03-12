package com.example.roomdemo.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.roomdemo.R;
import com.example.roomdemo.observer.MyObserver;


public class LifecyclesActivity extends AppCompatActivity implements LifecycleOwner {
    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycles);
        Log.i("观察LiveData", " 没有走吗啊！");
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("观察LiveData", " ==> " + s);
            }
        });
        getLifecycle().addObserver(new MyObserver());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    public LifecycleRegistry getLifecycleRegistry() {
        return lifecycleRegistry;
    }

    @Override
    protected void onStop() {
        super.onStop();
        liveData.postValue("运行试试");
    }
}
