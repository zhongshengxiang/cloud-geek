package com.example.myapplication.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

/**
 * Created by user on 2016/9/13.
 */
public class MyFloatView implements View.OnClickListener {

    private static MyFloatView instance = null;
    private static Context mContext;
    private ImageView iv_gift_selected;
    private ImageView iv_leida_selected;
    private ImageView iv_msg_selected;
    private ImageView iv_question_selected;
    private ImageView iv_user_selected;
    private static boolean isBig = true;
    private static boolean isRight = false;
    private int statusBarHeight;
    private FrameLayout fl_leida;
    private FrameLayout fl_gift;
    private FrameLayout fl_msg;
    private FrameLayout fl_question;
    private FrameLayout fl_user;

    private FrameLayout[] FLS;
    private float angle = 0;// 旋转的角度
    private static WindowManager.LayoutParams wmParams;
    private static Handler handler = new Handler();
    private static int time;
    private MyRunnable runnable = MyRunnable.bulid();
    // 创建浮动窗口设置布局参数的对象
    private static WindowManager mWindowManager;
    private static RelativeLayout mFloatLayout;
    private int mWidth;// 自身的宽度
    private int width;// 屏幕的宽度
    class MyListener implements Animator.AnimatorListener {
        String mcontent;

        public void setContent(String content) {
            mcontent = content;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            Toast.makeText(mContext,mcontent,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    private MyFloatView(Context context) {
        init(context.getApplicationContext());
    }

    protected void init(Context context) {
        this.mContext = context;
        createFloatView();
    }

    public synchronized static MyFloatView getInstance(Context context) {
        if (instance == null) {
            instance = new MyFloatView(context);
        }
        return instance;
    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // 获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();// 屏幕的宽度
        // 设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置window type
        wmParams.type = getAPIVersion() >= 19 ? WindowManager.LayoutParams.TYPE_TOAST : WindowManager.LayoutParams.TYPE_PHONE;

        // 调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mFloatLayout = (RelativeLayout) View.inflate(mContext, R.layout.sdk_suspensionmenu_main, null);

        Rect frame = new Rect();
        mFloatLayout.getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        // 添加mFloatLayout

        mWindowManager.addView(mFloatLayout, wmParams);
        init();
    }

    private int getAPIVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }

    private void init() {

        iv_gift_selected = (ImageView) mFloatLayout.findViewById(R.id.iv_gift_selected);
        iv_leida_selected = (ImageView) mFloatLayout.findViewById(R.id.iv_leida_selected);
        iv_msg_selected = (ImageView) mFloatLayout.findViewById(R.id.iv_msg_selected);
        iv_center_inner = (ImageView) mFloatLayout.findViewById(R.id.iv_center_inner);
        iv_center_inner2 = (ImageView) mFloatLayout.findViewById(R.id.iv_center_inner2);
        iv_left_inner = (ImageView) mFloatLayout.findViewById(R.id.iv_left_inner);
        iv_right_inner = (ImageView) mFloatLayout.findViewById(R.id.iv_right_inner);
        iv_question_selected = (ImageView) mFloatLayout.findViewById(R.id.iv_question_selected);
        fl_inner = (FrameLayout) mFloatLayout.findViewById(R.id.fl_inner);
        tv_hint = (TextView) mFloatLayout.findViewById(R.id.tv_hint);
        iv_user_selected = (ImageView) mFloatLayout.findViewById(R.id.iv_user_selected);

        fl_leida = (FrameLayout) mFloatLayout.findViewById(R.id.fl_leida);
        fl_gift = (FrameLayout) mFloatLayout.findViewById(R.id.fl_gift);
        fl_msg = (FrameLayout) mFloatLayout.findViewById(R.id.fl_msg);
        fl_question = (FrameLayout) mFloatLayout.findViewById(R.id.fl_question);
        fl_user = (FrameLayout) mFloatLayout.findViewById(R.id.fl_user);
        rl_fl = (RelativeLayout) mFloatLayout.findViewById(R.id.rl_fl);
        FLS = new FrameLayout[]{fl_gift, fl_msg, fl_user, fl_question, fl_leida};
        iVS = new ImageView[]{iv_gift_selected, iv_msg_selected, iv_user_selected, iv_question_selected, iv_leida_selected};
        fl_inner.setOnTouchListener(otl);
        fl_msg.setOnClickListener(this);
        fl_question.setOnClickListener(this);
        fl_user.setOnClickListener(this);
        fl_leida.setOnClickListener(this);
        fl_gift.setOnClickListener(this);
        scale(true);
        hide();
    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        int startx = 0;
        int starty = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            handler.removeCallbacks(runnable);
            time = 0;
            mWidth = mFloatLayout.getMeasuredWidth();// 自己的宽度
            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isBig) {
                        iv_center_inner2.setVisibility(View.VISIBLE);
                        iv_center_inner.setVisibility(View.GONE);
                        iv_left_inner.setVisibility(View.GONE);
                        iv_right_inner.setVisibility(View.GONE);
                    }
                    startx = (int) event.getRawX();
                    starty = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    wmParams.x = (int) event.getRawX() - mWidth * 2 / 3;
                    // 减statusBarHeight为状态栏的高度
                    wmParams.y = (int) event.getRawY() - mFloatLayout.getMeasuredHeight() * 2 / 3 - statusBarHeight;
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                    break;
                case MotionEvent.ACTION_UP:
                    int endx = (int) event.getRawX();
                    int endy = (int) event.getRawY();
                    int dx = endx - startx;
                    int dy = endy - starty;
                    if (dx <= 5 && dy <= 5) {
                        scale(isBig);
                    }
                    if (wmParams.x + mWidth / 2 < width / 2) {
                        wmParams.x = -90;
                        isRight = false;
                    } else {
                        wmParams.x = width;
                        isRight = true;
                    }
                    hide();
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);

