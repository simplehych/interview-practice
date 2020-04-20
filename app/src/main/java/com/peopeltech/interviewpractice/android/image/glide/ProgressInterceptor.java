package com.peopeltech.interviewpractice.android.image.glide;

import android.util.SparseArray;

import com.peopeltech.interviewpractice.android.image.lisenter.ProgressListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * @author hych
 * @since 2020/4/18 17:28
 */
public class ProgressInterceptor implements Interceptor {

    static final SparseArray<ProgressListener> LISTENER_MAP = new SparseArray<>();

    public static void addListener(String url, ProgressListener listener) {
        LISTENER_MAP.put(url.hashCode(), listener);
    }

    public static ProgressListener getListener(String url) {
        return LISTENER_MAP.get(url.hashCode());
    }

    public static void removeListener(String url) {
        LISTENER_MAP.remove(url.hashCode());
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        return response.newBuilder()
                .body(new ProgressResponseBody(url, body))
                .build();
    }

    static class ProgressResponseBody extends ResponseBody {

        private ResponseBody responseBody;
        private ProgressListener listener;

        private BufferedSource bufferedSource;

        public ProgressResponseBody(String url, ResponseBody body) {
            this.responseBody = body;
            this.listener = ProgressInterceptor.getListener(url);
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Nullable
        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @NotNull
        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(new ProgressSource(responseBody, listener));
            }
            return null;
        }
    }

    static class ProgressSource extends ForwardingSource {

        private ResponseBody responseBody;
        private ProgressListener listener;

        long totalBytesRead = 0;
        int currentProgress;

        public ProgressSource(ResponseBody responseBody, ProgressListener listener) {
            super(responseBody.source());
            this.responseBody = responseBody;
            this.listener = listener;
        }

        @Override
        public long read(@NotNull Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }

            int progress = (int) (100f * totalBytesRead / fullLength);
            if (listener != null && progress != currentProgress) {
                listener.onProgress(progress);
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener = null;
            }
            currentProgress = progress;
            return bytesRead;
        }
    }
}
