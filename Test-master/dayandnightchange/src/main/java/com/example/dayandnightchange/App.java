package com.example.dayandnightchange;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {
    /**
     * MODE_NIGHT_NO： 使用亮色(light)主题，不使用夜间模式；
     * MODE_NIGHT_YES：使用暗色(dark)主题，使用夜间模式；
     * MODE_NIGHT_AUTO：根据当前时间自动切换 亮色(light)/暗色(dark)主题；
     * MODE_NIGHT_FOLLOW_SYSTEM(默认选项)：设置为跟随系统，通常为 MODE_NIGHT_NO
     */
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);

    }
}
