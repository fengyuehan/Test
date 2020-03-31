package com.example.livedatabus.define;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;


public class BusMutableLiveData<T> extends MutableLiveData<T> {
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
        try {
            hook((Observer<T>) observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射技术  使observer.mLastVersion = mVersion
     *
     * @param observer ob
     */
    private void hook(Observer<T> observer) throws Exception {
        //根据源码 如果使observer.mLastVersion = mVersion; 就不会走 回调OnChange方法了，所以就算注册
        //也不会收到消息
        //首先获取liveData的class
        Class<LiveData> classLiveData = LiveData.class;
        //通过反射获取该类里mObserver属性对象
        Field fieldObservers = classLiveData.getDeclaredField("mObservers");
        //设置属性可以被访问
        fieldObservers.setAccessible(true);
        //获取的对象是this里这个对象值，他的值是一个map集合
        Object objectObservers = fieldObservers.get(this);
        //获取map对象的类型
        Class<?> classObservers = objectObservers.getClass();
        //获取map对象中所有的get方法
        Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
        //设置get方法可以被访问
        methodGet.setAccessible(true);
        //执行该get方法，传入objectObservers对象，然后传入observer作为key的值
        Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
        //定义一个空的object对象
        Object objectWrapper = null;
        //判断objectWrapperEntry是否为Map.Entry类型
        if (objectWrapperEntry instanceof Map.Entry) {
            objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
        }
        if (objectWrapper == null) {
            throw new NullPointerException("Wrapper can not be null!");
        }

        //如果不是空 就得到该object的父类
        Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
        //通过他的父类的class对象，获取mLastVersion字段
        Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
        fieldLastVersion.setAccessible(true);
        Field fieldVersion = classLiveData.getDeclaredField("mVersion");
        fieldVersion.setAccessible(true);
        Object objectVersion = fieldVersion.get(this);
        //把mVersion 字段的属性值设置给mLastVersion
        fieldLastVersion.set(objectWrapper, objectVersion);
    }
}
