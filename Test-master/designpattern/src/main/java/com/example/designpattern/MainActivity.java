package com.example.designpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.designpattern.DynamicProxyPattern.DynamicProxy;
import com.example.designpattern.DynamicProxyPattern.IRoom;
import com.example.designpattern.DynamicProxyPattern.XiaoMing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IRoom xiaoMing = new XiaoMing();
        InvocationHandler invocationHandler = new DynamicProxy(xiaoMing);
        ClassLoader classLoader = xiaoMing.getClass().getClassLoader();
        IRoom proxy = (IRoom) Proxy.newProxyInstance(classLoader,new Class[]{IRoom.class},invocationHandler);
        proxy.seekRoom();
        proxy.watchRoom();
        proxy.room();
        proxy.finish();
    }
}
