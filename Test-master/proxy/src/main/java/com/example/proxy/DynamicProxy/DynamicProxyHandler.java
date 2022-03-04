package com.example.proxy.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName DynamicProxyHandler
 * @Description TODO
 * @Author user
 * @Date 2019/12/10
 * @Version 1.0
 */
public class DynamicProxyHandler implements InvocationHandler {
    IPersonService iPersonService;

    public DynamicProxyHandler(IPersonService iPersonService) {
        this.iPersonService = iPersonService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = method.invoke(iPersonService,args);
        return obj;
    }

}
