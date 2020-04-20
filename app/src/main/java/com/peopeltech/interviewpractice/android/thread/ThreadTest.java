package com.peopeltech.interviewpractice.android.thread;

import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author hych
 * @since 2020/4/9 13:39
 */
public class ThreadTest {

    private static final String TAG = "ThreadTest";

    public void testThread() throws Exception {
        Object object = new Object();
        Thread thread = new Thread();

        // sleep 睡一段时间
        Thread.sleep(1000);

        // wait/notify 配套使用
        thread.wait();
        thread.notify();

        // yield 放弃CPU时间片
        Thread.yield();

        // suspend/resume 配套使用
        // 过期，会导致死锁
        thread.suspend();
        thread.resume();
    }

    public void testCondition() {
        Condition condition;
    }

    public void testAsyncTask() {
//        new AsyncTask<Integer, Integer, Integer>() {
//            @Override
//            protected Integer doInBackground(Integer... integers) {
//                return null;
//            }
//        }.execute();

        Log.i(TAG, "run begin " + currentTime());
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable1 inter " + Thread.currentThread());
                Log.i(TAG, "runnable1 begin " + currentTime());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "runnable1 end " + currentTime());
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable2 inter " + Thread.currentThread());
                Log.i(TAG, "runnable2 begin " + currentTime());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "runnable2 end " + currentTime());
            }
        };

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable2 inter " + Thread.currentThread());
                Log.i(TAG, "runnable2 begin " + currentTime());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "runnable2 end " + currentTime());
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2, 1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3));

        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable2);
        threadPoolExecutor.execute(runnable3);

//        SerialExecutor serialExecutor = new SerialExecutor(threadPoolExecutor);
//        serialExecutor.execute(runnable1);
//        serialExecutor.execute(runnable2);
//        serialExecutor.execute(runnable3);
    }

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING    = -1 << COUNT_BITS;

    public void testSerial() {
        Log.i(TAG, "RUNNING:  " + toHH(RUNNING));

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable1  " + Thread.currentThread());
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable2  " + Thread.currentThread());
            }
        };
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runnable3  " + Thread.currentThread());
            }
        };
        SerialExecutor serialExecutor = new SerialExecutor();
        serialExecutor.execute(runnable1);
        serialExecutor.execute(runnable2);
        serialExecutor.execute(runnable3);

    }

    class SerialExecutor implements Executor {
        final Queue<Runnable> tasks = new ArrayDeque<>();
        Runnable active;

        @Override
        public synchronized void execute(final Runnable r) {
            Log.i(TAG, "execute  " + Thread.currentThread());

            tasks.add(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "tasks  " + Thread.currentThread());
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((active = tasks.poll()) != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(active);
            }
        }
    }

    private String currentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
    }

    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
}
