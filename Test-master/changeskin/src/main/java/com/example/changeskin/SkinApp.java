package com.example.changeskin;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.changeskin.plugin.CustomSDCardLoader;
import com.example.changeskin.zip.ZipSDCardLoader;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

public class SkinApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 矢量图的兼容
         */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SkinCompatManager.withoutActivity(this)
                /**
                 * 使用不同的策略
                 */
                .addStrategy(new ZipSDCardLoader())
                .addStrategy(new CustomSDCardLoader())
                /**
                 * 基础控件换肤
                 */
                .addInflater(new SkinAppCompatViewInflater())
                /**
                 * material design换肤
                 */
                .addInflater(new SkinMaterialViewInflater())
                /**
                 * ConstraintLayout换肤
                 */
                .addInflater(new SkinConstraintViewInflater())
                /**
                 * CardView换肤
                 */
                .addInflater(new SkinCardViewInflater())
                /**
                 * true:关闭状态栏换肤
                 * flase:开启状态栏换肤
                 */
                //.setSkinStatusBarColorEnable(true)
                /**
                 * 关闭windowBackground换肤
                 */
                //.setSkinWindowBackgroundEnable(false)
                /**
                 * true: 默认所有的Activity都换肤;
                 * false: 只有实现SkinCompatSupportable接口的Activity换肤
                 */
                //.setSkinAllActivityEnable(false)
                .loadSkin();
    }
}
