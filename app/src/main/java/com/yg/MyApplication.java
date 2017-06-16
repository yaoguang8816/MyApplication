package com.yg;

import android.app.Application;

/**
 * Created by baijunfeng on 17/5/10.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private static synchronized void setInstance(MyApplication app) {
        MyApplication.instance = app;
    }
}
