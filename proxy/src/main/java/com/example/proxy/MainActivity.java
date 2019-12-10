package com.example.proxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proxy.DynamicProxy.DynamicProxy;
import com.example.proxy.DynamicProxy.IPersonService;
import com.example.proxy.DynamicProxy.PersonService;
import com.example.proxy.StaticProxy.ProxyService;
import com.example.proxy.StaticProxy.UserService;

public class MainActivity extends AppCompatActivity {

    private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userService = new UserService();
                ProxyService proxyService = new ProxyService(userService);
                proxyService.addUser();
                proxyService.deleteUser();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPersonService iPersonService = DynamicProxy.getProxy();
                iPersonService.addPerson();
                iPersonService.deletePerson();
            }
        });
    }

    private void initView() {
        button1 = findViewById(R.id.btn_static);
        button2 = findViewById(R.id.btn_Dynamic);
    }
}
