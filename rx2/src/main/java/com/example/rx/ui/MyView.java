package com.example.rx.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.rx.utils.Toaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        Point point = new Point(100, 100);
        paint.setStyle(Paint.Style.STROKE);
        int xoff = 20;
        paint.setColor(Color.BLUE);
        for (int i = 0; i < 4; i++) {
            Path path = new Path();
            path.addCircle(point.x + i * (100 + xoff), 100, 50, Path.Direction.CCW);
            canvas.drawPath(path, paint);
            Region region = new Region();
            region.setPath(path, new Region(0, 0, 1000, 1000));
            list.add(region);
        }
    }

    List<Region> list = new ArrayList<>();

    private void init() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains((int) event.getX(), (int) event.getY())) {
                        Toaster.show(i + "");
                    }
                }
                break;
        }
        return true;
    }
}
