package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.TabAdapter;
import com.qmy.yzsw.fragment.LeaseFragment;
import com.qmy.yzsw.fragment.TransferFragment;

import butterknife.BindView;

public class LeaseActivity extends BaseActivity {
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private static final String[] mTitle = {"租赁", "转让"};
    private static final BaseFragment[] size = new BaseFragment[]{new LeaseFragment(), new TransferFragment()};

    @Override
    public int getLayoutId() {
        return R.layout.activity_lease;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("租赁信息");
        setTvRightText("发布");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaseReleaseActivity.start(mActivity, null,null);
            }
        });
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, LeaseActivity.class));
    }
}
