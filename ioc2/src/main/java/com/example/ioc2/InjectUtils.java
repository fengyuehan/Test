package com.example.ioc2;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {
    public static void inject(Object object){
        injectLayout(object);
        inijectView(object);
        injectClick(object);
    }

    private static void injectClick(Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods){
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation :annotations){
                Class<?> annotationClass = annotation.annotationType();
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                if (eventBase == null){
                    continue;
                }

                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethed = eventBase.callbackMethod();
                Method valueMethed = null;
                try {
                    valueMethed = annotationClass.getDeclaredMethod("value");
                    int[] viewId = (int[]) valueMethed.invoke(annotation);
                    for (int id : viewId){
                        Method finViewById = clazz.getMethod("finViewById",int.class);
                        View view = (View) finViewById.invoke(object,id);
                        if (view == null){
                            continue;
                        }
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(object,method);
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class[]{listenerType},listenerInvocationHandler);
                        Method onClickMethod = view.getClass().getMethod(listenerSetter,listenerType);
                        onClickMethod.invoke(view,proxy);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void inijectView(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null){
                int viewId = bindView.value();
                try {
                    Method method = clazz.getMethod("findViewById",int.class);
                    View view = (View) method.invoke(object,viewId);
                    //如果获取的字段是私有的，不管是读还是写，都要先 field.setAccessible(true);才可以。否则会报：IllegalAccessException。
                    field.setAccessible(true);
                    field.set(object,view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectLayout(Object object) {
        Class<?> clazz = object.getClass();
        MyContentView myContentView = clazz.getAnnotation(MyContentView.class);
        if (myContentView != null) {
            int layoutId = myContentView.value();
            try {
                Method method = clazz.getMethod("setContentView",int.class);
                method.invoke(object,layoutId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
