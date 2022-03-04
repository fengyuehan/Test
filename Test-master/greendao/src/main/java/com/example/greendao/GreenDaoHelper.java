package com.example.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidbase.utils.ToastUtils;
import com.example.greendao.greendao.DaoMaster;
import com.example.greendao.greendao.DaoSession;
import com.example.greendao.greendao.UserDao;

import java.util.List;

public class GreenDaoHelper extends DaoMaster.OpenHelper{
    private  Context context;
    private static final String DATABASE_NAME = "user_db";
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public GreenDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        this.context = context;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (mDaoMaster == null) {
            DaoMaster.OpenHelper helper = new GreenDaoHelper(context, DATABASE_NAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster(context);
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public static List<User> queryAll(){
        List<User> list = GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().queryBuilder().list();
        return list;
    }

    public static void deleteSingle(User user){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().delete(user);
    }
    public static void deleteAll(){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().deleteAll();
    }

    public static void delete(List<User> list){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().deleteInTx(list);
    }

    public static void insertSingle(User user){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().insert(user);
    }

    public static List<User> query(String name){
        List<User> list = GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().queryBuilder().where(UserDao.Properties.Name.eq(name)).build().list();
        return list;
    }

    public static int count(){
        return (int) GreenDaoHelper.getDaoSession(MyApplication.getContext()).getUserDao().queryBuilder().count();
    }
}
