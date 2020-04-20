package com.peopeltech.interviewpractice.android.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.peopeltech.interviewpractice.R;
import com.peopeltech.interviewpractice.util.Util;

import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * @author hych
 * @since 2020/4/16 15:53
 */
public class GlideActivity extends Activity {

    //    String url = "https://wx2.sinaimg.cn/mw690/7d4d7a53gy1fixfomge82j20bfcmgwvz.jpg";
    String url = "http://guolin.tech/book.png";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_glide);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void loadImage(View view) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .override(Target.SIZE_ORIGINAL) // 设置显示大小
                .skipMemoryCache(false) // 设置内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 设置磁盘缓存

        Glide.with(this)
                .asDrawable() // asBitmap() / asGif() / asFile()
                .load(url) // load(new GlideUrl(url)) / load(file) / load(bitmap) / load(drawable)
                .apply(requestOptions)
                .into(imageView);

        SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bm, Transition transition) {
                imageView.setImageBitmap(bm);
            }
        };

        Glide.with(this)
                .load(url)
                .preload(); // 仅仅预加载，不显示

        Glide.with(this)
                .asDrawable()
                .load(url)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void testSubmit(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> submit = Glide.with(Util.getAppContext())
                        .asFile()
                        .load(url)
                        //.preload();
                        .submit();
                try {
                    File file = submit.get();
                    Util.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Util.toastShowLong(file.getPath());
                        }
                    });
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void testListener(View view) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return true; // true 表拦截
                    }
                })
                .into(imageView);
    }

    public void testTransforms(View view) {
        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop());

        Glide.with(this)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void testGlideModule(View view) {
        GlideApp.with(this)
                .load(url)
                .transform(new BlurTransformation())
                .miniThumb()
                .into(imageView);
    }

}
