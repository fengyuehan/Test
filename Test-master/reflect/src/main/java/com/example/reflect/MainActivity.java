package com.example.reflect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "zzf";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void test() {
        try {
            Class appClass = Class.forName("com.example.reflect.Apple");
            Log.e(TAG,"**********getDeclaredFields**********");
            Field[] fields1 = appClass.getDeclaredFields();
            for (Field field:fields1){
                Log.e(TAG,field.toString());
            }
            Log.e(TAG,"**********getDeclaredFields**********");
            Log.e(TAG,"**********getFields**********");
            Field[] fields = appClass.getFields();
            for (Field field: fields){
                Log.e(TAG,field.toString());
            }
            Log.e(TAG,"**********getFields**********");
            Log.e(TAG,"**********getDeclaredMethods**********");
            Method[] methods = appClass.getDeclaredMethods();
            for (Method method: methods){
                Log.e(TAG,method.toString());
            }
            Log.e(TAG,"**********getDeclaredMethods**********");
            Log.e(TAG,"**********getMethods**********");
            Method[] methods1=appClass.getMethods();
            for(Method method:methods1){
                Log.e(TAG,method.toString());
            }
            Log.e(TAG,"**********getMethods**********");
            Apple apple = (Apple) appClass.newInstance();
            Constructor constructor = appClass.getConstructor(String.class,int.class,int.class);
            Apple apple1 = (Apple) constructor.newInstance("红色",10,5);
            Method method = appClass.getDeclaredMethod("toString");
            String str = (String) method.invoke(apple1);
            Log.e(TAG,str);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
