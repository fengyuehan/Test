package com.example.designpattern.SingleTonPatten;

public class SingleTon4 {
    private SingleTon4 singleTon4;
    private SingleTon4(){

    }
    public static class Single0{
        private static final SingleTon4 SINGLETON = new SingleTon4();
    }

    public SingleTon4 getSingleTon4(){
        return Single0.SINGLETON;
    }
}

