package com.example.changeskin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.changeskin.R;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base,container,false);
        LinearLayout linearLayout = root.findViewById(R.id.ll_container);
        int layoutId = getLayoutId();
        View content = inflater.inflate(layoutId,null);
        linearLayout.addView(content);
        ButterKnife.bind(this,linearLayout);
        return root;
    }

    protected abstract int getLayoutId();
}
