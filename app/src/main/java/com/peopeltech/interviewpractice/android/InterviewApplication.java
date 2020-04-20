package com.peopeltech.interviewpractice.android;

import android.app.Application;
import android.content.Context;

/**
 * @author hych
 * @since 2020/4/14 14:38
 */
public class InterviewApplication extends Application {

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static Context getContext() {
        return context;
    }
}
