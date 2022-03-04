package com.example.roomdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveDataViewModel extends ViewModel {
    private MutableLiveData<String> nameLiveData = new MutableLiveData<>();
    public MutableLiveData<String> getData(){
        return nameLiveData;
    }
}
