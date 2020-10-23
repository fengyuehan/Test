package com.example.jetpack.databinding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.jetpack.BR;
import com.example.jetpack.R;

public class DataBindingActivity extends AppCompatActivity {
    User user;
    ActivityDatabindingBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        user = new User("zzf",18);
        binding.setUser(user);
    }

    public void update(View view){
        user.setName("zzzf");
        user.setAge(19);
        binding.setVariable(BR.user,user);
    }
}
