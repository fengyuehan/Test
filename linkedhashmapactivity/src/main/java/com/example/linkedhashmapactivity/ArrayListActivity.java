package com.example.linkedhashmapactivity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2020/12/15
 * desc   :
 */
public class ArrayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_list);
        init();
        List<Integer> list = new ArrayList<>();
    }

    private void init() {
        /*int[] a = {1,2,3,4};
        List list  = Arrays.asList(a);
        Log.e("zzf",list.size()+"");*/
        String[] strs = {"a","b","c","d"};
        List<String> list1 = Arrays.asList(strs);
        //Log.e("zzf",list1.size()+"");
        /*Integer[] c = {1,2,3,4};
        List list2  = Arrays.asList(c);
        Log.e("zzf",list2.size()+"");
        float[] d = {1,2,3,4};
        List list3  = Arrays.asList(d);
        Log.e("zzf",list3.size()+"");*/
        list1.add("e");
        Log.e("zzf",list1.size()+"");
        for (String s:list1){
            Log.e("zzf",s);
        }
    }
}
