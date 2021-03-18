package com.example.androidplugin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : zhangzf
 * date   : 2021/3/16
 * desc   :
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static MyApplication mApplication;

    /**
     * 为了重置,否则在HookAMS的情况下第二次之后的启动都是已经注册的Activity
     */
    private static Object mIActivityManagerObj;
    private static Object msPackageManager;
    private static Object mmPM;

    /**
     * 是否是Hook Instrumentation
     * true:Hook Instrumentation
     * false:Hook AMS and PMS
     */
    private final boolean mHookInstrumentation = false;

    private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        unseal();
        initApplication();
        setClassLoaderReporter();
        getPackageManager(base);
        handleActivity(base);
        /*HookInstrumentation.hookInstrumentation(base);
        mIActivityManagerObj = HookAMS.getIActivityManager();*/
        installActivity();
        handleService(base);
    }

    /**
     * 安装service插件,处理service插件
     */
    private void handleService(Context context) {
        try{
            HookAMSForServicePlugin.hookActivityManager();
        }catch (Throwable e){
            e.printStackTrace();
        }
        Runnable providerRunnable = () -> {
            final File apkFile = getFileStreamPath(PluginApkNameVersion.PLUGIN_SERVICE_APK);
            final File odexFile = getFileStreamPath(PluginApkNameVersion.PLUGIN_SERVICE_DEX);
            if (!apkFile.exists()) {
                Log.d("zzf", "pluginService apk start extract");
                PluginUtils.extractAssets(context, PluginApkNameVersion.PLUGIN_SERVICE_APK);
            }
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), apkFile, odexFile);
            try {
                ServiceManager.getInstance().preLoadServices(apkFile);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        mSingleThreadExecutor.execute(providerRunnable);
    }

    private void setClassLoaderReporter() {
        BaseDexClassLoaderReporter.setReporterHook();
    }

    private void handleActivity(Context context) {
        if (mHookInstrumentation) {
            HookInstrumentation.hookInstrumentation(context);
        } else {
            mIActivityManagerObj = HookAMS.getIActivityManager();
        }
    }

    private void installActivity() {
        Runnable pathClassLoaderRunable = () -> {
            PluginUtils.extractAssets(MyApplication.getInstance(), PluginApkNameVersion.PLUGIN_ACTIVITY_APK);
            File apkFile = getFileStreamPath(PluginApkNameVersion.PLUGIN_ACTIVITY_APK);
            File dexFile = getFileStreamPath(PluginApkNameVersion.PLUGIN_ACTIVITY_DEX);
            if (mHookInstrumentation){
                try {
                    LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(apkFile);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }else {
                //插件使用宿主的ClassLoader加载
                BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), apkFile, dexFile);
            }
        };
        mSingleThreadExecutor.execute(pathClassLoaderRunable);
    }

    private void getPackageManager(Context base) {
        mmPM = HookPMS.getApplicationPackageManager(base);
        msPackageManager = HookPMS.getPackageManager();
    }

    private void initApplication() {
        mApplication = this;
    }

    private void unseal() {
        int reflection = Reflection.unseal();
        Log.d("zzf", reflection == 0 ? "hide api exempt success" : "hide api exempt failure");
    }

    public static void resetAms() {
        if (mIActivityManagerObj == null) return;
        HookAMS.resetIActivityManager(mIActivityManagerObj);
    }

    public static void resetPms() {
        if (mmPM == null){
            return;
        }
        HookPMS.resetApplicatonPackageManager(getInstance(),mmPM);
        if (msPackageManager == null){
            return;
        }
        HookPMS.resetPackageManager(msPackageManager);
    }

    public static MyApplication getInstance() {
        return mApplication;
    }

    public boolean isHookInstrumentation() {
        return mHookInstrumentation;
    }
}
