package com.example.parcelable;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : zhangzf
 * date   : 2021/4/8
 * desc   :
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        Bean bean = (Bean) getIntent().getSerializableExtra("bean");
        Log.e("zzf","bean :" + bean);
        NameBean bean1 = getIntent().getParcelableExtra("bean1");
        Log.e("zzf","bean :" + bean1);
    }
}
