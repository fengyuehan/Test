package com.example.jetpack.livedata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpack.R;
import com.example.jetpack.livedata.MyViewModel;

public class LiveDataActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private MyViewModel myViewModel;

    //观察多个数据
    MediatorLiveData<String> mediatorLiveData = new MediatorLiveData<>();

    MutableLiveData<String> liveData5 = new MutableLiveData<>();
    MutableLiveData<String> liveData6 = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        textView = findViewById(R.id.tv_num);
        button = findViewById(R.id.btn);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = myViewModel.increaseNumber();
                myViewModel.getInfo().setValue("info = " + x);

                myViewModel.getLiveDataSwitch().setValue(false);

                liveData5.setValue("liveData5");
                //liveData6.setValue("liveData6");
            }
        });
        myViewModel.getInfo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        myViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        myViewModel.liveDataSwitch1.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("zzf", "onChanged: " + s);
            }
        });

        mediatorLiveData.addSource(liveData5, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("zzf", "onChanged3: " + s);
                mediatorLiveData.setValue(s);
            }
        });

        mediatorLiveData.addSource(liveData6, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("zzf", "onChanged4: " + s);
                mediatorLiveData.setValue(s);
            }
        });

        mediatorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("zzf", "onChanged5: "+s);
                //无论liveData5、liveData6更新，都可以接收到
            }
        });
    }
}
