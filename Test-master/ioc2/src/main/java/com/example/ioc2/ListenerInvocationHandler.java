package com.example.ioc2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ListenerInvocationHandler implements InvocationHandler {
    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity,Method activityMethod){
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return activityMethod.invoke(args,activity);
    }
}
