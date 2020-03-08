package com.example.innerclass;

public class ClassOuter {
    private int noStaticInt = 1;
    private static int staticInt = 2;

    public void fun(){
        System.out.print("外部方法");
    }

    public class InnerClass{
        //非静态内部类不能有静态变量
        //static int num = 1;
        public void fun(){
            System.out.println("内部方法");
            System.out.println(noStaticInt);
            System.out.println(staticInt);
        }
    }

    public static class StaticInnerClass{
        static int num = 1;
        public void fun(){
            System.out.println("静态内部类方法");
            System.out.println(staticInt);
            System.out.println(num);
        }
    }
}