                    break;

                default:
                    break;
            }

            return true; // 此处必须返回false，否则OnClickListener获取不到监听
        }

    };

    private void hide() {

        handler.postDelayed(runnable, 1000);
    }

    private static FrameLayout fl_inner;
    private ImageView[] iVS;
    private TextView tv_hint;
    private static ImageView iv_center_inner;
    private static ImageView iv_center_inner2;
    private RelativeLayout rl_fl;
    private static ImageView iv_left_inner;
    private static ImageView iv_right_inner;

    private static class MyRunnable implements Runnable {
        static MyRunnable myRunnable = null;

        private MyRunnable() {
            super();
        }

        public synchronized static MyRunnable bulid() {
            if (myRunnable == null) {
                myRunnable = new MyRunnable();
            }
            return myRunnable;
        }

        @Override
        public void run() {
            // handler.removeCallbacks(this);
            time += 1000;
            if (time >= 3000) {
                handler.removeCallbacks(this);
                if (!isBig) {
                    toTransparency();
                }
                return;
            }
            handler.postDelayed(this, 1000);

        }

        // 改变透明度
        private void toTransparency() {

            iv_center_inner.setVisibility(View.GONE);

            iv_center_inner2.setVisibility(View.GONE);
            if (isRight) {
                iv_left_inner.setVisibility(View.VISIBLE);
            } else {
                iv_right_inner.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator oaY;
        MyListener listener=new MyListener();
        if (v.getId() == fl_msg.getId()) {
            initSelected(iv_msg_selected);
            Float toDegrees1 = Math.abs(-72f - angle) < Math.abs(288f - angle) ? -72f : 288f;
            oaY = ObjectAnimator.ofFloat(iv_center_inner, "rotation", angle, toDegrees1);

            oaY.setDuration(200);

            oaY.start();
            tv_hint.setText("消息");
            listener.setContent("消息");
            oaY.addListener(listener);
            angle = toDegrees1;
        } else if (v.getId() == fl_user.getId()) {

            initSelected(iv_user_selected);
            Float toDegrees2 = Math.abs(-144f - angle) < Math.abs(216f - angle) ? -144f : 216f;
            oaY = ObjectAnimator.ofFloat(iv_center_inner, "rotation", angle, toDegrees2);

            oaY.setDuration(200);

            oaY.start();
            listener.setContent("账号");
            oaY.addListener(listener);
            tv_hint.setText("账号");

            angle = toDegrees2;

        } else if (v.getId() == fl_question.getId()) {
            initSelected(iv_question_selected);
            Float toDegrees3 = Math.abs(-216f - angle) < Math.abs(144f - angle) ? -216f : 144f;
            oaY = ObjectAnimator.ofFloat(iv_center_inner, "rotation", angle, toDegrees3);

            oaY.setDuration(200);

            oaY.start();
            tv_hint.setText("帮助");
            listener.setContent("帮助");
            oaY.addListener(listener);
            angle = toDegrees3;

        } else if (v.getId() == fl_leida.getId()) {
            initSelected(iv_leida_selected);
            Float toDegrees4 = Math.abs(-288f - angle) < Math.abs(72f - angle) ? -288f : 72f;
            oaY = ObjectAnimator.ofFloat(iv_center_inner, "rotation", angle, toDegrees4);

            oaY.setDuration(200);

            oaY.start();

            tv_hint.setText("推广");

            angle = toDegrees4;
        } else if (v.getId() == fl_gift.getId()) {
            initSelected(iv_gift_selected);
            Float toDegrees0 = Math.abs(-360f - angle) < Math.abs(0.0f - angle) ? -360f : 0.0f;
            oaY = ObjectAnimator.ofFloat(iv_center_inner, "rotation", angle, toDegrees0);

            oaY.setDuration(200);

            oaY.start();
            tv_hint.setText("礼包");

            angle = 0.0f;
        }

    }

    /*
     * 控制被选中按钮的圈圈
     */
    private void initSelected(ImageView iv) {
        for (int i = 0; i < iVS.length; i++) {
            iVS[i].setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
        }
    }

    /*
     *
     * 执行菜单的展开和闭合
     */
    private void scale(boolean b) {

        if (b) {
            iv_center_inner2.setVisibility(View.VISIBLE);
            iv_center_inner.setVisibility(View.GONE);
            rl_fl.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
            for (int i = 0; i < FLS.length; i++) {
                FLS[i].setVisibility(View.GONE);
            }
            ObjectAnimator.ofFloat(mFloatLayout, "scale", 1.0F, 0.0F).setDuration(300).start();
            isBig = false;
        } else {
            iv_center_inner2.setVisibility(View.GONE);
            iv_center_inner.setVisibility(View.VISIBLE);
            rl_fl.setVisibility(View.VISIBLE);
            tv_hint.setVisibility(View.VISIBLE);
            for (int i = 0; i < FLS.length; i++) {
                FLS[i].setVisibility(View.VISIBLE);
            }
            ObjectAnimator.ofFloat(mFloatLayout, "scale", 0.0F, 1.0F).setDuration(300).start();
            isBig = true;
        }
    }


    // 隐藏悬浮窗口
    public static void hidFloat() {
        if (null != mFloatLayout) {
            mFloatLayout.setVisibility(View.GONE);
        }
    }

    // 移除悬浮窗口
    public static void removeFloat() {
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
            isRight = false;
            instance = null;
        }
    }

    // 显示悬浮窗口
    public static void ShowFloat() {
        if (null != mFloatLayout) {
            mFloatLayout.setVisibility(View.VISIBLE);
        }
    }

    // 显示或者隐藏悬浮窗口
    public static void showOrHideFloat() {
        if (null != mFloatLayout) {
            if (mFloatLayout.getVisibility() == View.VISIBLE) {
                mFloatLayout.setVisibility(View.GONE);
            } else if (mFloatLayout.getVisibility() == View.GONE) {
                mFloatLayout.setVisibility(View.VISIBLE);
            }
        }
    }


}
