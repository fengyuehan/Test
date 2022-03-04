package com.example.designpattern.stragety;

public class BusStragety implements Stragety {
    @Override
    public int calculatePrice(int km) {
        int extrationTotal = km -10;
        int extraFactor = extrationTotal / 5;
        int fraction = extraFactor % 5;
        int price = 10 + extraFactor * 2;
        return fraction > 0 ? ++price:price;
    }
}
