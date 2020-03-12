package com.example.roomdemo.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.roomdemo.LiveDataViewModel;
import com.example.roomdemo.LogUtils;
import com.example.roomdemo.R;
import com.example.roomdemo.data.TestBean;
import com.example.roomdemo.databinding.ActivityLiveDataBinding;

public class LiveDataActivity extends AppCompatActivity {
    private ActivityLiveDataBinding activityLiveDataBinding;
    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private TestBean testBean = new TestBean();
    private LiveDataViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        activityLiveDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_live_data);
        activityLiveDataBinding.setOnlyLive(liveData.getValue());
        activityLiveDataBinding.setDataSource(testBean);
        activityLiveDataBinding.setLifecycleOwner(this);
        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        activityLiveDataBinding.setLiveModule(model);
        addLiveObserve();
        addTextViewChange();

    }

    private void addTextViewChange() {
        activityLiveDataBinding.txtOnlyLive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i("TextView的变化", "单独使用LiveData ==> " + s);
            }
        });
        activityLiveDataBinding.txtDataBinding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i("TextView的变化", "DataBinding双向绑定 ==> " + s);
            }
        });
        activityLiveDataBinding.txtViewmodelLivedata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i("TextView的变化", "ViewModel配合LiveData使用 ==> " + s);
            }
        });
    }

    private void addLiveObserve() {
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityLiveDataBinding.setOnlyLive(s);
                LogUtils.i("观察LiveData", "单独使用LiveData ==> " + s);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        liveData.postValue("单独LiveData使用");
        testBean.name.set("我是DataBinding双向绑定");
        model.getData().postValue("ViewModel配合LiveData使用");
    }
}
