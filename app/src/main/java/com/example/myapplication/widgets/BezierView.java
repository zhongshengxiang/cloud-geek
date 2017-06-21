package com.example.myapplication.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/6/15.
 */

public class BezierView extends View {
    private Path mPath;
    private Point startPoint;
    private Point endPoint;
    // 辅助点
    private Point assistPoint;
    private Point mCurrentAssist;

    public BezierView(Context context) {
        this(context, null);
        init();
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        startPoint = new Point(0, height / 2);
        endPoint = new Point(width + 1000, height / 2);
        assistPoint = new Point(width / 2, height / 2);
        setMeasuredDimension(width, height);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;
    int width;
    int height;
    Point endAssist;
    boolean isRelease;

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        endAssist = new Point();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        // 笔宽
        mPaint.setStrokeWidth(3);
        // 空心
        mPaint.setStyle(Paint.Style.STROKE);
        mCurrentAssist = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 重置路径
        mPath.reset();
        if (isRelease) {
            // 起点
            mPath.moveTo(startPoint.x, startPoint.y);
            // 重要的就是这句
            mPath.quadTo(mCurrentAssist.x, mCurrentAssist.y, endPoint.x, endPoint.y);
            // 画路径
            canvas.drawPath(mPath, mPaint);
            mPath.rewind();
            // 画辅助点
            canvas.drawPoint(mCurrentAssist.x, mCurrentAssist.y, mPaint);
        } else {
            // 起点
            mPath.moveTo(startPoint.x, startPoint.y);
            // 重要的就是这句
            mPath.quadTo(assistPoint.x, assistPoint.y, endPoint.x, endPoint.y);
            // 画路径
            canvas.drawPath(mPath, mPaint);
            mPath.rewind();
            // 画辅助点
            canvas.drawPoint(assistPoint.x, assistPoint.y, mPaint);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isRelease = false;
                break;
            case MotionEvent.ACTION_MOVE:
                assistPoint.x = (int) event.getX();
                assistPoint.y = (int) event.getY() * 3 / 2;
//                Log.i(TAG, "assistPoint.x = " + assistPoint.x);
//                Log.i(TAG, "assistPoint.Y = " + assistPoint.y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                endAssist.x = (int) event.getX();
                float y = event.getY() * 3 / 2;
                if (y < height / 2) {
                    endAssist.y = (int) (y + (height / 2 - y) * 2);
                } else {
                    endAssist.y = (int) (y - (y - height / 2) * 2);
                }
                mCurrentAssist.x = endAssist.x;
                isRelease = true;
                setAnimation(assistPoint.y, endAssist.y, 8000);
                break;
        }
        return true;
    }

    private void setAnimation(float start, float end, int length) {
        int size = 40;
        float v[] = new float[size + 1];
        float s = start;
        float e = end;
        for (int i = 0; i < size; i += 2) {
            if (s >= height / 2 || e <= height / 2) {
                s -= 15;
                e -= 15;
                v[i] = s;
                v[i + 1] = e;
            }
        }
        v[size] = height / 2;
        final ValueAnimator progressAnimator = ValueAnimator.ofFloat(v);
        progressAnimator.setDuration(length);
////        final CycleInterpolator interpolator = new CycleInterpolator(-10f);
////        progressAnimator.setInterpolator(interpolator);
//        progressAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                progressAnimator.ge
//                progressAnimator.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentAssist.y = (int) (float) animation.getAnimatedValue();


                invalidate();
            }
        });
        progressAnimator.start();
    }
}