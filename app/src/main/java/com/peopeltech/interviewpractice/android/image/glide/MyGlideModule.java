package com.peopeltech.interviewpractice.android.image.glide;

import android.content.Context;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author hych
 * @since 2020/4/17 16:14
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {
    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder
                .setBitmapPool(new BitmapPoolAdapter())
                .setMemoryCache(new LruResourceCache(1024))
                .setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, DISK_CACHE_SIZE))
                .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
                .setSourceExecutor(GlideExecutor.newSourceExecutor())
                .setDefaultRequestOptions(new Glide.RequestOptionsFactory() {
                    @NonNull
                    @Override
                    public RequestOptions build() {
                        return new RequestOptions().format(DecodeFormat.PREFER_RGB_565);
                    }
                });
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();

        registry.replace(GlideUrl.class, InputStream.class,
                new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }

    @CheckResult
    public int check() {
        return 0;
    }
}
