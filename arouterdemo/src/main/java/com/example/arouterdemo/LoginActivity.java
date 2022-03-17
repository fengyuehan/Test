package com.example.arouterdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/a/login")
public class LoginActivity extends AppCompatActivity {
    /**
     * 被@Autowired修饰的成员变量不能背pravite修饰，不然运行时会报错
     */
    /**
     * 带参传递有两种方式接收：
     *
     * 1 如果@Autowired没有指定name，则成员变量的名字必须为传过来的key，不然没有效果
     *     @Autowired
     *     public String path;
     *
     *  2、如果@Autowired指定name，则成员变量的名字可以随便写。
     *      @Autowired(name = "path")
     *     public String pasts
     *
     *   无参和有参不能跳转同一个activity，不然只有有参的有效。
     */
    /*@Autowired
    public String path;*/

    @Autowired(name = "path")
    public String pasts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("zzf","跳转过来了");
        ARouter.getInstance().inject(this);
        Log.e("zzf","--------onCreate--------");
        TextView textView = findViewById(R.id.tv);
        textView.setText(pasts);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","--------onStart--------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","--------onResume--------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","--------onPause--------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","--------onStop--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","--------onDestroy--------");
    }
}
