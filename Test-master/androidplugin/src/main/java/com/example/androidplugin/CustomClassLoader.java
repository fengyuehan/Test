package com.example.androidplugin;

import android.util.Log;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * author : zhangzf
 * date   : 2021/3/17
 * desc   :
 */
public class CustomClassLoader extends DexClassLoader {
    public CustomClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }

    /**
     * 便利方法: 获取插件的ClassLoader, 能够加载指定的插件中的类
     */
    public static CustomClassLoader getPluginClassLoader(File plugin, String packageName){
        Log.d("zzf", "dexPath:" + plugin.getPath());
        Log.d("zzf", "optimizedDirectory:" + PluginUtils.getPluginOptDexDir(packageName).getPath());
        Log.d("zzf", "librarySearchPath:" + PluginUtils.getPluginLibDir(packageName).getPath());
        Log.d("zzf", "parent ClassLoader:" + MyApplication.getInstance().getClassLoader().getClass().getCanonicalName());
        return new CustomClassLoader(plugin.getPath()
            ,PluginUtils.getPluginOptDexDir(packageName).getPath()
                ,PluginUtils.getPluginLibDir(packageName).getPath()
                ,MyApplication.getInstance().getClassLoader()
        );
    }
}
