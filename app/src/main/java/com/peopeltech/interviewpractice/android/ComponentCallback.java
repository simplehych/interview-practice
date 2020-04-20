package com.peopeltech.interviewpractice.android;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

/**
 * @author hych
 * @since 2020/4/14 14:41
 */
public class ComponentCallback implements ComponentCallbacks2 {

    public void register(Context context) {
        context.registerComponentCallbacks(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
