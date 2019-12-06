package com.example.linkedhashmapactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinkedHashMap<Integer,Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*map = new LinkedHashMap<>(2);*/
        map = new LinkedHashMap<Integer, Integer>(6,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
                return map.size() > 6;
            }
        };
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);
        map.put(5,5);
        map.put(6,6);
        for (Map.Entry<Integer,Integer> a :map.entrySet()){
            Log.e("TAG", "key->" + a.getKey() + "");
            Log.e("TAG", "value->" + a.getKey() + "");
        }
        map.get(1);
        map.get(2);
        for (Map.Entry<Integer,Integer> a :map.entrySet()){
            Log.e("TAG", "key1->" + a.getKey() + "");
            Log.e("TAG", "value1->" + a.getKey() + "");
        }
    }
}
