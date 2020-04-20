package com.peopeltech.interviewpractice.android.image.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * @author hych
 * @since 2020/4/17 16:25
 */
@GlideExtension
public class MyGlideExtension {

    /**
     * Size of mini thumb in pixels.
     */
    private static final int MINI_THUMB_SIZE = 100;

    private MyGlideExtension() {

    }

    @GlideOption
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options) {
        return options
                .override(MINI_THUMB_SIZE);
    }

}