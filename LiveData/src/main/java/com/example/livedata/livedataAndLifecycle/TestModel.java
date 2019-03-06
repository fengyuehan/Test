package com.example.livedata.livedataAndLifecycle;

import android.arch.lifecycle.MutableLiveData;

public class TestModel {
    private MutableLiveData<String> status;

    public MutableLiveData<String> getStatus() {
        if (status == null){
            status = new MutableLiveData<>();
        }
        return status;
    }
}
