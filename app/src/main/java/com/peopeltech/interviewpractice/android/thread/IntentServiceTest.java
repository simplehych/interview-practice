package com.peopeltech.interviewpractice.android.thread;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.peopeltech.interviewpractice.LogI;

/**
 * @author hych
 * @since 2020/4/14 17:28
 */
public class IntentServiceTest extends IntentService {

    public static String MESSAGE = "msg";

    public IntentServiceTest() {
        super("IntentServiceTest");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        LogI.i(this, "onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogI.i(this, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        LogI.i(this, "stopService");
        return super.stopService(name);
    }

    @Override
    public void onCreate() {
        LogI.i(this, "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogI.i(this, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = intent.getStringExtra(MESSAGE);
        LogI.i(this, "onHandleIntent msg: " + msg);
    }
}
