package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {
    private final String TAG = "ZZF";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"------------onCreateView-------------");
        return inflater.inflate(R.layout.fragment1,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"------------onAttach-------------");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"------------onViewCreated-------------");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"------------onStart-------------");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"------------onAttach-------------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"------------onPause-------------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"------------onAttach-------------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"------------onDestroyView-------------");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"------------onStop-------------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"------------onCreate-------------");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG,"------------onActivityCreated-------------");
    }
}
