package com.example.livedatabus;

import android.app.Application;

import com.jeremyliao.liveeventbus.LiveEventBus;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LiveEventBus.config().supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
    }
}
