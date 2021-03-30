package com.example.mat;

import android.content.Context;

/**
 * author : zhangzf
 * date   : 2021/3/27
 * desc   :
 */
public class CommonUtils {
    private static CommonUtils instance;
    private Context context;
    private CommonUtils(Context context){
        this.context = context;
    }

    public static CommonUtils getInstance(Context context){
        if (instance == null){
            instance = new CommonUtils(context);
        }
        return instance;
    }
}
