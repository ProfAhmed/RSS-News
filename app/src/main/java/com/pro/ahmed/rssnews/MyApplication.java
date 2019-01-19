package com.pro.ahmed.rssnews;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.pro.ahmed.rssnews.services.FetchNewsService;

public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        startService(new Intent(this, FetchNewsService.class)); //start service which is MyService.java

    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
