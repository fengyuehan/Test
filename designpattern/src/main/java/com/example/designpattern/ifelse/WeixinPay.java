package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public class WeixinPay implements IPay {
    @Override
    public void pay() {
        System.out.println("===发起微信支付===");
    }
}
