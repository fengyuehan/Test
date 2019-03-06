package com.example.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.greendao.greendao.DaoMaster;
import com.example.greendao.greendao.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

public class SQLiteOpenHelper extends DaoMaster.OpenHelper {
    public SQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //如果需要其他的，则再添加xxxDao.class。
        MigrationHelper.migrate(db, UserDao.class);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {

            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {

            }
        });
    }
}
