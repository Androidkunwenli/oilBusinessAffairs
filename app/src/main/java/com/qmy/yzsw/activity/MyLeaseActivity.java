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
import com.qmy.yzsw.fragment.MyLeaseFragment;
import com.qmy.yzsw.fragment.MyTransferFragment;

import butterknife.BindView;

public class MyLeaseActivity extends BaseActivity {
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private static final String[] mTitle = {"出租", "转让"};
    private BaseFragment[] size;
    private int myself;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_lease;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        myself = getIntent().getIntExtra("myself", 1);
        size = new BaseFragment[]{new MyLeaseFragment().newInstance(myself), new MyTransferFragment().newInstance(myself)};
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("我的租赁");
    }

    public static void start(FragmentActivity activity, int myself) {
        Intent intent = new Intent(activity, MyLeaseActivity.class);
        intent.putExtra("myself", myself);
        activity.startActivity(intent);
    }
}
