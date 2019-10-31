package com.example.contentprovider.sortlistview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentprovider.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortContactActivity extends AppCompatActivity implements com.example.contentprovider.sortlistview.MyAdapter.OnItemListener{
    private EditText editText;
    private RecyclerView mRecyclerView;
    private com.example.contentprovider.sortlistview.MyAdapter myAdapter;
    private SideBar mSideBar;
    private CharacterParser characterParser;
    private List<SortModel> datas;
    private PinyinComparator pinyinComparator;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        initView();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = myAdapter.getPositionForSection(s.charAt(0));
                if (position != -1){
                    linearLayoutManager.scrollToPositionWithOffset(position,0);
                }
            }
        });
    }

    private void initView() {
        editText = findViewById(R.id.et_search);
        mRecyclerView = findViewById(R.id.rv);
        mSideBar = findViewById(R.id.sb);
        characterParser = new CharacterParser();
        datas = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SortContactActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SortContactActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            datas = readContacts();
        }
        linearLayoutManager =new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new com.example.contentprovider.sortlistview.MyAdapter(this,datas);
        mRecyclerView.setAdapter(myAdapter);
    }

    private void filterData(String s) {
        List<SortModel> list = new ArrayList<>();
        if (TextUtils.isEmpty(s)){
            list = datas;
        }else {
            list.clear();
            for (SortModel sortModel:datas) {
                String name = sortModel.getName();
                if (characterParser.getSelling(name).toUpperCase().startsWith(s.toUpperCase())||name.toUpperCase().indexOf(s.toUpperCase())!= -1){
                    list.add(sortModel);
                }
            }
        }
        pinyinComparator = new PinyinComparator();
        Collections.sort(list,pinyinComparator);
        myAdapter.updateData(list);
    }

    private List<SortModel> readContacts(){
        List<SortModel> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String pinyin = characterParser.getSelling(name);
                    String letter = pinyin.substring(0,1).toUpperCase();
                    SortModel sortModel = new SortModel();
                    sortModel.setName(name);
                    if (letter.matches("[A-Z]")){
                        sortModel.setSortLetters(letter);
                    }else {
                        sortModel.setSortLetters("#");
                    }
                    list.add(sortModel);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return list;
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    datas = readContacts();
                }else {
                    Toast.makeText(this, "读取联系人失败", Toast.LENGTH_SHORT).show();
                }
                default:
                    break;
        }

    }

    @Override
    public void itemListener(int position) {
        Toast.makeText(SortContactActivity.this, ""+ myAdapter.getName(position), Toast.LENGTH_SHORT).show();
    }
}
