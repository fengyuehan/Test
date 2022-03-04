package com.example.greendao;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText search;
    private Button btn_search;
    private TagFlowLayout mTagFlowLayout;
    private RecyclerView mRecyclerView;
    private Button mButton;
    private GreenDaoAdapter mGreenDaoAdapter;
    Long id = Long.valueOf(0);
    private List<User> userList = new ArrayList<>();
    private String[] data = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*GreenDaoHelper.deleteAll();
        User user = new User();
        user.setId(id);
        user.setName("tian");
        userList.add(user);
        GreenDaoHelper.insertSingle(user);*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mGreenDaoAdapter = new GreenDaoAdapter(this);
        mRecyclerView.setAdapter(mGreenDaoAdapter);
        Log.e("ZZF1", "-----------" + userList.size());
        Collections.reverse(userList);
        mGreenDaoAdapter.resetData(userList);
        mGreenDaoAdapter.setOnItemClickListener(new GreenDaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, User item) {
                search.setText(item.getName());
            }
        });
        mGreenDaoAdapter.setOnItemLongClickListener(new GreenDaoAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, User item) {
                if (userList.size() != 0 ){
                    GreenDaoHelper.deleteSingle(item);
                    userList.remove(item);
                    mGreenDaoAdapter.resetData(userList);
                }
            }
        });
        mTagFlowLayout.setMaxSelectCount(1);
        mTagFlowLayout.setAdapter(new TagAdapter<String>(data) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.search_page_flowlayout_tv, mTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(MainActivity.this, data[position], Toast.LENGTH_SHORT).show();
                search.setText(data[position]);
                return true;
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        //searchView = findViewById(R.id.sv);
        search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        mTagFlowLayout = findViewById(R.id.tfl);
        mRecyclerView = findViewById(R.id.rv);
        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                GreenDaoHelper.deleteAll();
                userList.clear();
                mGreenDaoAdapter.resetData(userList);
                break;
            case R.id.btn_search:
                userList = GreenDaoHelper.queryAll();
                boolean haveRepeat = false;
                String searchTxt = search.getText().toString();
                for (User user : userList) {
                    if (user.getName().equals(searchTxt)) {
                        haveRepeat = true;
                        break;
                        /*GreenDaoHelper.count();
                        Log.e("ZZF"," ---" +GreenDaoHelper.count());*/
                    }
                }
                if (!haveRepeat) {
                    insertDB(searchTxt);
                }
                /*userList.clear();*/
                /*userList = GreenDaoHelper.queryAll();*/
                Log.e("ZZF", "" + userList.size());
                mGreenDaoAdapter.resetData(userList);
                break;
            default:
                break;
        }
    }

    private void insertDB(String name) {
        id++;
        User user = new User();
        user.setId(id);
        user.setName(name);
        userList.add(user);
        GreenDaoHelper.insertSingle(user);
    }
}
