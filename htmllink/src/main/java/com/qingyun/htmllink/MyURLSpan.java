package com.qingyun.htmllink;


import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author zhangzhenfeng
 * @date 2020/8/4 13:41
 */
public class MyURLSpan extends ClickableSpan {
    private String mUrl;

    MyURLSpan(String mUrl){
        this.mUrl = mUrl;
    }
    @Override
    public void onClick(@NonNull View widget) {
        Log.e("zzf",mUrl);
    }
}
