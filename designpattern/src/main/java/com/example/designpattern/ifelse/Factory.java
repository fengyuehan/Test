package com.example.designpattern.ifelse;

/**
 * author : zhangzf
 * date   : 2021/1/21
 * desc   :
 */
public abstract class Factory {
    public abstract <T extends IPay> T createPay(Class<T> clz);
}
