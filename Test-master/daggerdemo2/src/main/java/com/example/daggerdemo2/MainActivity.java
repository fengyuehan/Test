package com.example.daggerdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.daggerdemo2.dagger.ApiService;
import com.example.daggerdemo2.dagger.HttpService;
import com.example.daggerdemo2.dagger.UserManager;
import com.example.daggerdemo2.dagger.di.DaggerUserComponet;
import com.example.daggerdemo2.dagger.di.HttpModule;
import com.example.daggerdemo2.dagger.di.UserModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    /**
     * 这个标识需要注入的对象
     */
    @Inject
    HttpService apiService_dev;

    @Inject
    HttpService apiService_release;

    @Inject
    UserManager userManager;

    private boolean is_dev = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 下面的两种方式都可以，
         * 第一种：
         * DaggerUserComponet.create().inject(this);
         *
         * 第二种的主要是为了提供有参数的方法
         * 比如：
         * public UserModule(Context context){
         *         mContext = context;
         *     }
         * 这种就需要第二种方式
         */
        //DaggerUserComponet.create().inject(this);
        DaggerUserComponet.builder()
                .httpComponet(App.getHttpComponet())
                .userModule(new UserModule(this))
                //.httpModule(new HttpModule())
                .build()
                .inject(this);//这一步是做真正的注入
        userManager.register();

        if(is_dev){
            apiService_dev.register();
        }else {
            apiService_release.register();
        }
    }
}
