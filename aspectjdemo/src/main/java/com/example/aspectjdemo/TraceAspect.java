package com.example.aspectjdemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TraceAspect {
    private static final String POINTCUT_METHOD = "execution(@com.example.aspectjdemo.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.example.aspectjdemo.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        DebugLog.log(className,buildLogMessage(className,methodName,stopWatch.getTotalTime()));
        return result;
    }

    private String buildLogMessage(String className,String methodName, long totalTime) {
        StringBuilder message = new StringBuilder();
        message.append(className);
        message.append(" --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(totalTime);
        message.append("ms");
        message.append("]");
        return message.toString();
    }
}
