package com.example.dell.myapplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceHelper {
    public static <T> List<T> getServices(Class<T> interfaceClass){
        ServiceLoader loader = ServiceLoader.load(interfaceClass);
        Iterator iterator = loader.iterator();
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()){
            T t = (T) iterator.next();
            if (t != null){
                list.add(t);
            }
        }
        return list;
    }

    public static <T> T getService(Class<T> interfaceClass){
        ServiceLoader loader = ServiceLoader.load(interfaceClass);
        Iterator<T> iterator = loader.iterator();
        if (iterator.hasNext()){
            return (T) iterator.next();
        }else {
            return null;
        }
    }
}
