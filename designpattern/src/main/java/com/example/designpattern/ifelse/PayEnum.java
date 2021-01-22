package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public enum PayEnum {
    ALIPAY("ali",new AliaPay()),
    WEIXINPAY("weixin",new WeixinPay()),
    JINGDONGPAY("jingdong",new JingDongPay());

    public String name;

    public IPay channel;

    PayEnum(String name, IPay channel) {
        this.name = name;
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public IPay getChannel() {
        return channel;
    }

    public static PayEnum match(String name){
        PayEnum[] values = PayEnum.values();
        for (PayEnum value : values) {
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }
}
