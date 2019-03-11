package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.TabAdapter;
import com.qmy.yzsw.fragment.FindCarFragment;
import com.qmy.yzsw.fragment.PullTheGoodsFragment;
import com.qmy.yzsw.view.ViewPagerSlide;

import butterknife.BindView;

public class TransportActivity extends BaseActivity {

    @BindView(R.id.fl_layout)
    FrameLayout fl_layout;
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPagerSlide vpView;
    private static final String[] mTitle = {"我要拉货", "我要找车"};
    private static BaseFragment[] size;
    private int myself;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transport;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("危货运输");
        setTvRightText("发布");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransportReleaseActivity.start(mActivity, 0, null,null);
            }
        });

        myself = getIntent().getIntExtra("myself", 0);
        size = new BaseFragment[]{new PullTheGoodsFragment().newInstance(myself), new FindCarFragment().newInstance(myself)};
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);

        int index = getIntent().getIntExtra("index", 0);
        vpView.setCurrentItem(index);
        boolean isShow = getIntent().getBooleanExtra("isShow", false);
        fl_layout.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mRightKey.setVisibility(isShow ? View.GONE : View.VISIBLE);
        if (isShow) {
            switch (index) {
                case 0:
                    setTitleStr("拉货管理");
                    break;
                case 1:
                    setTitleStr("我的找车");
                    break;
            }
        }
    }

    @Override
    public void initView() {

    }

    public static void start(FragmentActivity activity, int myself) {
        Intent intent = new Intent(activity, TransportActivity.class);
        intent.putExtra("myself", myself);
        activity.startActivity(intent);
    }

    public static void start(FragmentActivity activity, int myself, int index, boolean isShow) {
        Intent intent = new Intent(activity, TransportActivity.class);
        intent.putExtra("myself", myself);
        intent.putExtra("index", index);
        intent.putExtra("isShow", isShow);
        activity.startActivity(intent);
    }
}
