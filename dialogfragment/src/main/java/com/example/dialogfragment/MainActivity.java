package com.example.dialogfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements EditNameDialogFragment.LoginInputListener {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn =  findViewById(R.id.btn_dialog_fragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    
    private void showEditDialog() {
        EditNameDialogFragment mEditNameDialogFragment = new EditNameDialogFragment();
        mEditNameDialogFragment.show(getSupportFragmentManager(),"etit");
    }

    @Override
    public void onLoginInputComplete(String mUserName, String mPassWord) {
        Toast.makeText(this, "帐号：" + mUserName + ",  密码 :" + mPassWord, Toast.LENGTH_SHORT).show();
    }
}
