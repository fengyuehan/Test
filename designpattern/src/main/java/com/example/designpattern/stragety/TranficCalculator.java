package com.example.designpattern.stragety;

public class TranficCalculator {
    Stragety stragety;
    public TranficCalculator(Stragety stragety){
        this.stragety = stragety;
    }

    public int calculatePrice(int km){
        return stragety.calculatePrice(km);
    }
}

