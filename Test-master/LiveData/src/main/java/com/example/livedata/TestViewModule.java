package com.example.livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModule  extends ViewModel {
    public MutableLiveData<Integer> testLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getTestLiveData() {
        return testLiveData;
    }
}
