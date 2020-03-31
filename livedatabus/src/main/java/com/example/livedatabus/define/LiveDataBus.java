package com.example.livedatabus.define;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public final class LiveDataBus {
    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder{
        private static final LiveDataBus INSTANCE = new LiveDataBus();
    }

    public static LiveDataBus getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public synchronized <T> MutableLiveData<T> with(String key,Class<T> type){
        Log.e("zzf",key);
        if (!bus.containsKey(key)){
            bus.put(key,new BusMutableLiveData<Object>());
        }

        return (MutableLiveData<T>) bus.get(key);
    }
}
