package com.example.floatingwindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_show,btn_show_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_show = findViewById(R.id.btn_show);
        btn_show_view = findViewById(R.id.btn_show_view);
        btn_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show:

                break;
            case R.id.btn_show_view:
                /**
                 * ExampleFloatingService.isStar 表示已经启动服务
                 */
                if (ExampleFloatingService.isStart){
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ExampleFloatingService.ACTION_CLICK));
                }else {
                    /**
                     * 没有启动则需要看是否需要悬浮窗权限
                     */
                    if (FloatingWindowHelper.canDrawOverlays(this,true)){
                        startService(new Intent(this,ExampleFloatingService.class));
                    }
                }
                break;
        }
    }
}
