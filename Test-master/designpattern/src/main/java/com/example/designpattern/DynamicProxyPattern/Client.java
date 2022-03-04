package com.example.designpattern.DynamicProxyPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args){
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
