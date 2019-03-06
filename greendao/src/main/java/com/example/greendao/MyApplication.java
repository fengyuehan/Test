package com.example.greendao;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.greendao.greendao.DaoMaster;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private static Context mContext;
    private SQLiteOpenHelper helper;
    private DaoMaster daoMaster;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = MyApplication.this;
        myApplication = this;
        MigrationHelper.DEBUG = true;
    }

    public static Context getContext() {
        return mContext;
    }
}
