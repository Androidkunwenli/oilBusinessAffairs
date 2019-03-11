package com.qmy.yzsw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.TabAdapter;
import com.qmy.yzsw.fragment.MySubmissionReportFormFragment;

import butterknife.BindView;

public class MySubmissionReportFormActivity extends BaseActivity {
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private static final String[] mTitle = {"我的下载", "我的提交"};
    private BaseFragment[] size;
    private MyReportFormAdmFragment myReportFormAdmFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_submission_report_form;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        myReportFormAdmFragment = new MyReportFormAdmFragment();
        size = new BaseFragment[]{myReportFormAdmFragment, new MySubmissionReportFormFragment()};
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("我的报表");
        setimageRightText(R.mipmap.ic_from_edit);
        mImageRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmissionReportFormActivity.start(mActivity);
            }
        });
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MySubmissionReportFormActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (myReportFormAdmFragment != null) {
            myReportFormAdmFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
