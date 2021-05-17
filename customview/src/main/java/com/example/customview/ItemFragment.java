package com.example.customview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * author : zhangzf
 * date   : 2021/5/17
 * desc   :
 */
public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String s){
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",s);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,null);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.tv);
        String s = getArguments().getString("title");
        if (!TextUtils.isEmpty(s)){
            textView.setText(s);
        }else {
            Toast.makeText(getContext(),"没有携带值过来",Toast.LENGTH_LONG).show();
        }
    }
}
