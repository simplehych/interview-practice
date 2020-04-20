package com.peopeltech.interviewpractice.android.image.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author hych
 * @since 2020/4/18 17:13
 */
public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    public static final Option<Integer> TIMEOUT =
            Option.memory("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", 2500);

    private OkHttpClient okHttpClient;
    @Nullable
    private final ModelCache<GlideUrl, GlideUrl> modelCache;

    public OkHttpGlideUrlLoader(OkHttpClient okHttpClient, @Nullable ModelCache<GlideUrl, GlideUrl> modelCache) {
        this.okHttpClient = okHttpClient;
        this.modelCache = modelCache;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        // GlideUrls memoize parsed URLs so caching them saves a few object instantiations and time
        // spent parsing urls.
        GlideUrl url = model;
        if (modelCache != null) {
            url = modelCache.get(model, 0, 0);
            if (url == null) {
                modelCache.put(model, 0, 0, model);
                url = model;
            }
        }
        return new LoadData<>(url, new OkHttpFetcher(okHttpClient, model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private final ModelCache<GlideUrl, GlideUrl> modelCache = new ModelCache<>(500);
        private OkHttpClient client;

        public Factory() {
        }

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        private synchronized OkHttpClient getOkHttpClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpGlideUrlLoader(client, modelCache);
        }

        @Override
        public void teardown() {
        }
    }
}
