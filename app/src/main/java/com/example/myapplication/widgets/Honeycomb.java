package com.example.myapplication.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/24.
 */

public class Honeycomb extends View {
    public Honeycomb(Context context) {
        this(context,null);
    }

    public Honeycomb(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Honeycomb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColorList = new ArrayList<>();

        mColorList.add(Color.parseColor("#33B5E5"));

        mColorList.add(Color.parseColor("#AA66CC"));

        mColorList.add(Color.parseColor("#99CC00"));

        mColorList.add(Color.parseColor("#FFBB33"));

        mColorList.add(Color.parseColor("#FF4444"));
        mTextList = new ArrayList<>(mLength);
        for (int i = 0; i < mLength; i++) {
            mTextList.add("第"+i);
        }
    }

    Path mPath;
    //每行的个数
    private int mColumnsCount = 3;
    int mLength = 30;
    //行数
    private int mLineCount = 3;
    private ArrayList<Integer> mColorList;
    private ArrayList<String> mTextList;

    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        Paint mTextPaint = new Paint();
        //正六边形的高
        float height = (float) (Math.sqrt(3) * mLength);
        for (int j = 0; j < mLineCount; j++) {

            mPaint.setColor(mColorList.get(j));

            if (j % 2 == 0) {

//                mPaint.setColor(Color.parseColor("#FFBB33"));

                for (int i = 0; i < mColumnsCount; i++) {
                    int txtId = j * 3 + i;
                    //横坐标偏移量
                    float offset = mLength * 3 * i;
                    //左上角的x
                    float x = mLength / 2 + offset;
                    float y = j * height / 2;


                    mPath.reset();
                    getPath(height, x, y);

                    canvas.drawPath(mPath, mPaint);
                    float txtLength = mTextPaint.measureText(mTextList.get(txtId));
                    canvas.drawText(mTextList.get(txtId), x + mLength / 2 - txtLength / 2, y + height / 2 + 5, mTextPaint);

                }
            } else {

//                mPaint.setColor(Color.parseColor("#AA66CC"));

                for (int i = 0; i < mColumnsCount; i++) {

                    int txtId = j * 3 + i;
                    float offset = mLength * 3 * i;
                    float x = mLength * 2 + offset;
                    float y = (height / 2) * j;
                    mPath.reset();
                    getPath(height, x, y);
                    canvas.drawPath(mPath, mPaint);
                    float txtLength = mTextPaint.measureText(mTextList.get(txtId));
                    canvas.drawText(mTextList.get(txtId), x + mLength / 2 - txtLength / 2, y + height / 2 + 5, mTextPaint);

                }


            }


        }


    }

    private void getPath(float height, float x, float y) {
        mPath.moveTo(x, y);
        mPath.lineTo(x - mLength / 2, height / 2 + y);
        mPath.lineTo(x, height + y);
        mPath.lineTo(x + mLength, height + y);
        mPath.lineTo((float) (x + 1.5 * mLength), height / 2 + y);
        mPath.lineTo(x + mLength, y);
        mPath.lineTo(x, y);
        mPath.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) ((3f * mColumnsCount + 0.5f) * mLength);
        } else {
//            throw new IllegalStateException("only support wrap_content");
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) ((mLineCount / 2f + 0.5f) * (Math.sqrt(3) * mLength));
        } else {

//            throw new IllegalStateException("only support wrap_content");
        }


        setMeasuredDimension(widthSize, heightSize);


    }
}
