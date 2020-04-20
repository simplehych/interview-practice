package com.peopeltech.interviewpractice.android.handler;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peopeltech.interviewpractice.java.Test;

/**
 * @author hych
 * @since 2020/4/7 17:46
 */
public class HandlerTestActivity extends Activity {

    private static final String TAG = "HandlerTestActivity";

    private static Handler mainHandler = new MainHandler();

    static class MainHandler extends Handler {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            Log.e(TAG, "MainHandler dispatchMessage " + Thread.currentThread());
        }
    }

    private ThreadLocal<Integer> mIntThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "post current thread: " + Thread.currentThread());
            }
        });

        mIntThreadLocal.set(1);
        Log.e(TAG, Thread.currentThread() + " mIntThreadLocal:" + mIntThreadLocal.get());


        new Thread() {
            @Override
            public void run() {
                super.run();
                mIntThreadLocal.set(2);
                Log.e(TAG, Thread.currentThread() + " mIntThreadLocal:" + mIntThreadLocal.get());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, Thread.currentThread() + " mIntThreadLocal:" + mIntThreadLocal.get());
            }
        }.start();


        childThread();

        for (int i = 0; i < 1000; i++) {
            Test test = new Test();
        }

    }


    public void childThread() {
        ChildThread childThread = new ChildThread(this);
        childThread.start();
    }

    static class ChildThread extends Thread {

        private Context context;

        public ChildThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Handler handler = new Handler() {
                @Override
                public void dispatchMessage(@NonNull Message msg) {
                    super.dispatchMessage(msg);
                    Log.e(TAG, "OtherThread dispatchMessage " + Thread.currentThread());
                }
            };
            handler.sendMessage(Message.obtain());
            Looper.loop();
        }
    }


    private void exitApp() {
        // 方式1
        System.exit(0);
        // 方式2
        Process.killProcess(Process.myPid());
        // 方式3
        finish();
    }
}
