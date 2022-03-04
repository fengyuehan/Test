package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public class PayService {
    private AliaPay aliaPay;
    private WeixinPay weixinPay;
    private JingDongPay jingDongPay;

    public void toPay(String code) {
        if ("alia".equals(code)) {
            aliaPay.pay();
        } else if ("weixin".equals(code)) {
            weixinPay.pay();
        } else if ("jingdong".equals(code)) {
            jingDongPay.pay();
        } else {
            System.out.println("找不到支付方式");
        }
    }
}
