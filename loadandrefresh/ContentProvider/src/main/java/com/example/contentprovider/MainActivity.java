package com.example.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contentprovider.readContacts.ReadContactActivity;
import com.example.contentprovider.sortlistview.SortContactActivity;
import com.example.contentprovider.wavesidebarrecyclerview.SortRecyclerViewContactActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add,delete,updata,search,readContacts,contactRead,contactRead1;
    private TextView info;
    private ContentResolver mContentResolver;
    private BookContentProvider mBookContentProvider;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        updata.setOnClickListener(this);
        search.setOnClickListener(this);
        readContacts.setOnClickListener(this);
        contactRead.setOnClickListener(this);
        contactRead1.setOnClickListener(this);
    }

    private void initView() {
        add = findViewById(R.id.btn_add);
        delete = findViewById(R.id.btn_delete);
        updata = findViewById(R.id.btn_updata);
        search = findViewById(R.id.btn_search);
        info = findViewById(R.id.tv_info);
        readContacts = findViewById(R.id.btn_read_contacts);
        contactRead = findViewById(R.id.btn_read);
        contactRead1 = findViewById(R.id.btn_read_recycler_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_read_recycler_view:
                startActivity(new Intent(MainActivity.this, SortRecyclerViewContactActivity.class));
                break;
            case R.id.btn_read:
                startActivity(new Intent(MainActivity.this, SortContactActivity.class));
                break;
            case R.id.btn_read_contacts:
                startActivity(new Intent(MainActivity.this, ReadContactActivity.class));
                break;
            case R.id.btn_add:
                mContentResolver = getContentResolver();
                String[] bookNames = new String[]{"Chinese", "Math", "English", "Sports"};
                String[] bookPublishers = new String[]{"XinHua", "GongXin", "DianZi", "YouDian"};
                for (int i = 0;i < bookNames.length;i++){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(IProviderMetaData.BookTableMetaData.BOOK_NAME,bookNames[i]);
                    contentValues.put(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER,bookPublishers[i]);
                    mContentResolver.insert(IProviderMetaData.BookTableMetaData.CONTENT_URI,contentValues);
                }
                break;
            case R.id.btn_delete:
                String bookId = "1";
                if (!"".equals(bookId)){
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put(IProviderMetaData.BookTableMetaData.BOOK_ID,bookId);
                    mContentResolver.delete(Uri.withAppendedPath(IProviderMetaData.BookTableMetaData.CONTENT_URI,bookId),"_id = ?",new String[]{bookId});
                }else {
                    mContentResolver.delete(IProviderMetaData.BookTableMetaData.CONTENT_URI,null,null);
                }
                break;
            case R.id.btn_search:
                Cursor cursor = mContentResolver.query(IProviderMetaData.BookTableMetaData.CONTENT_URI,null,null,null,null);
                String text = "";
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String bookIdText = cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_ID));
                        String bookName = cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_NAME));
                        String bookPublisher = cursor.getString(cursor.getColumnIndex(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER));
                        text += "_id = " + bookIdText + "，name = " + bookName + ", publisher = " + bookPublisher + "\n";
                    }
                    info.setText(text);
                }
                cursor.close();
                break;
            case R.id.btn_updata:
                String bookId1 = "2";
                String bookName2 = "Art";
                String publisher = "haha";
                ContentValues contentValues = new ContentValues();
                contentValues.put(IProviderMetaData.BookTableMetaData.BOOK_ID,bookId1);
                contentValues.put(IProviderMetaData.BookTableMetaData.BOOK_NAME,bookName2);
                contentValues.put(IProviderMetaData.BookTableMetaData.BOOK_PUBLISHER,publisher);
                if ("".equals(bookId1)){
                    mContentResolver.update(IProviderMetaData.BookTableMetaData.CONTENT_URI,contentValues,null,null);
                }else {
                    mContentResolver.update(Uri.withAppendedPath(IProviderMetaData.BookTableMetaData.CONTENT_URI,bookId1),contentValues,null,null);
                }
                break;
                default:
                    Log.e("MainActivity", "default");
                    break;
        }
    }
}
