package com.example.wanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private List<Bean> mData;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mHeaderImage;
    private TextView mHeaderName;
    private MyAdapter mMyAdapter;
    private int mSelectItem = R.id.nav_home;
    private int mCurrentSelectPosition = 0;
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initDrawerLayout();
        initBottomNavigationView();
        initNavigationView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter = new MyAdapter(mData);
        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    mFloatingActionButton.hide();
                }else {
                    mFloatingActionButton.show();
                }
            }
        });
    }

    /*//这个方法是为了在toolbar上面添加一个menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_drawer,menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
    private void initNavigationView() {
        mNavigationView.setCheckedItem(R.id.nav_home);
        /*Resources resource=(Resources)getBaseContext().getResources();
        @SuppressLint("ResourceType")
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.drawable.navigation_menu_item_color);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.getMenu().getItem(0).setChecked(true);*/
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Log.e("zzf",itemId+"");
                switch (itemId){
                    case R.id.nav_home:
                        mPosition = 0;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_public_account:
                        mPosition = 1;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_collection:
                        mPosition = 2;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_scan:
                        mPosition = 3;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        mPosition = 4;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_welfare:
                        mPosition = 5;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_login_out:
                        mPosition = 6;
                        Toast.makeText(MainActivity.this,"功能正在开发中",Toast.LENGTH_SHORT).show();
                        break;
                }
                setSelectItem(mPosition);
                return true;
            }
        });
        View mHeaderView = mNavigationView.getHeaderView(0);
        mHeaderImage = mHeaderView.findViewById(R.id.headIv);
        mHeaderName = mHeaderView.findViewById(R.id.nameTv);
        mHeaderImage.setImageResource(R.drawable.ic_launcher_foreground);
        mHeaderName.setText("zzf");
    }

    private void setSelectItem(int mPosition){
        if (mCurrentSelectPosition == mPosition){
            return;
        }
        mNavigationView.getMenu().getItem(mCurrentSelectPosition).setChecked(false);
        mNavigationView.getMenu().getItem(mPosition).setChecked(true);
        mCurrentSelectPosition = mPosition;
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setSelectedItemId(R.id.action_home);
        mToolBar.setTitle(mBottomNavigationView.getMenu().getItem(0).getTitle());
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId){
                    case R.id.action_home:
                    case R.id.action_knowledge:
                    case R.id.action_navigation:
                    case R.id.action_project:
                        Log.e("zzf",item.getTitle().toString());
                        mToolBar.setTitle(item.getTitle());
                        break;
                }

                return true;
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            Bean bean = new Bean();
            bean.setName("测试" + i);
            mData.add(bean);
        }
    }

    private void initDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this
                ,mDrawerLayout
                ,mToolBar
                ,R.string.navigation_drawer_open
                ,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private void initView() {
        mAppBarLayout = findViewById(R.id.apl);
        mBottomNavigationView = findViewById(R.id.bnv);
        mDrawerLayout = findViewById(R.id.dl);
        mFloatingActionButton = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.rv);
        mToolBar = findViewById(R.id.tool_bar);
        mNavigationView =findViewById(R.id.nav_view);

    }
}
