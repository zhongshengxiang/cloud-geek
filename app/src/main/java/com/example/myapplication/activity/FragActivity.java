package com.example.myapplication.activity;

import android.support.v4.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fargment.Fragment1;

/**
 * Created by Administrator on 2017/3/15.
 */

public class FragActivity extends BaseActivity {
    @Override
    public int getLayoutID() {
        return R.layout.activity_frag;
    }

    @Override
    public void initView() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container, new Fragment1()).addToBackStack(null);
        transaction.commit();
    }
}
