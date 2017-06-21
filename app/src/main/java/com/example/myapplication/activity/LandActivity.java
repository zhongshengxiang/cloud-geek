package com.example.myapplication.activity;

import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dl7.player.media.IjkPlayerView;
import com.example.myapplication.R;
import com.kennyc.view.MultiStateView;

import butterknife.BindView;

public class LandActivity extends BaseActivity {


    @BindView(R.id.player) IjkPlayerView mPlayerView;
    @BindView(R.id.multiStateView) MultiStateView mMultiStateView;
    @BindView(R.id.yellow) FrameLayout mYellow;
    private static final String VIDEO_URL = "http://118.26.161.35:31000/streaming/mc1/M00/00/64/ZQAAAFknjc6AH-hLBHL6i13pWtY440.mp4";

    @Override
    public int getLayoutID() {
        return R.layout.activity_land;
    }

    @Override
    public void initView() {
        mPlayerView.init()              // Initialize, the first to use
                .setTitle("Title")    // set title
                .setSkipTip(1000 * 60 * 1)  // set the position you want to skip
                .enableOrientation()    // enable orientation
                .setVideoPath(VIDEO_URL)    // set video url
//                .setVideoSource(null, VIDEO_URL, VIDEO_URL, VIDEO_URL, null) // set multiple video url
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)  // set the initial video url
                .enableDanmaku()        // enable Danmaku
//                .setDanmakuSource(getResources().openRawResource(R.raw.comments)) // add Danmaku source, you need to use enableDanmaku() first
                .start();
        final GestureDetector gestureDetector = new GestureDetector(thisActivity, new MyGestureDetector());
        mYellow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            log("onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            log("onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            log("onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            log("onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            log("onContextClick");
            return super.onContextClick(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            log("onLongPress");
            super.onLongPress(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            log("onShowPress");
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            log("onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            log("onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            log("onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        mPlayerView.onDestroy();
        super.onDestroy();
    }

}
