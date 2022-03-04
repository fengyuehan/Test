package com.example.litepaldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.litepaldemo.bean.Category;
import com.example.litepaldemo.bean.CommentBean;
import com.example.litepaldemo.bean.IntroductionBean;
import com.example.litepaldemo.bean.NewsBean;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);

        SQLiteDatabase db = Connector.getDatabase();
        DataSupport.deleteAll(NewsBean.class);
        DataSupport.deleteAll(CommentBean.class);
        DataSupport.deleteAll(Category.class);
        DataSupport.deleteAll(IntroductionBean.class);


        NewsBean newsBean = new NewsBean("丰城风景秀天下", "数据源", System.currentTimeMillis());
        newsBean.saveOrUpdate();
        List<CommentBean> commentBeanList = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            CommentBean commentBean = new CommentBean(System.currentTimeMillis(),"小白", "今天天气不错!+" + i);
            commentBeanList.add(commentBean);
        }
        DataSupport.saveAll(commentBeanList);
        newsBean.setList(commentBeanList);

        IntroductionBean introductionBean = new IntroductionBean("丰城是江西下的直辖市，气候适中。");
        introductionBean.save();
        newsBean.setIntroductionBean(introductionBean);
        //设置类别
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("丰城"));
        categoryList.add(new Category("环境"));
        categoryList.add(new Category("温度"));
        DataSupport.saveAll(categoryList);
        newsBean.setCategoryList(categoryList);
        //保存更新数据
        newsBean.save();

        //查询
        List<NewsBean> list = DataSupport.findAll(NewsBean.class);
        List<CommentBean> list1 = DataSupport.findAll(CommentBean.class);
        List<Category> list2 = DataSupport.findAll(Category.class);
        List<IntroductionBean> list3 = DataSupport.findAll(IntroductionBean.class);
        textView.setText(list1.toString() + "\n\n" + list2.toString()+"\n\n"+list3.toString()+"\n\n"+list.toString());
    }
}
