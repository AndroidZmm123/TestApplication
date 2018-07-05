package com.example.zongm.testapplication;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author zongm on 2018/6/22
 */
public class MyApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }
}
