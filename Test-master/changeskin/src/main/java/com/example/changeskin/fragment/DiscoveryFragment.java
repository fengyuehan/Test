package com.example.changeskin.fragment;

import android.content.Intent;
import android.view.View;

import com.example.changeskin.R;
import com.example.changeskin.plugin.SettingsActivity;
import com.example.changeskin.zip.ZipActivity;

import butterknife.OnClick;

public class DiscoveryFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @OnClick({R.id.tv,R.id.tv_plugin})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv:
                startActivity(new Intent(mActivity, ZipActivity.class));
                break;
            case R.id.tv_plugin:
                startActivity(new Intent(mActivity, SettingsActivity.class));
                break;
        }
    }
}
