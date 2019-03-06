package com.example.shoppingcart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.shoppingcart.greendao.DaoMaster;
import com.example.shoppingcart.greendao.DaoSession;
import com.example.shoppingcart.greendao.ShopCarDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import java.util.List;

public class GreenDaoHelper extends DaoMaster.OpenHelper{
    private  Context context;
    private static final String DATABASE_NAME = "shopcar_db";
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

    /*@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, ShopCarDao.class);
    }*/

    public static List<ShopCar> queryAll(){
        List<ShopCar> list = GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().queryBuilder().list();
        return list;
    }

    public static ShopCar querySingle(){
        List<ShopCar> list = GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().queryBuilder().list();
        ShopCar shopCar = null;
        for (int i = 0 ; i < list.size() ; i++) {
            shopCar = list.get(i);
        }
        return shopCar;
    }

    public static void deleteSingle(ShopCar shopCar){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().delete(shopCar);
    }
    public static void deleteAll(){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().deleteAll();
    }

    public static void delete(List<ShopCar> list){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().deleteInTx(list);
    }

    public static void insertSingle(ShopCar shopCar){
        GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().insertOrReplace(shopCar);
    }

   /* public static List<ShopCar> query(String name){
        List<ShopCar> list = GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().queryBuilder().where(ShopCar.Properties.Name.eq(name)).build().list();
        return list;
    }*/

    public static int count(){
        return (int) GreenDaoHelper.getDaoSession(MyApplication.getContext()).getShopCarDao().queryBuilder().count();
    }
}
