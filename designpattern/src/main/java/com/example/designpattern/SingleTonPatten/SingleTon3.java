package com.example.designpattern.SingleTonPatten;

public class SingleTon3 {
    private SingleTon3 singleTon3;
    private SingleTon3(){

    }
    public SingleTon3 getSingleTon3(){
        if (singleTon3 == null){
            synchronized (SingleTon3.class){
                if(singleTon3 == null){
                    singleTon3 = new SingleTon3();
                }
            }
        }
        return singleTon3;
    }
}
