package com.mj.demo.statusbarplus;

import android.content.Context;

public class ChangePxFromDp {
    /**
     * 适配手机分辨率
     *
     * @param context 上下文
     * @param dpValue 参数值
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
