package com.example.designpattern.stragety;

public class SubwayStragety implements Stragety {
    @Override
    public int calculatePrice(int km) {
        if (km < 6){
            return 6;
        }else if (km >= 6 && km <= 12){
            return 7;
        }else {
            return 10;
        }
    }
}
