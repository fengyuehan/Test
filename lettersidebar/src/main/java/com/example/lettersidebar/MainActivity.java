package com.example.lettersidebar;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qdx.indexbarlayout.IndexLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText et_search;
    private TextView tv_tip;
    private ImageView iv_delete;
    private LetterSlideBar lsv;
    private LinearLayoutManager mLayoutManager;
    private Boolean mIsScale = false;
    private ContactWithSearchAdapter mAdapter;
    private List<ConstactBean> list = new ArrayList<>();
    private String[] names = {"宋江", "卢俊义", "吴用", "公孙胜", "关胜", "林冲",
            "秦明", "呼延灼", "花荣", "柴进", "李应", "朱仝", "鲁智深", "武松", "董平", "张清", "杨志", "徐宁",
            "索超", "戴宗", "刘唐", "李逵", "史进", "穆弘", "雷横", "李俊", "阮小二", "张横", "阮小五",
            " 张顺", "阮小七", "杨雄", "石秀", "解珍", " 解宝", "燕青", "朱武", "黄信", "孙立", "宣赞",
            "郝思文", "韩滔", "彭玘", "单廷珪", "魏定国", "萧让", "裴宣", "欧鹏", "邓飞", " 燕顺", "杨林",
            "凌振", "蒋敬", "吕方", "郭 盛", "安道全", "皇甫端", "王英", "扈三娘", "鲍旭", "樊瑞", "孔明", "孔亮", "项充",
            "李衮", "金大坚", "马麟", "童威", "童猛", "孟康", "侯健", "陈达", "杨春", "郑天寿", "陶宗旺",
            "宋清", "乐和", "龚旺", "丁得孙", "穆春", "曹正", "宋万", "杜迁", "薛永", "施恩"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < names.length; i++){
            ConstactBean constactBean = new ConstactBean();
            constactBean.setContact("abcd",names[i]);
            GreenDaoHelper.insertSingle(constactBean);
            list.add(constactBean);
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv);
        et_search = findViewById(R.id.et_search);
        iv_delete = findViewById(R.id.iv_delete);
        tv_tip = findViewById(R.id.tv_tip);
        lsv = findViewById(R.id.lsv);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactWithSearchAdapter(list);
        recyclerView.addItemDecoration(new TitleItemDecoration(this, list));
        recyclerView.setAdapter(mAdapter);
    }

    private void showLetter(String letter) {
        tv_tip.setText(letter);
        if (!mIsScale) {
            mIsScale = true;
            ViewCompat.animate(tv_tip)
                    .scaleX(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(380)
                    .start();
            ViewCompat.animate(tv_tip)
                    .scaleY(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(380)
                    .start();
        }

        // 延时隐藏
        tv_tip.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(tv_tip)
                        .scaleX(0f)
                        .setDuration(380)
                        .start();
                ViewCompat.animate(tv_tip)
                        .scaleY(0f)
                        .setDuration(380)
                        .start();
                mIsScale = false;
            }
        }, 380);
    }
}
