package com.example.aspectjdemo;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

@Aspect
public class SingleClickAspect {
    private static final String TAG = "SingleClickAspect";
    public static final int MIN_CLICK_DELAY_TIME = 600;
    static int TIME_TAG = R.id.click_time;

    //定义切点
    @Pointcut("execution(@com.example.aspectjdemo.SingleClick * *(..))")
    public void methodAnnotated(){

    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{
        View view = null;
        for (Object obj : joinPoint.getArgs()){
            if (obj instanceof View){
                view = (View) obj;
            }
        }
        if (view != null){
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = (tag != null)? (long) tag :0;
            Log.d(TAG, "lastClickTime:" + lastClickTime);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                Log.d(TAG, "currentTime:" + currentTime);
                joinPoint.proceed();//执行原方法
            }
        }
    }
}
