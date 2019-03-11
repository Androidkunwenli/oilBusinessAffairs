package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.youth.xframe.widget.loadingview.XLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SupplyActivity extends BaseActivity {
    @BindView(R.id.x_layout)
    XLoadingView xLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_supply;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("现货供应");
        xLayout.showEmpty();
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, SupplyActivity.class));
    }

}
