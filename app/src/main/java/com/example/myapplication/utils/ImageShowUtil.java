package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.myapplication.R;

public class ImageShowUtil {
    private static final int loadingResID = R.drawable.ic_launcher;
    private static final int errorResID = R.drawable.ic_launcher;

    public static void show(Context context, ImageView imageView, String imgurl) {
        Glide.with(context)
                .load(imgurl)
                .placeholder(R.drawable.yx)//加载中的默认图片
                .error(R.drawable.ic_launcher)
                .crossFade()//图片的淡入淡出动画效果
                .dontAnimate()
                .into(imageView);
    }

    public static void showwithoutAni(Context context, ImageView imageView, String imgurl) {
        Glide.with(context)
                .load(imgurl)
                .placeholder(loadingResID)
                .error(errorResID)
                .dontAnimate()
                .into(imageView);
    }

    public static void show(Context context, ImageView imageView, int ResId, int loadingResID, int errorResID) {
        Glide.with(context)
                .load(ResId)
                .placeholder(loadingResID)
                .error(errorResID)
                .crossFade()
                .into(imageView);
    }

    public static void showRoundImage(final Context context, final ImageView imageView, String url, int loadingResID, int errorResID) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void showRoundImageWithTint(final Context context, final ImageView imageView, String url, int loadingResID, int errorResID, final int tintColorID) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        Drawable tintIcon = DrawableCompat.wrap(circularBitmapDrawable);
                        DrawableCompat.setTintList(tintIcon, context.getResources().getColorStateList(tintColorID));
                        imageView.setImageDrawable(tintIcon);
                    }
                });
    }

    public static void showRoundImage(final Context context, final ImageView imageView, int resID) {
        Glide.with(context)
                .load(resID)
                .asBitmap()
                .centerCrop()
                .placeholder(loadingResID)
                .error(errorResID)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void showGifImage(Context context, ImageView imageView, int ResID) {
        Glide
                .with(context)
                .load(ResID)
                .asGif()
                .error(errorResID)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
