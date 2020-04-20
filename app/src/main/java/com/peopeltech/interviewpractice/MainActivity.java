package com.peopeltech.interviewpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;

import com.peopeltech.interviewpractice.android.image.GlideActivity;
import com.peopeltech.interviewpractice.android.thread.IntentServiceTest;
import com.peopeltech.interviewpractice.android.thread.ThreadTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startGlideActivity() {
        startActivity(new Intent(this, GlideActivity.class));
    }

    public void test(View view) {
        startGlideActivity();
    }

    public void startIntentService(View view) {

        Intent service = new Intent(this, IntentServiceTest.class);

        service.putExtra(IntentServiceTest.MESSAGE, "No.1");
        startService(service);

        service.putExtra(IntentServiceTest.MESSAGE, "No.2");
        startService(service);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                service.putExtra(IntentServiceTest.MESSAGE, "No.3");
                startService(service);
            }
        }, 2000);

        boolean lowMemory = getAvailableMemory().lowMemory;

        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.append(1, "");
    }

    private ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        return outInfo;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
