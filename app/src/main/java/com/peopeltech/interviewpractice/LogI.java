package com.peopeltech.interviewpractice;

import android.util.Log;

/**
 * @author hych
 * @since 2020/4/14 17:34
 */
public class LogI {

    public static void i(Object obj, String msg) {
        Log.i(obj.getClass().getSimpleName(), msg);
    }

    public static void d(Object obj, String msg) {
        Log.d(obj.getClass().getSimpleName(), msg);
    }

    public static void w(Object obj, String msg) {
        Log.w(obj.getClass().getSimpleName(), msg);
    }
}
