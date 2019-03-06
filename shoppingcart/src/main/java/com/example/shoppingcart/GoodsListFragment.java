package com.example.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GoodsListFragment extends Fragment {

    private RecyclerView left, right;
    private MyLeftAdapter myLeftAdapter;
    private MyAdapter myAdapter;
    private FragmentInteraction listerner;
    private String[] mTitles = new String[]{"生活食品", "酒水饮料", "粮油副食", "日用品"};
    List<ShopCarLeftBean> data;
    List<ShopCar> list ;
    int count;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentInteraction){
            listerner = (FragmentInteraction) activity;
        }else{
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_goods, null);
        data = new ArrayList<>();
        list = new ArrayList<>();
        left = view.findViewById(R.id.rv_left);
        right = view.findViewById(R.id.rv_right);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        initLeftData();
        initRightData();
        initRecyclerViewLeft();
        initRecyclerViewRight();
    }

    private void initRightData() {
        for (int i = 0; i < 10; i++) {
            ShopCar shopCar = new ShopCar();
            shopCar.setId((long) i);
            shopCar.setName("商店" + i);
            shopCar.setNum(100);
            shopCar.setStockCount("100");
            shopCar.setPrice(10.0 + i);
            GreenDaoHelper.insertSingle(shopCar);
        }
        count = GreenDaoHelper.count();
    }

    private void initRecyclerViewRight() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        right.setLayoutManager(linearLayoutManager);
        myAdapter= new MyAdapter(getActivity());
        right.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        right.setAdapter(myAdapter);
        list = GreenDaoHelper.queryAll();
        myAdapter.resetData(list);
    }

    private void initLeftData() {
        for (int i = 0; i < mTitles.length; i++) {
            ShopCarLeftBean shopCarLeftBean = new ShopCarLeftBean();
            shopCarLeftBean.setName(mTitles[i]);
            data.add(shopCarLeftBean);
        }
    }

    private void initRecyclerViewLeft() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        left.setLayoutManager(linearLayoutManager);
        left.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        myLeftAdapter = new MyLeftAdapter(getActivity());
        left.setAdapter(myLeftAdapter);
        myLeftAdapter.resetData(data);
    }

    interface FragmentInteraction {
        void process(List<ShopCar> list);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listerner = null;
    }
}
