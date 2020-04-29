package com.example.dayandnightchange;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ThemeManager {
    private static ThemeMode mThemeMode = ThemeMode.DAY;
    private static List<OnThemeChangeListener> listeners = new LinkedList<>();
    // 夜间资源的缓存，key : 资源类型, 值<key:资源名称, value:int值>
    private static HashMap<String, HashMap<String, Integer>> sCachedNightResrouces = new HashMap<>();
    // 夜间模式资源的后缀，比如日件模式资源名为：R.color.activity_bg, 那么夜间模式就为 ：R.color.activity_bg_night
    private static final String RESOURCE_SUFFIX = "_night";

    public enum ThemeMode{
        DAY,
        NIGHT
    }

    public static void setThemeMode(ThemeMode themeMode){
        if (themeMode != null){
            mThemeMode = themeMode;
            if (listeners.size() > 0){
                for (OnThemeChangeListener listener:listeners){
                    listener.onThemeChanged();
                }
            }
        }
    }

    public static ThemeMode getmThemeMode(){
        return mThemeMode;
    }

    public static int getCurrentThemeRes(Context context,int dayResId){
        if (getmThemeMode() == ThemeMode.DAY){
            return dayResId;
        }
        //R.color.colorPrimary ，那么资源名称就是 colorPrimary ，资源类型就是 color。
        //资源名
        String entryName = context.getResources().getResourceEntryName(dayResId);
        //资源类型
        String typeName = context.getResources().getResourceTypeName(dayResId);
        HashMap<String,Integer> cachedRes = sCachedNightResrouces.get(typeName);
        if (cachedRes == null){
            cachedRes = new HashMap<>();
        }
        Integer resId = cachedRes.get(entryName + RESOURCE_SUFFIX);
        if (resId != null && resId != 0){
            return resId;
        }else {
            try {
                int nightResId = context.getResources().getIdentifier(entryName + RESOURCE_SUFFIX,typeName,context.getPackageName());
                cachedRes.put(entryName +RESOURCE_SUFFIX,nightResId);
                sCachedNightResrouces.put(typeName,cachedRes);
                return nightResId;
            }catch (Resources.NotFoundException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void registerThemeChangeListener(OnThemeChangeListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public static void unregisterThemeChangeListener(OnThemeChangeListener listener){
        if (listeners.contains(listeners)){
            listeners.remove(listener);
        }
    }
}

