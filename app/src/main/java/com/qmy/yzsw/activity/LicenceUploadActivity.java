package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.luck.picture.lib.config.PictureConfig;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.TabAdapter;
import com.qmy.yzsw.bean.OnClickBean;
import com.qmy.yzsw.fragment.CertificatesPhotoFragment;
import com.qmy.yzsw.fragment.ScenePhotoFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class LicenceUploadActivity extends BaseActivity {
    @BindView(R.id.tab_view)
    SlidingTabLayout tabView;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private static final String[] mTitle = {"证件照片", "场景照片"};
    private static final BaseFragment[] size = new BaseFragment[]{new CertificatesPhotoFragment(), new ScenePhotoFragment()};

    @Override
    public int getLayoutId() {
        return R.layout.activity_licence_upload;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        vpView.setAdapter(new TabAdapter(mActivity, getSupportFragmentManager(), mTitle, size));
        tabView.setViewPager(vpView);
        vpView.setOffscreenPageLimit(0);
    }

    @Override
    public void initView() {
        switch (getIntent().getIntExtra("type", 0)) {
            case 1:
                setTitleStr("油站证照");
                break;
            default:
                setTitleStr("证照上传");
                break;
        }
        setFindViewById(true);
        setTitleStr("证照上传");
        setTvRightText("保存");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OnClickBean());
            }
        });
    }

    public static void start(FragmentActivity activity, int i) {
        Intent intent = new Intent(activity, LicenceUploadActivity.class);
        intent.putExtra("type", i);
        activity.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    for (BaseFragment baseFragment : size) {
                        if (baseFragment != null) {
                            baseFragment.onActivityResult(requestCode, resultCode, data);
                        }
                    }
                    break;
            }
        }
    }

    private static final String TAG = "LicenceUploadActivity";
}
