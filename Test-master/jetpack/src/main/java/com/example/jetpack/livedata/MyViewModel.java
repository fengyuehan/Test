package com.example.jetpack.livedata;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> infos;
    private MutableLiveData<Integer> liveData = new MutableLiveData<>();
    private int number;
    //如果想要根据某个值 切换观察不同LiveData数据，则可以使用Transformations.switchMap()方法。
    MutableLiveData<String> liveData3 = new MutableLiveData<>();
    MutableLiveData<String> liveData4 = new MutableLiveData<>();
    MutableLiveData<Boolean> liveDataSwitch = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLiveDataSwitch() {
        return liveDataSwitch;
    }


    public LiveData<String> liveDataSwitch1 = Transformations.switchMap(liveDataSwitch, input -> {
        if (input){
            return liveData3;
        }
        return liveData4;
    });

    public MutableLiveData<String> getLiveData(){
        LiveData<String> stringLiveData = Transformations.map(liveData, input -> {
            String s = input + " + Transformations.map";
            Log.i("zzf", "apply: " + s);
            return s;
        });
        return (MutableLiveData<String>) stringLiveData;
    }

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
