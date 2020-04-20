package com.peopeltech.interviewpractice.android;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author hych
 * @since 2020/4/14 14:29
 */
public class InterviewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
