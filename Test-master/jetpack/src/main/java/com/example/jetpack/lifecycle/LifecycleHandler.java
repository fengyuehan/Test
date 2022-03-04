package com.example.jetpack.lifecycle;

import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 具有生命周期感知的Handler,防止内存泄漏
 */
public class LifecycleHandler extends Handler implements LifecycleObserver {

    private LifecycleOwner lifecycleOwner;

    public LifecycleHandler(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    /**
     * 被OnLifecycleEvent修饰的不能用private修饰
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        removeCallbacksAndMessages(null);
        lifecycleOwner.getLifecycle().removeObserver(this);
    }
}
