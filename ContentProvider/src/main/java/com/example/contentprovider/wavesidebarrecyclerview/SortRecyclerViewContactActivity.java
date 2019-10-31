package com.example.contentprovider.wavesidebarrecyclerview;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DividerItemDecoration;
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

public class SortRecyclerViewContactActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private SortAdapter mSortAdapter;
    private WaveSideBar mWaveSideBar;
    private EditText mEditText;
    private LinearLayoutManager mLinearLayoutManager;
    private List<SortModelBean> datas;
    private TitleItemDecoration titleItemDecoration;
    private TitleItemDecorations mTitleItemDecorations;
    private PinyinComparator mComparator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);
        initView();
        //mEditText.addTextChangedListener(textWatcher);
        mWaveSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mSortAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1){
                    mLinearLayoutManager.scrollToPositionWithOffset(position,0);
                }
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv);
        mWaveSideBar = findViewById(R.id.sideBar);
        mEditText = findViewById(R.id.et_search);
        mComparator = new PinyinComparator();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        datas = filledData(getResources().getStringArray(R.array.date));
        Collections.sort(datas, mComparator);
        /*if (ContextCompat.checkSelfPermission(SortRecyclerViewContactActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SortRecyclerViewContactActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            datas = readContacts();
        }*/
        //mTitleItemDecorations = new TitleItemDecorations(this,datas);
        titleItemDecoration = new TitleItemDecoration(this,datas);
        mSortAdapter = new SortAdapter(this,datas);
        mRecyclerView.addItemDecoration(titleItemDecoration);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mSortAdapter);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //fillData(s.toString());
            filterData(s.toString());
        }



        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private List<SortModelBean> readContacts(){
        List<SortModelBean> list = new ArrayList<>();
        Cursor cursor = null;

        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor.moveToFirst()){
                do {
                    SortModelBean sortModel = new SortModelBean();
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String pinyin = PinyinUtils.getPingYin(name);
                    String letter = pinyin.substring(0,1).toUpperCase();
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
            if (cursor!= null){
                cursor.close();
            }
        }
        return list;
    }

    private void filterData(String filterStr) {
        List<SortModelBean> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            //得到values的数据
            filterDateList = filledData(getResources().getStringArray(R.array.date));
        } else {
            filterDateList.clear();
            for (SortModelBean sortModel : datas) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        datas.clear();
        datas.addAll(filterDateList);
        mSortAdapter.notifyDataSetChanged();
    }


    private List<SortModelBean> filledData(String[] date) {
        List<SortModelBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModelBean sortModel = new SortModelBean();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void fillData(String s) {
        List<SortModelBean> list = new ArrayList<>();
        if (TextUtils.isEmpty(s)){
            list = datas;
        }else {
            list.clear();
            for (SortModelBean sortModelBean : datas){
                String name = sortModelBean.getName();
                //indexOf表示该字母第一次出现的位置，返回-1表示
                if (name.indexOf(s) != -1 || PinyinUtils.getFirstSpell(name).startsWith(s)
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(s)
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(s)){
                    list.add(sortModelBean);
                }
            }
        }
        Collections.sort(list,mComparator);
        datas.clear();
        datas.addAll(list);
        mSortAdapter.updataData(datas);

    }


    //需要在用到权限的地方，每次都检查是否APP已经拥有权限
    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    datas = readContacts();
                }else {
                    Toast.makeText(this, "读取联系人失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
