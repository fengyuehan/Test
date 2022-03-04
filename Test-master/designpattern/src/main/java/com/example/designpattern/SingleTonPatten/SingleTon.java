package com.example.designpattern.SingleTonPatten;

public class SingleTon {
    private SingleTon singleTon;
    private SingleTon(){

    }
    public SingleTon getSingleTon(){
        if(singleTon == null){
            singleTon = new SingleTon();
        }
        return singleTon;
    }
}
