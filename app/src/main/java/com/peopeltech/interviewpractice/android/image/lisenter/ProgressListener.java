package com.peopeltech.interviewpractice.android.image.lisenter;

/**
 * @author hych
 * @since 2020/4/18 17:32
 */
public interface ProgressListener {

    /**
     * 进度
     *
     * @param progress
     */
    void onProgress(int progress);

    /**
     * 完成
     */
    default void onComplete() {

    }

    /**
     * 失败
     */
    default void onFail() {

    }
}
