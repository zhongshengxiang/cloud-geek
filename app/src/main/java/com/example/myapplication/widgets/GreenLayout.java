package com.example.myapplication.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by user on 2016/9/13.
 */
public class GreenLayout extends ImageView {
    String tag = "zhongshengxiang";

    public GreenLayout(Context context) {
        this(context, null);
    }

    public GreenLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Path mPath = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, Path.Direction.CCW);


        canvas.clipPath(mPath);

    }
}
