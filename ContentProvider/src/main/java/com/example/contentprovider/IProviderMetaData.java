package com.example.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public interface IProviderMetaData {
    public static final String  AUTHORITY  = "com.example.contentprovider";
    public static final String  DB_NAME = "book.db";
    public static final int VERSION = 1;

    public  interface BookTableMetaData extends BaseColumns{
        public static final String TABLE_NAME = "book";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static final String BOOK_ID = "_id";
        public static final String BOOK_NAME = "name";
        public static final String BOOK_PUBLISHER = "publisher";

        public static final String SORT_ORDER = "_id desc";
        public static final String CONTENT_LIST = "vnd.android.cursor.dir/vnd.bookprovider.book";
        public static final String CONTENT_ITEM = "vnd.android.cursor.item/vnd.bookprovider.book";
    }
}
