package com.example.androidplugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * author : zhangzf
 * date   : 2021/3/18
 * desc   :
 */
public class PluginProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Hook ActivityManagerNative之后，所有的插件Service的启动都被重定向了到了我们注册的ProxyService，
     * 这样可以保证我们的插件Service有一个真正的Service组件作为宿主；
     * 但是要执行特定插件Service的任务，我们必须把这个任务分发到真正要启动的Service上去；
     * 以onStart为例，在启动ProxyService之后，会收到ProxyService的onStart回调，
     * 我们可以在这个方法里面把具体的任务交给原始要启动的插件Service组件：
     */
    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("zzf", "onStart() called with " + "intent = [" + intent + "], startId = [" + startId + "]");
        ServiceManager.getInstance().onStart(intent, startId);
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zzf", "onStartCommand() called with " + "intent = [" + intent + "], startId = [" + startId + "]");
        return super.onStartCommand(intent, flags, startId);
    }
}
