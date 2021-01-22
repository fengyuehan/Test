package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public class PayStrategy {
    private IPay iPay;

    public PayStrategy(IPay iPay){
        this.iPay = iPay;
    }

    public void pay(){
        iPay.pay();
    }
}
