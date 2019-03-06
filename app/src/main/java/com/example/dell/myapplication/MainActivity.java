package com.example.dell.myapplication;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Integer> datas;
    private RecyclerView.ItemDecoration mItemDecoration;
    private MyAdapter mMyAdapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        resources = getResources();
        datas = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            datas.add(resources.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mMyAdapter = new MyAdapter(this);
        mMyAdapter.setDatas(datas);
        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setmOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Integer data) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                //java.lang.NoClassDefFoundError: Failed resolution of: Landroid/support/v4/animation/AnimatorCompatHe
                //报这个错时将recyclerview的依赖改成：implementation 'com.android.support:recyclerview-v7:27.1.1'
                //int i = (int) (Math.random()*datas.size());
                int i = 1;
                mMyAdapter.addData(i, resources.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
                //mMyAdapter.notifyItemInserted(1);
                //mMyAdapter.notifyItemRangeInserted(2,6);
                break;
            case R.id.delete:
                //int i1 = (int) (Math.random()*datas.size());
                //int i1 = 1;
                //datas.remove(i1);
                mMyAdapter.removeData(2);
                //mMyAdapter.notifyItemRangeChanged(1,5);
                break;
            case R.id.list_view:
                mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mMyAdapter.setType(0);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.horizontal_list__iew:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                mMyAdapter.setType(0);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.gridview:
                mMyAdapter.setType(1);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.horizontalGridView:
                mMyAdapter.setType(1);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);

                break;
            case R.id.staggeredgridview:
                mMyAdapter.setType(3);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            default:
                break;
        }
        return true;
    }
}
