package com.example.lettersidebar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class GreenDaoHelper extends DaoMaster.OpenHelper{
    private static Context mContext;
    private static final String DATABASE_NAME = "contact_db";
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public GreenDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        mContext = context;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (mDaoMaster == null) {
            DaoMaster.OpenHelper helper = new GreenDaoHelper(context, DATABASE_NAME, null);
            if (helper != null){
                mDaoMaster = new DaoMaster(helper.getWritableDatabase());
            }
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

    public static List<ConstactBean> queryAll(){
        List<ConstactBean> list = GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().queryBuilder().list();
        return list;
    }

    public static void deleteSingle(ConstactBean constactBean){
        GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().delete(constactBean);
    }
    public static void deleteAll(){
        GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().deleteAll();
    }

    public static void delete(List<ConstactBean> list){
        GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().deleteInTx(list);
    }

    public static void insertSingle(ConstactBean user){
        GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().insert(user);
    }

    public static List<ConstactBean> query(String name){
        List<ConstactBean> list = GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().queryBuilder().where(ConstactBeanDao.Properties.Memo_py.eq(name)).build().list();
        return list;
    }

    public static int count(){
        return (int) GreenDaoHelper.getDaoSession(AppApplication.getContext()).getConstactBeanDao().queryBuilder().count();
    }
}
