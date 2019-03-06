package com.example.shoppingcart;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoodsListFragment.FragmentInteraction {
    private BottomSheetLayout mBottomSheetLayout;
    private ScrollableLayout mScrollableLayout;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;
    private TextView tv_money;
    private ImageView shopping_img;
    private TextView shopping_number;
    private TextView clear;
    private RecyclerView mBottomRecyclerView;
    private String[] titles = new String[]{"商品", "评价", "商家"};
    MyBottomAdapter myBottomAdapter;
    int shopping_num;
    List<ShopCar> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBottomSheetLayout = findViewById(R.id.bsl);
        mScrollableLayout = findViewById(R.id.sl);
        mTableLayout = findViewById(R.id.tl);
        mViewPager = findViewById(R.id.vp);
        mRelativeLayout = findViewById(R.id.title_bottom);
        tv_money = findViewById(R.id.shopping_money);
        shopping_img = findViewById(R.id.shopping_img);
        shopping_number = findViewById(R.id.shopping_number);
        initTabLayout();
        initViewPager();
        initBottom();
    }

    private void initBottom() {
        shopping_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        
        initTotilMoney();
        initNum();
    }

    private void showBottomSheet() {
        if (mBottomSheetLayout.isSheetShowing()){
            mBottomSheetLayout.dismissSheet();
        }else {
            mBottomSheetLayout.showWithSheetView(createBottomSheetView());
        }
    }

    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.shop_carlist_vice, (ViewGroup) getWindow().getDecorView(), false);
        clear = view.findViewById(R.id.clear);
        mBottomRecyclerView = view.findViewById(R.id.rv_bottom);
        clear.setOnClickListener(this);
        initBottomRecyclerView();
        return view;
    }

    private void initBottomRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBottomRecyclerView.setLayoutManager(linearLayoutManager);
        mBottomRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        myBottomAdapter = new MyBottomAdapter(this);
        mBottomRecyclerView.setAdapter(myBottomAdapter);
        data = GreenDaoHelper.queryAll();
        myBottomAdapter.resetData(data);
    }

    private void initNum() {
        shopping_num = GreenDaoHelper.count();
        shopping_number.setText("" + shopping_num);
    }

    private void initTotilMoney() {

    }

    private void initViewPager() {
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),titles));
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0){
                    mRelativeLayout.setVisibility(View.GONE);
                }else {
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initTabLayout() {
        for(int i = 0; i < titles.length; i++){
            mTableLayout.addTab(mTableLayout.newTab().setText(titles[i]));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear:

                break;
                default:
                    break;
        }
    }

    @Override
    public void process(List<ShopCar> list) {
        //data = list;
    }
}
