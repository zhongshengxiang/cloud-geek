package com.example.myapplication.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseActivity;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RoadView extends View {
    public RoadView(Context context) {
        super(context);
        init();
    }

    public RoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int width;
    int height;
    Paint paint;
    Bitmap bitmap;
    int offset = 0;
    private Rect src1;
    private Rect dst1;
    int last;

    public void setProgress(int progress) {
        this.offset = progress;
        invalidate(last,0,progress,height);
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.road);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        src1 = new Rect();
        dst1 = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // dst指的是canvas上的区域
        // src指的是bitmap上的区域

        dst1.set(0, 0, (int) (getWidth() * ((float) offset / 100)), height);
        src1.set(dst1);
        canvas.drawBitmap(bitmap, src1, dst1, paint);
        Log.i(BaseActivity.TAG, getWidth() * ((float) offset / 100) + "--");
        last = offset;
        canvas.restore();


    }
}
