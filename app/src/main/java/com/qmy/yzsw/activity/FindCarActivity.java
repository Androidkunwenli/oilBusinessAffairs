package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;

public class FindCarActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_find_car;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("我要找车");
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, FindCarActivity.class));
    }
}
