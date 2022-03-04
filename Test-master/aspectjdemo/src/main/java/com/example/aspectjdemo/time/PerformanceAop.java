package com.example.aspectjdemo.time;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PerformanceAop {
    //定义切点
    @Pointcut("execution(@com.example.aspectjdemo.time.TimeTest * *(..))")
    public void methodAnnotated(){

    }


    /**
     * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
     * String name = signature.getName(); // 方法名：test
     * Method method = signature.getMethod(); // 方法：public void com.lqr.androidaopdemo.MainActivity.test(android.view.View)
     * Class returnType = signature.getReturnType(); // 返回值类型：void
     * Class declaringType = signature.getDeclaringType(); // 方法所在类名：MainActivity
     * String[] parameterNames = signature.getParameterNames(); // 参数名：view
     * Class[] parameterTypes = signature.getParameterTypes(); // 参数类型：View
     * // 通过Method对象得到切点上的注解
     * TestAnnoTrace annotation = method.getAnnotation(TestAnnoTrace.class);
     * String value = annotation.value();
     * int type = annotation.type();
     */

    /**
     * Around注解里面是String类型的
     */
    @Around("methodAnnotated()")
    public void getTime(ProceedingJoinPoint joinPoint){
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        try {
            joinPoint.proceed();
        }catch (Throwable e){
            e.printStackTrace();
        }
        Log.e("zzf",methodName + "方法耗时" + (System.currentTimeMillis() - startTime));
    }
}
