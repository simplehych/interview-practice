package com.peopeltech.interviewpractice.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.peopeltech.interviewpractice.android.InterviewApplication;

/**
 * @author hych
 * @since 2020/4/17 15:05
 */
public class Util {

    public static Context getAppContext() {
        return InterviewApplication.getContext();
    }

    public static void runOnUiThread(Runnable runnable) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(runnable);
    }


    public static void toastShow(String msg) {
        Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShowLong(String msg) {
        Toast.makeText(getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void log(String msg) {
        Log.i("Util", msg);
    }

    public static void log(String tag, String msg) {
        Log.i(tag, msg);
    }
}
