package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.widgets.GreenLayout;
import com.example.myapplication.widgets.MyView;
import com.example.myapplication.widgets.RedLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/9/13.
 */
public class SecondActivity extends Activity {
    @BindView(R.id.view) MyView mView;
    @BindView(R.id.green) GreenLayout mGreen;
    @BindView(R.id.red) RedLayout mRed;
    String tag="zhongshengxiang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        mView.setIlistener(new MyView.Ilistener() {
            @Override
            public void click() {
                Toast.makeText(SecondActivity.this, "灰色", Toast.LENGTH_SHORT).show();
                Log.i(tag,"灰色");
            }
        });

    }

    @OnClick({R.id.view, R.id.green, R.id.red})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view:
                Toast.makeText(this, "灰色", Toast.LENGTH_SHORT).show();
                Log.i(tag,"灰色");
                break;
            case R.id.green:
                Toast.makeText(this, "绿色", Toast.LENGTH_SHORT).show();
                Log.i(tag,"绿色");
                break;
            case R.id.red:
                Toast.makeText(this, "红色", Toast.LENGTH_SHORT).show();
                Log.i(tag,"红色");
                break;
        }
    }
}
