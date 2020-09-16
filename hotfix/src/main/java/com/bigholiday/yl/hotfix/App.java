package com.bigholiday.yl.hotfix;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

public class App extends Application implements BetaPatchListener {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(this,"b4fed379f2",false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        Beta.installTinker();
    }

    @Override
    public void onPatchReceived(String s) {

    }

    @Override
    public void onDownloadReceived(long l, long l1) {

    }

    @Override
    public void onDownloadSuccess(String s) {

    }

    @Override
    public void onDownloadFailure(String s) {

    }

    @Override
    public void onApplySuccess(String s) {
        restartApp();
    }



    @Override
    public void onApplyFailure(String s) {

    }

    @Override
    public void onPatchRollback() {

    }

    /**
     * 更新补丁后，重启app,不然不起效
     */
    private void restartApp() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
