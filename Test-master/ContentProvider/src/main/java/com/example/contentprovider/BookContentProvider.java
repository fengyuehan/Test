package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookContentProvider extends ContentProvider {

    private static final String TAG = "BookContentProvider";
    private static UriMatcher uriMatcher = null;
    private static final int BOOKS = 1;
    private static final int BOOK = 2;

    private ContentProviderDBHelper mContentProviderDBHelper;
    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IProviderMetaData.AUTHORITY,IProviderMetaData.BookTableMetaData.TABLE_NAME,BOOKS);
        uriMatcher.addURI(IProviderMetaData.AUTHORITY,IProviderMetaData.BookTableMetaData.TABLE_NAME + "/#",BOOK);
    }
    @Override
    public boolean onCreate() {
        mContentProviderDBHelper = new ContentProviderDBHelper(getContext());
        db = mContentProviderDBHelper.getReadableDatabase();
        return true;
    }

    //uri:表名
    //projection:返回的列（数组形式）
    //selection:查询条件
    //selectionArgs:替换掉第三个参数中的占位符"?"
    //sortOrder:排序
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){
            case BOOKS:
                return db.query(IProviderMetaData.BookTableMetaData.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && "".equals(selection)){
                    where = selection + " and " + where;
                }
                return db.query(IProviderMetaData.BookTableMetaData.TABLE_NAME,projection,where,selectionArgs,null,null,sortOrder);
                default:
                    throw new IllegalArgumentException(" this is a unknow uri" + uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOKS:
                return IProviderMetaData.BookTableMetaData.CONTENT_LIST;
            case BOOK:
                return IProviderMetaData.BookTableMetaData.CONTENT_ITEM;
                default:
                    throw new IllegalArgumentException(" this is a unknow uri" + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)){
            case BOOKS:
                //当磁盘已经满了,getWritableDatabase会抛异常，而getReadableDatabase不会报错，它此时不会返回读写数据库的对象，
                // 而是仅仅返回一个读数据库的对象。
                long rowId = db.insert(IProviderMetaData.BookTableMetaData.TABLE_NAME,IProviderMetaData.BookTableMetaData.BOOK_ID,values);
                Uri insertUri = Uri.withAppendedPath(uri,"/" + rowId);
                getContext().getContentResolver().notifyChange(uri,null);
                return insertUri;
            case BOOK:

                default:
                    throw new IllegalArgumentException("This is a unKnow Uri"
                            + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case BOOKS:
                db.delete(IProviderMetaData.BookTableMetaData.TABLE_NAME,selection,selectionArgs);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && "".equals(selection)){
                    where = selection + "and " + where;
                }
                return db.delete(IProviderMetaData.BookTableMetaData.TABLE_NAME,where,selectionArgs);
                default:
                    throw new IllegalArgumentException("This is a unKnow Uri"
                            + uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return db.update(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        values, null, null);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.update(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        values, where, selectionArgs);
            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }
}
