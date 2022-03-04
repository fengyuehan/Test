package com.example.designpattern.SingleTonPatten;

/**
 * author : zhangzf
 * date   : 2021/1/15
 * desc   :
 */
public class SingleTon5 {

    private SingleTon5(){

    }

    /**
     * 采用其他的方式，反序列化会生成新的对象，解决的方法是重写readResolve方法，并把这个实例在这个方法内返回
     */
    enum SingletonEnum{
        SINGLETON_ENUM;
        private SingleTon5 singleTon5;

        SingletonEnum(){
            singleTon5 = new SingleTon5();
        }

        private SingleTon5 getSingleTon(){
            return singleTon5;
        }
    }

    public static SingleTon5 getInstance(){
        return SingletonEnum.SINGLETON_ENUM.getSingleTon();
    }
}
