package com.example.myapplication.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by user on 2016/9/13.
 */
public class MyView extends View {
    String tag = "zhongshengxiang";
    Ilistener mIlistener;

    public void setIlistener(Ilistener ilistener) {
        mIlistener = ilistener;
    }

    Context mContext;

    public MyView(Context context) {
        this(context, null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(tag, "灰色");
//            }
//        });
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(tag, "按下");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(tag, "移动");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(tag, "抬起");
//                        break;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(tag, "按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(tag, "移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(tag, "抬起");
                performClick();
//                if (mIlistener!=null){
//                    mIlistener.click();
//                }
                break;
        }
        return true;
    }
    public interface Ilistener{
        void click();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
