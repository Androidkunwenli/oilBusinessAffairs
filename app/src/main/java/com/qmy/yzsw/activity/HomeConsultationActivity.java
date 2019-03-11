package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.TabAdapter;
import com.qmy.yzsw.fragment.GovernmentFragment;
import com.qmy.yzsw.fragment.IndustryFragment;

import butterknife.BindView;

public class HomeConsultationActivity extends BaseActivity {
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private static final String[] mTitle = {"政府简报", "行业资讯"};
    private static final BaseFragment[] size = new BaseFragment[]{new GovernmentFragment(), new IndustryFragment()};

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_consultation;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("今日资讯");
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, HomeConsultationActivity.class));
    }
}
