package com.example.myapplication.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by Administrator on 2017/3/13.
 */

public class TitleView extends RelativeLayout implements View.OnClickListener {

    private TextView mTitle;
    private ImageView mRightImg;
    private static final int defValue = -1;

    public TitleView(Context context) {
        this(context, null, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setRightImgClickListener(OnClickListener listener) {
        mRightImg.setOnClickListener(listener);
    }

    public TitleView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        boolean isBackBarVisibility = typeArray.getBoolean(R.styleable.TitleView_backBarVisibility, true);
        String title = typeArray.getString(R.styleable.TitleView_title);
        String rightTitle = typeArray.getString(R.styleable.TitleView_rightTitle);
        Drawable rightIcon = typeArray.getDrawable(R.styleable.TitleView_rightIcon);
        float width = typeArray.getDimension(R.styleable.TitleView_imgWidth, defValue);
        float height = typeArray.getDimension(R.styleable.TitleView_imgHeight, defValue);
        typeArray.recycle();
        setBackgroundColor(ContextCompat.getColor(context, R.color.color));
        View.inflate(context, R.layout.head_layout, this);
        ImageView back = (ImageView) getChildAt(0);
        mRightImg = (ImageView) getChildAt(3);
        mTitle = (TextView) getChildAt(1);
        TextView titleRight = (TextView) getChildAt(2);
//        setPadding(10, 10, 10, 10);
        back.setVisibility(isBackBarVisibility ? VISIBLE : GONE);
        if (rightIcon != null) {
            if (width != defValue && height != defValue) {
                ViewGroup.LayoutParams layoutParams = mRightImg.getLayoutParams();
                layoutParams.width = (int) width;
                layoutParams.height = (int) height;
                mRightImg.setLayoutParams(layoutParams);
            }
            mRightImg.setImageDrawable(rightIcon);
        } else {
            mRightImg.setVisibility(GONE);
        }
        if (rightTitle != null) {
            titleRight.setText(rightTitle);
        } else {
            titleRight.setVisibility(GONE);
        }
        mTitle.setText(title == null ? "" : title);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Activity) this.getContext()).finish();
//        ((Activity) this.getContext()).getFragmentManager().popBackStack(); //弹栈
    }

}
