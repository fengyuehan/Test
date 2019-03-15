package com.example.wanandroid.ui.hot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseRootActivity;
import com.example.wanandroid.ui.Bean.SearchHot;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class HotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View, View.OnClickListener {
    private ImageView icon_back,menu_main_hot,menu_main_search;
    private TagFlowLayout tagFlowLayout;
    private TextView title;
    private List<SearchHot> list;
    private TagAdapter<SearchHot> adapter;

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        icon_back = findViewById(R.id.iv_back_toolbar);
        icon_back.setOnClickListener(this);
        menu_main_hot = findViewById(R.id.menu_main_hot);
        menu_main_hot.setVisibility(View.GONE);
        menu_main_search = findViewById(R.id.menu_main_search);
        menu_main_search.setVisibility(View.GONE);
        title = findViewById(R.id.tv_title);
        title.setText("常用网站");
        tagFlowLayout = findViewById(R.id.tfl);
        showLoading();
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        mPresenter.getHotWeb();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    public void getHotWebOk(List<SearchHot> data) {
        showNormal();
        list.clear();
        list.addAll(data);
        adapter = new TagAdapter<SearchHot>(list) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHot searchHot) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.item_flow_layout, null);
                text.setText(searchHot.getName());
                return text;
            }
        };
        tagFlowLayout.setAdapter(adapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Bundle bundle = new Bundle();
                bundle.putString("tltle",list.get(position).getName());
                bundle.putString("link",list.get(position).getLink());
                startActivity(new Intent(HotActivity.this,ArticleDetailsActivity.class),bundle);
                return false;
            }
        });
    }

    @Override
    public void getHotWebErr(String info) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_toolbar:
                finish();
                break;
                default:
                    break;
        }
    }
}
