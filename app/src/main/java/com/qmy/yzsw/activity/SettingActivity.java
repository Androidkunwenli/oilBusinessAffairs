package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.ll_change_pass)
    LinearLayout llChangePass;
    @BindView(R.id.ll_opinion)
    LinearLayout llOpinion;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("设置");
        mTvVersion.setText("V " + AppUtils.getAppVersionName());
    }

    @Override
    public void initView() {

    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, SettingActivity.class));
    }

    @OnClick({R.id.ll_change_pass, R.id.ll_opinion, R.id.ll_help, R.id.ll_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_pass:
                ChangePasswordActivity.start(mActivity);
                break;
            case R.id.ll_opinion:
                FeedbackActivity.start(mActivity);
                break;
            case R.id.ll_help:
//                ToastUtils.showShort("敬请期待!");
                HelpActivity.start(mActivity);
                break;
            case R.id.ll_version:
                ToastUtils.showShort("当前已是最新版本");
                break;
        }
    }
}
