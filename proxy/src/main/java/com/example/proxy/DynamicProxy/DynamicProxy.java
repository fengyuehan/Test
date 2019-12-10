package com.example.proxy.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName DynamicProxy
 * @Description TODO
 * @Author user
 * @Date 2019/12/10
 * @Version 1.0
 */
public class DynamicProxy {
    public static IPersonService getProxy(){
        final IPersonService iPersonService = new PersonService();
        IPersonService proxy = (IPersonService) Proxy.newProxyInstance(IPersonService.class.getClassLoader(), new Class<?>[]{IPersonService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object obj = method.invoke(iPersonService,args);
                return obj;
            }
        });
        return proxy;
    }
}
