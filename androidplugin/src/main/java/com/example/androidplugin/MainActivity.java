package com.example.androidplugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn_test_hide_black_api,btn_start,btn_start_appcompat,btn_start_instrumentation,btn_start_instrumentation_appCompat,
            btn_start_plugin_apk_activity,btn_start_plugin_apk_appcompat_activity,btn_start_plugin_service,
            btn_stop_plugin_service,btn_send_broadcast_to_plugin,btn_insert_provider,btn_query_provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLinstener();
    }

    private void initLinstener() {
        btn_test_hide_black_api.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_start_appcompat.setOnClickListener(this);
        btn_start_instrumentation.setOnClickListener(this);
        btn_start_instrumentation_appCompat.setOnClickListener(this);
        btn_start_plugin_apk_activity.setOnClickListener(this);
        btn_start_plugin_apk_appcompat_activity.setOnClickListener(this);
        btn_start_plugin_service.setOnClickListener(this);
        btn_stop_plugin_service.setOnClickListener(this);
        btn_send_broadcast_to_plugin.setOnClickListener(this);
        btn_insert_provider.setOnClickListener(this);
        btn_query_provider.setOnClickListener(this);
    }

    private void initView() {
        btn_test_hide_black_api = findViewById(R.id.btn_test_hide_black_api);
        btn_start = findViewById(R.id.btn_start);
        btn_start_appcompat = findViewById(R.id.btn_start_appcompat);
        btn_start_instrumentation = findViewById(R.id.btn_start_instrumentation);
        btn_start_instrumentation_appCompat = findViewById(R.id.btn_start_instrumentation_appCompat);
        btn_start_plugin_apk_activity = findViewById(R.id.btn_start_plugin_apk_activity);
        btn_start_plugin_apk_appcompat_activity = findViewById(R.id.btn_start_plugin_apk_appcompat_activity);
        btn_start_plugin_service = findViewById(R.id.btn_start_plugin_service);
        btn_stop_plugin_service = findViewById(R.id.btn_stop_plugin_service);
        btn_send_broadcast_to_plugin = findViewById(R.id.btn_send_broadcast_to_plugin);
        btn_insert_provider = findViewById(R.id.btn_insert_provider);
        btn_query_provider = findViewById(R.id.btn_query_provider);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test_hide_black_api:
                boolean success = testBlackListApi();
                Toast.makeText(this, success ? "success" : "fail", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_start:
                startHook(false);
                startActivity(new Intent(this, TargetActivity.class));
                break;
            case R.id.btn_start_appcompat:
                startHook(true);
                startActivity(new Intent(this, TargetAppCompatActivity.class));
                break;
            case R.id.btn_start_instrumentation:
                MyApplication.resetPms();
                HookUtil.hookPackageManager(this,StubActivity.class);
                startActivity(new Intent(this,TargetActivity.class));
                break;
            case R.id.btn_start_instrumentation_appCompat:
                MyApplication.resetPms();
                HookUtil.hookPackageManager(this, StubAppCompatActivity.class);
                startActivity(new Intent(this, TargetAppCompatActivity.class));
                break;
            case R.id.btn_start_plugin_apk_activity:
                //startHook(false);
                //use hook Instrumentation, please modify MApplication#mHookInstrumentation=true;
                MyApplication.resetPms();
                HookUtil.hookPackageManager(this, StubActivity.class);
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.pluginactivity", "com.example.pluginactivity.PluginActivity"));
                startActivity(intent);
                break;
            case R.id.btn_start_plugin_apk_appcompat_activity:
                //startHook(true);
                MyApplication.resetPms();
                HookUtil.hookPackageManager(this, StubAppCompatActivity.class);
                Intent intent1 = new Intent();
                intent1.setComponent(new ComponentName("com.example.pluginactivity", "com.example.pluginactivity.PluginAppCompatActivity"));
                startActivity(intent1);
                break;
            case R.id.btn_start_plugin_service:
                startService(new Intent().setComponent(new ComponentName("com.example.pluginservice", "com.example.pluginservice.TargetService1")));
                break;
            case R.id.btn_stop_plugin_service:
                stopService(new Intent().setComponent(new ComponentName("com.example.pluginservice", "com.example.pluginservice.TargetService1")));
                break;
            case R.id.btn_insert_provider:

                break;
            case R.id.btn_send_broadcast_to_plugin:

                break;
            case R.id.btn_query_provider:

                break;
        }
    }

    private void startHook(boolean isAppCompatActivity) {
        MyApplication.resetPms();
        MyApplication.resetAms();
        if (isAppCompatActivity) {
            HookAMS.hookStartActivity(this, StubAppCompatActivity.class, true);
        } else {
            HookAMS.hookStartActivity(this, StubActivity.class, false);
        }
    }

    private boolean testBlackListApi() {
        boolean success = true;
        if (Build.VERSION.SDK_INT < 29) return true;
        try {
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            Field field = activityManagerClazz.getField("INSTR_FLAG_DISABLE_HIDDEN_API_CHECKS");
            Object object = field.get(null);
            if (object == null) {
                return false;
            }
            int check_flag = (int) object;
            Log.d("zzf", "get blacklist api :" + check_flag);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            success = false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            success = false;
        } catch (IllegalAccessException e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }
}