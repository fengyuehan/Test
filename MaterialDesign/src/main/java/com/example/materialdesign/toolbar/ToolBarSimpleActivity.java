package com.example.materialdesign.toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.materialdesign.R;

import java.lang.reflect.Method;

public class ToolBarSimpleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.popuptheme);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("正标题");
        toolbar.setSubtitle("副标题");
        //toolbar.setNavigationIcon(R.drawable.c);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBarSimpleActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Toast.makeText(ToolBarSimpleActivity.this, "点击设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(ToolBarSimpleActivity.this, "点击编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(this, "点击分享", Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null){
            if (menu.getClass() == MenuBuilder.class){
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }
}
