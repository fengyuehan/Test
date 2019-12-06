package com.example.fragment;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1,button2,button3,button4,button5,button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        button1 = findViewById(R.id.btn_show_fragment1);
        button2 = findViewById(R.id.btn_show_fragment2);
        button3 = findViewById(R.id.btn_remove_fragment1);
        button4 = findViewById(R.id.btn_remove_fragment2);
        button5 = findViewById(R.id.btn_replace_fragment);
        button6 = findViewById(R.id.btn_jump);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_fragment1:
                Fragment1 fragment1 = new Fragment1();
                addFragment(fragment1,"fragment1");
                break;
            case R.id.btn_show_fragment2:
                Fragment2 fragment2 = new Fragment2();
                addFragment(fragment2,"fragment2");
                break;
            case R.id.btn_remove_fragment1:
                removeFragment1();
                break;
            case R.id.btn_remove_fragment2:
                removeFragment2();
                break;
            case R.id.btn_replace_fragment:
                replaceFragment1();
                break;
            case R.id.btn_jump:
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                break;
                default:
                    break;
        }
    }

    private void replaceFragment1() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment2 fragment2 = new Fragment2();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment2);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void removeFragment2() {FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragment2");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void removeFragment1(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragment1");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void addFragment(Fragment fragment,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment,tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
