package com.example.customview.downMenu;

import android.content.Context;
import android.util.TypedValue;

/**
 * author : zhangzf
 * date   : 2021/5/25
 * desc   :
 */
public class DisplayUtils {
    public static int dp2px(Context context, int dpValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources
                ().getDisplayMetrics());
    }
    public static int sp2px(Context context, int spValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context
                .getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
