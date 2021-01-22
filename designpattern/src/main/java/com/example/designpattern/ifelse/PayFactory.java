package com.example.designpattern.ifelse;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * author : zhangzf
 * date   : 2021/1/20
 * desc   :
 */
public class PayFactory {
    static Map<String, IPay> operationMap = new HashMap<>();
    static {
        operationMap.put("ali", new AliaPay());
        operationMap.put("weixin", new WeixinPay());
        operationMap.put("jingdong", new JingDongPay());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static IPay getOperation(String operation) {
        return operationMap.get(operation);
    }
}
