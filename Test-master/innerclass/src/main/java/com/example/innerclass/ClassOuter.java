package com.example.innerclass;

public class ClassOuter {
    private int noStaticInt = 1;
    private static int staticInt = 2;

    private static int fun1(){
        return staticInt;
    }

    public int fun2(){
        System.out.print("外部方法");
        return noStaticInt;
    }

    public class InnerClass{
        //非静态内部类不能有静态变量
        //static int num = 1;
        //非静态内部类不能有静态方法
        /*public static void fun1(){

        }*/
        public void fun(){
            System.out.println("内部方法");
            System.out.println(noStaticInt);
            System.out.println(staticInt);
            System.out.println(fun1());
            System.out.println(fun2());
        }
    }

    public static class StaticInnerClass{
        static int num = 1;
        public void fun(){
            System.out.println("静态内部类方法");
            System.out.println(staticInt);
            System.out.println(num);
            //静态内部类不能访问外部类的非静态成员变量，只能访问静态成员变量
            //System.out.println(noStaticInt);
            //静态内部类不能访问外部类非静态方法
            //System.out.println(fun2());
        }
    }
}
