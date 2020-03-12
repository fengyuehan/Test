package com.example.roomdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.roomdemo.R;
import com.example.roomdemo.data.Clothes;
import com.example.roomdemo.data.DBInstance;
import com.example.roomdemo.data.Person;
import com.example.roomdemo.databinding.RoomBinding;

@SuppressLint("Registered")
public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    private RoomBinding mRoomBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);
        mRoomBinding = DataBindingUtil.setContentView(this,R.layout.room);
        mRoomBinding.setOnClickListener(this);
        checkAll();
    }

    private void checkAll() {
        int size = DBInstance.getInstance().getPresonDao().getAll().size();
        mRoomBinding.txtAll.setText("总人数： = " + size);
        int clothesCount = DBInstance.getInstance().getClothesDao().getAll().size();
        mRoomBinding.txtClothesAll.setText("当前衣服总数： = " + clothesCount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete_:
                Person person1 = DBInstance.getInstance().getPresonDao().getPersonByUid(100);
                DBInstance.getInstance().getPresonDao().delete(person1);
                checkAll();
                break;
            case R.id.btn_insert:
                Person person = new Person("ROOM",10);
                DBInstance.getInstance().getPresonDao().insert(person);
                checkAll();
                break;
            case R.id.btn_insert_clothes_black:
                Clothes clothes = new Clothes();
                clothes.setId(1);
                clothes.setColor("黑色");
                clothes.setFather_id(100);
                DBInstance.getInstance().getClothesDao().insert(clothes);
                checkAll();
                break;
            case R.id.btn_insert_clothes_red:
                Clothes clothes_ = new Clothes();
                clothes_.setId(2);
                clothes_.setColor("红色");
                clothes_.setFather_id(101);
                DBInstance.getInstance().getClothesDao().insert(clothes_);
                checkAll();
                break;
            case R.id.btn_insert_one:
                Person person2 = new Person("岩浆", 18);
                person2.setUid(100);
                DBInstance.getInstance().getPresonDao().insert(person2);
                checkAll();
                break;
            default:
                break;
        }
    }
}
