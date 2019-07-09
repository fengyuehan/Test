package com.example.keyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class PasswordActivity extends AppCompatActivity {
    private PasswordEditText passwordEditText;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //有三种方式实现
        //1、设置6个edittext，然后每个edittext只能输入一个数字
        //2、完全自定义view（继承View）
        //3、继承自EditView实现自定义view
        setContentView(R.layout.activity_password);
        passwordEditText = findViewById(R.id.password_edit_text);
        editText = findViewById(R.id.et);

        //修改为密码类型
        //editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
