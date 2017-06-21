package com.example.myapplication.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class LineView extends View {
    int width;
    int height;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        init();
        setMeasuredDimension(width, height);
    }

    Paint paint;
    Paint mPaint;
    List<Point> mPoints = new ArrayList<>();
    Path mPath;

    public void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        // 防抖动
        paint.setDither(true);

        paint.setColor(Color.RED);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);

//        mPaint.setColor(Color.BLACK);

        int pointWidthSpace = width / 5;

        for (int i = 0; i < 6; i++) {
            Point point;
            // 一高一低五个点
            if (i % 2 != 0) {
                point = new Point(pointWidthSpace * i, height);
            } else {
                point = new Point(pointWidthSpace * i, 0);
            }
            mPoints.add(point);
        }
        mPath = new Path();

        initMidPoints(mPoints);
        initMidMidPoints(mMidPoints);
        initControlPoints(mPoints, mMidPoints, mMidMidPoints);
    }

    List<Point> mMidPoints = new ArrayList<>();
    List<Point> mMidMidPoints = new ArrayList<>();
    List<Point> mControlPoints = new ArrayList<>();

    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midMidPoints) {
        for (int i = 0; i < points.size(); i++) {
            if (i == 0 || i == points.size() - 1) {

            } else {
                Point before = new Point();
                Point after = new Point();
                before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
                before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
                after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
                after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
                mControlPoints.add(before);
                mControlPoints.add(after);
            }
        }
    }

    private void initMidPoints(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            Point midPoint = null;
            if (i == points.size() - 1) {
                return;
            } else {
                midPoint = new Point((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
            }
            mMidPoints.add(midPoint);
        }
    }

    /**
     * 初始化中点的中点集合
     */
    private void initMidMidPoints(List<Point> midPoints) {
        for (int i = 0; i < midPoints.size(); i++) {
            Point midMidPoint = null;
            if (i == midPoints.size() - 1) {
                return;
            } else {
                midMidPoint = new Point((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
            }
            mMidMidPoints.add(midMidPoint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画原始点
        drawPoints(canvas);
        // 画穿越原始点的折线
        drawCrossPointsBrokenLine(canvas);
        // 画中间点
        drawMidPoints(canvas);
        // 画中间点的中间点
        drawMidMidPoints(canvas);
        // 画控制点
        drawControlPoints(canvas);
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {// 第一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);
            } else if (i < mPoints.size() - 2) {// 三阶贝塞尔
                mPath.cubicTo(mControlPoints.get(2 * i - 1).x, mControlPoints.get(2 * i - 1).y,// 控制点
                        mControlPoints.get(2 * i).x, mControlPoints.get(2 * i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            } else if (i == mPoints.size() - 2) {// 最后一条为二阶贝塞尔
//                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(mControlPoints.size() - 1).x, mControlPoints.get(mControlPoints.size() - 1).y,
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            }
        }
        canvas.drawPath(mPath, paint);
        mPath.rewind();
    }
    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < mPoints.size(); i++) {
            canvas.drawPoint(mPoints.get(i).x, mPoints.get(i).y, mPaint);
        }
    }

    /** 画穿越原始点的折线 */
    private void drawCrossPointsBrokenLine(Canvas canvas) {

        mPaint.setColor(Color.RED);
        // 重置路径
        mPath.reset();
        // 画穿越原始点的折线
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        for (int i = 0; i < mPoints.size(); i++) {
            mPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /** 画中间点 */
    private void drawMidPoints(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < mMidPoints.size(); i++) {
            canvas.drawPoint(mMidPoints.get(i).x, mMidPoints.get(i).y, mPaint);
        }
    }

    /** 画中间点的中间点 */
    private void drawMidMidPoints(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        for (int i = 0; i < mMidMidPoints.size(); i++) {
            canvas.drawPoint(mMidMidPoints.get(i).x, mMidMidPoints.get(i).y, mPaint);
        }

    }

    /** 画控制点 */
    private void drawControlPoints(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        // 画控制点
        for (int i = 0; i < mControlPoints.size(); i++) {
            canvas.drawPoint(mControlPoints.get(i).x, mControlPoints.get(i).y, mPaint);
        }
    }
    private void setAnimation(float... values) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(values);
        progressAnimator.setDuration(2000);
//        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                curValues = (float) animation.getAnimatedValue();
//
//                currentAngle = sweepAngle * (curValues / 100);

                invalidate();
            }
        });
        progressAnimator.start();
    }
}
