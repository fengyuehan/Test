package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout mContent;
    private RadioGroup mRadioGroup;
    private RadioButton mHome,mLoan,mMarket,mMine;

    private HomeFragment mHomeFragment;
    private MarketFragment mMarketFragment;
    private SupportFragment mSupportFragment;
    private MineFragment mMineFragment;

    private RadioButton mSelected;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        initView();
        int id = 0;
        if (savedInstanceState != null){
            id = savedInstanceState.getInt("rb_checked",0);
        }
        if (id == 0){
            showFragment(R.id.rb_home);
        }else {
            showFragment(id);
        }
    }

    private void showFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        switch (index){
            case R.id.rb_home:
                hideFragment(mFragmentTransaction);
                if (mHomeFragment != null){
                    mFragmentTransaction.show(mHomeFragment);
                }else {
                    mHomeFragment = new HomeFragment();
                    mFragmentTransaction.add(R.id.fl_content,mHomeFragment);
                }
                mFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_market:
                hideFragment(mFragmentTransaction);
                if (mMarketFragment != null){
                    mFragmentTransaction.show(mMarketFragment);
                }else {
                    mMarketFragment = new MarketFragment();
                    mFragmentTransaction.add(R.id.fl_content,mMarketFragment);
                }
                mFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_support:
                hideFragment(mFragmentTransaction);
                if (mSupportFragment != null){
                    mFragmentTransaction.show(mSupportFragment);
                }else {
                    mSupportFragment = new SupportFragment();
                    mFragmentTransaction.add(R.id.fl_content,mSupportFragment);
                }
                mFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.rb_mine:
                hideFragment(mFragmentTransaction);
                if (mMineFragment != null){
                    mFragmentTransaction.show(mMineFragment);
                }else {
                    mMineFragment = new MineFragment();
                    mFragmentTransaction.add(R.id.fl_content,mMineFragment);
                }
                mFragmentTransaction.commitAllowingStateLoss();

                break;
                default:
                    break;
        }
    }

    //解决Fragment重叠的问题
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mHomeFragment == null && fragment instanceof HomeFragment){
            mHomeFragment = (HomeFragment) fragment;
            getSupportFragmentManager().beginTransaction().hide(mHomeFragment).commit();
        }else if (mMarketFragment == null && fragment instanceof MarketFragment){
            mMarketFragment = (MarketFragment) fragment;
            getSupportFragmentManager().beginTransaction().hide(mMarketFragment).commit();
        }else if (mSupportFragment == null && fragment instanceof SupportFragment){
            mSupportFragment = (SupportFragment) fragment;
            getSupportFragmentManager().beginTransaction().hide(mSupportFragment).commit();
        }else if (mMineFragment == null && fragment instanceof MineFragment){
            mMineFragment = (MineFragment) fragment;
            getSupportFragmentManager().beginTransaction().hide(mMineFragment).commit();
        }
    }

    private void hideFragment(FragmentTransaction mFragmentTransaction) {
        if (mHomeFragment != null){
            mFragmentTransaction.hide(mHomeFragment);
        }
        if (mMarketFragment != null){
            mFragmentTransaction.hide(mMarketFragment);
        }
        if (mSupportFragment != null){
            mFragmentTransaction.hide(mSupportFragment);
        }
        if (mMineFragment != null){
            mFragmentTransaction.hide(mMineFragment);
        }
    }

    private void initView() {
        mContent = findViewById(R.id.fl_content);
        mRadioGroup = findViewById(R.id.rg);
        mHome = findViewById(R.id.rb_home);
        mLoan = findViewById(R.id.rb_support);
        mMarket = findViewById(R.id.rb_market);
        mMine = findViewById(R.id.rb_mine);
        mHome.setOnClickListener(this);
        mLoan.setOnClickListener(this);
        mMarket.setOnClickListener(this);
        mMine.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("rb_checked",mRadioGroup.getCheckedRadioButtonId());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {
        if (v != mHome){
            mSelected = (RadioButton) v;
        }else {
            mSelected = mHome;
            mSelected.setChecked(true);
        }
        showFragment(v.getId());
    }
}
