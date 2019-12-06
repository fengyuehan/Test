package com.example.flowlayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TagFlowLayout search_page_flowlayout;
    private RecyclerView search_page_rv;
    private RelativeLayout search_page_rl;
    private Button search_page_delete;
    private FlowLayoutAdapter mFlowLayoutAdapter;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld"};
    private List<FlowLayoutBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        beanList = new ArrayList<>();
        FlowLayoutBean flowLayoutBean = null;
        for (int i = 0 ; i < mVals.length; i++){
            flowLayoutBean = new FlowLayoutBean();
            flowLayoutBean.setTv(mVals[i]);
            beanList.add(flowLayoutBean);
        }
    }

    private void initView() {
        search_page_flowlayout = findViewById(R.id.search_page_flowlayout);
        search_page_rv = findViewById(R.id.search_page_rv);
        search_page_rl = findViewById(R.id.search_page_rl);
        search_page_delete = findViewById(R.id.search_page_delete);
        search_page_delete.setOnClickListener(this);
        search_page_flowlayout.setMaxSelectCount(5);
        search_page_flowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.search_page_flowlayout_tv,search_page_flowlayout,false);
                tv.setText(s);
                return tv;
            }
        });
        search_page_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(MainActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });
        search_page_flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        search_page_rv.setLayoutManager(linearLayoutManager);
        search_page_rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mFlowLayoutAdapter = new FlowLayoutAdapter(this);
        mFlowLayoutAdapter.resetData(beanList);
        search_page_rv.setAdapter(mFlowLayoutAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_page_delete:
                search_page_rl.setVisibility(View.VISIBLE);
                search_page_rv.setVisibility(View.GONE);
                search_page_delete.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
