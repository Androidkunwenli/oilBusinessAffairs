package com.qmy.yzsw.activity;

import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.SPUtils;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.youth.xframe.utils.statusbar.XStatusBar;

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        XStatusBar.setTranslucent(this);
    }

    @Override
    public void initView() {
        String token = SPUtils.getInstance().getString("token");
        if (token != null && !token.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.start(mActivity);
                    finish();
                }
            }, 0);
        } else {
            LoginActivity.start(mActivity);
            finish();
        }
    }
}
