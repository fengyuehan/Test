package com.example.changeskin.fragment;

import android.content.Intent;
import android.view.View;

import com.example.changeskin.MainActivity;
import com.example.changeskin.R;

import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @OnClick(R.id.item)
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.item:
                startActivity(new Intent(mActivity, MainActivity.class));
                break;
        }
    }
}
