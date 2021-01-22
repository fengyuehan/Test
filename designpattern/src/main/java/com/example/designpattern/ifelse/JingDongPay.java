package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public class JingDongPay implements IPay {
    @Override
    public void pay() {
        System.out.println("===发起京东支付===");
    }
}
