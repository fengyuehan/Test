package com.example.designpattern.SingleTonPatten;

public class SingleTon2 {
    private SingleTon2 singleTon = new SingleTon2();
    private SingleTon2(){

    }
    public SingleTon2 getSingleTon(){
        return singleTon;
    }

}
