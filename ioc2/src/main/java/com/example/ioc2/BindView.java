package com.example.ioc2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {

    //ElementType.TYPE:表示修饰在类上
    //ElementType.METHOD：表示修饰在方法上
    //ElementType.FIELD：表示修饰在属性上
    //下面的这个方法表示@MyContentView(R.layout.activity_main)，括号里面的东西
    int value();
}
