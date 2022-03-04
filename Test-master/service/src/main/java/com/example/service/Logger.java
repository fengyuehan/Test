package com.example.service;

import android.util.Log;

import androidx.annotation.NonNull;


public class Logger {
    private static boolean showLog = true;
    /**
     * Log日志的 Tag，默认 Logger
     */
    private static String TAG = "Logger";
    /**
     * 打印全类名类型  0：全类名； 1：简单类名； -1：不打印 默认简单类名
     */
    private static int fullClassNameType = 1;

    public static void setShowLog(boolean showLog) {
        Logger.showLog = showLog;
    }

    /**
     * 打印全类名类型
     *
     * @param fullClassNameType 0：全类名； 1：简单类名； -1：不打印 默认简单类名
     */
    public static void fullClassName(int fullClassNameType) {
        Logger.fullClassNameType = fullClassNameType;
    }

    /**
     * 设置Log的Tag，默认 "Logger"
     *
     * @param tag
     */
    public static void setAppTAG(@NonNull String tag) {
        Logger.TAG = tag;
    }

    public static void i(String msg) {
        if (showLog) {
            Log.i(TAG, getLogTitle() + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (showLog) {
            Log.i(tag, getLogTitle() + msg);
        }
    }


    public static void v(@NonNull String msg) {
        if (showLog) {
            Log.v(TAG, getLogTitle() + msg);
        }
    }

    public static void d(@NonNull String msg) {
        if (showLog) {
            Log.d(TAG, getLogTitle() + msg);
        }
    }

    public static void w(@NonNull String msg) {
        if (showLog) {
            Log.w(TAG, getLogTitle() + msg);
        }
    }

    public static void e(@NonNull String msg) {
        if (showLog) {
            Log.e(TAG, getLogTitle() + msg);
        }
    }

    /**
     * 返回类名(根据是否设置了打印全类名返回响应的值)，方法名和日子打印所在行数
     *
     * @return (全)类名.方法名(所在行数):
     */
    @NonNull
    private static String getLogTitle() {
        if (fullClassNameType <= 0) {
            return "";
        }

        StackTraceElement elm = Thread.currentThread().getStackTrace()[4];
        String className = elm.getClassName();
        if (fullClassNameType == 1) {
            int dot = className.lastIndexOf('.');
            if (dot != -1) {
                className = className.substring(dot + 1);
            }
        }
        return className + "." + elm.getMethodName() + "(" + elm.getLineNumber() + ")" + ": ";
    }
}
