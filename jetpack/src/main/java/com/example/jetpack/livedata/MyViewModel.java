package com.example.jetpack.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> infos;
    private int number;

    public MutableLiveData<String> getInfo(){
        if (infos == null){
            infos = new MutableLiveData<>();
        }
        return infos;
    }

    public int increaseNumber(){
        number++;
        return number;
    }
}
