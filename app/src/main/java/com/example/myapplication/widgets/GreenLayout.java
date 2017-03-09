package com.example.myapplication.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by user on 2016/9/13.
 */
public class GreenLayout extends RelativeLayout {
    Context mContext;
    String tag = "zhongshengxiang";

    public GreenLayout(Context context) {
        this(context, null);
    }

    public GreenLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(tag, "绿色按下");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(tag, "绿色移动");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(tag, "绿色抬起");
//                        break;
//                }
//                return false;
//            }
//        });
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(mContext, "绿色2", Toast.LENGTH_SHORT).show();
//        Log.i(tag, "绿色2");
//        return false;
//    }
}
