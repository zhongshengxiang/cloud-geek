package com.example.myapplication.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.utils.SizeUtils;
import com.example.myapplication.R;

import cn.finalteam.toolsfinal.BitmapUtils;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ProgressView extends View {
    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int height;
    Paint paint;
    RectF bgRect;
    float currentAngle;
    float curValues;
    int sweepAngle = 240;
    private float progressWidth = SizeUtils.dp2px(10);
    private float longdegree = SizeUtils.dp2px(0);
    private final int DEGREE_PROGRESS_DISTANCE = SizeUtils.dp2px(10);
    int startAngle = 150;
    Paint progressPaint;
    Paint vTextPaint;
    Paint pointPaint;
    Bitmap bitmap;

    private float centerX;  //圆心X坐标
    private float centerY;  //圆心Y坐标

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(progressWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        int diameter = Math.min(width, height);
        //弧形的矩阵区域
        bgRect = new RectF();
        bgRect.top = progressWidth / 2 + DEGREE_PROGRESS_DISTANCE;
        bgRect.left = progressWidth / 2 + DEGREE_PROGRESS_DISTANCE;
        bgRect.right = diameter - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE;
        bgRect.bottom = diameter - progressWidth / 2;
        bitmap = BitmapUtils.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher));

        //当前进度的弧形
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(Color.GREEN);

        //内容显示文字
        vTextPaint = new Paint();
        vTextPaint.setTextSize(30);
        vTextPaint.setColor(Color.BLACK);
        vTextPaint.setTextAlign(Paint.Align.CENTER);

        //红色小球
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.RED);

        centerX = (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2;
        centerY = (2 * longdegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2;
        setAnimation(0, 60, 3000);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(bgRect, startAngle, sweepAngle, false, paint);

        canvas.drawArc(bgRect, startAngle, currentAngle, false, progressPaint);
        canvas.drawText(String.format("%.0f", curValues), bgRect.centerX(), bgRect.centerY(), vTextPaint);


        float ang = currentAngle;
        float x = (float) (centerX - Math.abs(centerX * Math.cos(ang)) + DEGREE_PROGRESS_DISTANCE);
        Log.i("okhttp", Math.cos(ang) + "   多少");
        float y = (float) (centerX - Math.abs(centerX * Math.sin(ang))) + DEGREE_PROGRESS_DISTANCE;
        Log.i("okhttp", "x=" + x + "  y=" + y + "  currentAngle=" + currentAngle);
        canvas.drawCircle(x, y, progressWidth / 2, pointPaint);
        //当前进度百分比

    }

    ValueAnimator progressAnimator;

    private void setAnimation(float last, final float current, int length) {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curValues = (float) animation.getAnimatedValue();
                Log.i("okhttp", "curValues=" + curValues);
                currentAngle = sweepAngle * (curValues / 100);
                if (mLister != null) {
                    mLister.ondegree(currentAngle);
                }
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void reset() {
        if (progressAnimator != null) {
            progressAnimator.cancel();
            progressAnimator.start();
        }
    }

    Lister mLister;

    public void setLister(Lister lister) {
        this.mLister = lister;
    }

    interface Lister {
        void ondegree(float progress);
    }
}
