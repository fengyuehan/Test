package com.example.hilt;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Retrofit;

/**
 * author : zhangzf
 * date   : 2021/3/15
 * desc   :
 */
@AndroidEntryPoint
public class MultiActivity extends AppCompatActivity {
    /**
     * cannot be provided without an @Inject constructor
     *
     * 在使用这个实例的时候，这个实例必须用public修饰，不写修饰符也会报错，（针对接口的实例，不是接口的实例没有修饰符可以编译通过）
     */
    //@bindGasEngine
    @Inject
    public GasEngine gasEngine;

    /*@bindElectricEngine
    @Inject
    public ElectricEngine electricEngine;*/

    @Inject
    public Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gasEngine.start();
        gasEngine.shutdown();
        /*electricEngine.start();
        electricEngine.shutdown();*/
        Log.e("zzf",retrofit.toString());
    }
}
