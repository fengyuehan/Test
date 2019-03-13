
package com.example.wanandroid.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbase.utils.ToastUtils;
import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseActivity;
import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.event.MessageEvent;
import com.example.wanandroid.model.constant.Constant;
import com.example.wanandroid.model.constant.EventConstant;
import com.example.wanandroid.ui.gank.GankFragment;
import com.example.wanandroid.ui.home.HomePageFragment;
import com.example.wanandroid.ui.hot.HotActivity;
import com.example.wanandroid.ui.knowledge.KnowledgeFragment;
import com.example.wanandroid.ui.mine.PersonalFragment;
import com.example.wanandroid.ui.project.ProjectFragment;
import com.example.wanandroid.ui.search.SearchActivity;
import com.example.wanandroid.util.app.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private TextView title;
    private ImageView icon_back,menu_main_hot,menu_main_search;

    private List<Fragment> fragments;
    private BasePresenter mBasePresenter;
    private int lastIndex;
    private long mExitTime;

    @Override
    protected void initData() {
        initNavigation();
    }

    private void initNavigation() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab_main:
                        mFloatingActionButton.setVisibility(View.VISIBLE);
                        selectFragment(0);
                        break;
                    case R.id.tab_knowledge:
                        mFloatingActionButton.setVisibility(View.VISIBLE);
                        selectFragment(1);
                        break;
                    case R.id.tab_project:
                        mFloatingActionButton.setVisibility(View.VISIBLE);
                        selectFragment(2);
                        break;
                    case R.id.tab_gank:
                        mFloatingActionButton.setVisibility(View.GONE);
                        selectFragment(3);
                        break;
                    case R.id.tab_mine:
                        mFloatingActionButton.setVisibility(View.GONE);
                        selectFragment(4);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initUI() {
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mFloatingActionButton = findViewById(R.id.float_button);
        mFloatingActionButton.setOnClickListener(this);
        title = findViewById(R.id.tv_title);
        title.setText("WanAndroid");
        icon_back = findViewById(R.id.iv_back_toolbar);
        icon_back.setVisibility(View.GONE);
        menu_main_hot = findViewById(R.id.menu_main_hot);
        menu_main_search = findViewById(R.id.menu_main_search);
        mBasePresenter = new BasePresenter();
        initFragment();
        selectFragment(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_hot:
                startActivity(new Intent(mBaseActivity, HotActivity.class));
                break;
            case R.id.menu_main_search:
                startActivity(new Intent(mBaseActivity, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void selectFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        fragmentTransaction.hide(lastFragment);
        if (!currentFragment.isAdded()){
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            fragmentTransaction.add(R.id.frame_layout,currentFragment);
        }
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
        mBasePresenter.setCurrentPage(position);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new GankFragment());
        fragments.add(new HomePageFragment());
        fragments.add(new PersonalFragment());
        fragments.add(new ProjectFragment());
        fragments.add(new KnowledgeFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /*@Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
    }*/


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_button:
                scrollToTop();
                break;
            case R.id.menu_main_hot:
                startActivity(new Intent(mBaseActivity, HotActivity.class));
                break;
            case R.id.menu_main_search:
                startActivity(new Intent(mBaseActivity, SearchActivity.class));
                break;

        }
    }

    private void scrollToTop() {
        switch (mBasePresenter.getCurrentPage()){
            case 0:
                EventBus.getDefault().post(new MessageEvent(EventConstant.MAINSCROLLTOTOP,""));
                break;
            case 1:
                EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGESCROLLTOTOP,""));
                break;
            case 2:
                EventBus.getDefault().post(new MessageEvent(EventConstant.PROJECTSCROLLTOTOP,""));
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(mBaseActivity,getString(R.string.exit_system),Toast.LENGTH_LONG).show();
            mExitTime = System.currentTimeMillis();
        } else {
            SharedPreferenceUtil.put(mBaseActivity, Constant.ISLOGIN, Constant.FALSE);
            finish();
        }
    }
}
