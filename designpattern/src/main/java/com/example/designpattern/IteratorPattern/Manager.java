package com.example.designpattern.IteratorPattern;

import android.util.Log;

/**
 * author : zhangzf
 * date   : 2021/2/3
 * desc   :
 */
public class Manager extends Leader {
    @Override
    protected void handle(int money) {
        Log.e("zzf","经理批复报销" + money);
    }

    @Override
    protected int limit() {
        return 10000;
    }
}
