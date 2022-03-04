package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/21
 * desc   :
 */
public class PayFactory2 extends Factory {
    @Override
    public <T extends IPay> T createPay(Class<T> clz) {
        IPay iPay = null;
        try {
            iPay = (IPay) Class.forName(clz.getName()).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T) iPay;
    }
}
