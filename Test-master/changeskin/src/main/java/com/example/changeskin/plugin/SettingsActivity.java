package com.example.changeskin.plugin;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.changeskin.R;
import com.example.changeskin.activity.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolBar(true);
        getToolbar().setTitle("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        configFragment();
    }

    private void configFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.FrameLayout,new SettingsFragment())
                .commitAllowingStateLoss();
    }
}
