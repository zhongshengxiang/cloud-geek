package com.example.myapplication.widgets;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/1/19.
 */

public class DragLayout extends FrameLayout {
    Context context;
    ViewDragHelper helper;
    RelativeLayout top;
    RelativeLayout button;
    private Point mAutoBackOriginPos = new Point();

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        helper = ViewDragHelper.create(this, 0.5f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == button;
            }

            @Override
            public int clampViewPositionVertical(View child, int top1, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = top.getBottom();
                final int newTop = Math.min(Math.max(top1, topBound), bottomBound);
                if (dy > bottomBound) {
                    return bottomBound;
                }
                float v = (float) button.getTop() / bottomBound;
                Log.i("okhttp", "" + v);

                top.setAlpha(v + 0.3f);
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == button) {

                    if (button.getTop() < 100) {
//                        if (helper.smoothSlideViewTo(button,0,top.getTop())){
//                        };
                        ViewCompat.postInvalidateOnAnimation(button,0, top.getTop(), button.getRight(), mAutoBackOriginPos.y);

//                        button.layout(0, top.getTop(), button.getRight(), mAutoBackOriginPos.y);
                    }
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mAutoBackOriginPos.x = button.getLeft();
        mAutoBackOriginPos.y = button.getBottom();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return helper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        top = (RelativeLayout) getChildAt(0);
        button = (RelativeLayout) getChildAt(1);
    }
}
