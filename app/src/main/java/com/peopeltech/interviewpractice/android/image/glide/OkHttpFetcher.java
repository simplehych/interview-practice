package com.peopeltech.interviewpractice.android.image.glide;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.LogTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author hych
 * @since 2020/4/18 16:52
 */
public class OkHttpFetcher implements DataFetcher<InputStream> {

    private OkHttpClient client;
    private final GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;
    private volatile boolean isCancelled;

    public OkHttpFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(@NonNull Priority priority,
                         @NonNull DataCallback<? super InputStream> callback) {
        try {
            InputStream result = loadDataWithRedirects();
            callback.onDataReady(result);
        } catch (IOException e) {
            callback.onLoadFailed(e);
        }
    }

    private InputStream loadDataWithRedirects() throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url.toStringUrl());

        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }

        Request request = requestBuilder.build();
        if (isCancelled) {
            return null;
        }

        Response response = client.newCall(request).execute();
        responseBody = response.body();

        if (!response.isSuccessful() || responseBody == null) {
            throw new IOException("Request failed with code: " + response.code());
        }

        stream = ContentLengthInputStream.obtain(responseBody.byteStream(),
                responseBody.contentLength());
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (responseBody != null) {
            responseBody.close();
        }
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
