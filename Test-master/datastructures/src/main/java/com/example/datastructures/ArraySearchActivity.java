package com.example.datastructures;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @ClassName ArraySearchActivity
 * @Description TODO
 * @Author user
 * @Date 2019/9/6
 * @Version 1.0
 */
public class ArraySearchActivity extends AppCompatActivity {

    //题目描述
    //在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，
    // 输入这样的一个二维数组和一个整数，判断数组中是否含有该整数
    //
    //解题思路
    //
    //二维数组是有序的，从右上角来看，向左数字递减，向下数字递增。
    //因此从右上角开始查找，
    //
    //当要查找数字比右上角数字大时，下移；
    //当要查找数字比右上角数字小时，左移；
    //如果出了边界，则说明二维数组中不存在该整数。
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        initView();
    }

    private void initView() {
        button = findViewById(R.id.btn);
        textView = findViewById(R.id.tv_data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean arraySearch(int[][] array,int target){
        boolean result ;
        if (array.length == 0 && array[0].length == 0){
            result = false;
            return result;
        }
        int m = array[0].length-1;
        int n = 0;
        int temp = array[n][m];
        while (target != temp){
            if (m > 0 && n < array.length-1){
                if (target > temp){
                    n += 1;
                }else if (target < temp){
                    m -= 1;
                }
                temp = array[n][m];
            }else {
                result = false;
                return result;
            }
        }
        result = true;
        return result;
    }
}
