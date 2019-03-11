package com.qmy.yzsw.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmy.yzsw.R;
import com.youth.xframe.base.ICallback;
import com.youth.xframe.common.XActivityStack;
import com.youth.xframe.utils.permission.XPermission;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements ICallback {
    private boolean isFindViewById;
    public FrameLayout ffMainLayout;
    public ImageView mTvReturn;
    public TextView mTvTitleStr;
    public String mTvRightText;
    public TextView mRightKey;
    public ImageView mImageRightKey;
    public BaseActivity mActivity;
    private static final String TAG = "BaseActivity";
    private String titleStr;
    public int page = 0;
    public FrameLayout ff_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        page = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
        XActivityStack.getInstance().addActivity(mActivity);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData(savedInstanceState);
        initView();
        if (isFindViewById) {
            ffMainLayout = (FrameLayout) findViewById(R.id.ff_main_layout);
            mTvReturn = (ImageView) findViewById(R.id.tv_return);
            mTvTitleStr = (TextView) findViewById(R.id.tv_title_str);
            mTvTitleStr.setText(titleStr);
            mTvReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFindViewById = false;
                    finish();

                }
            });
        }


    }


    public void setTvRightText(String tvRightText) {
        mTvRightText = tvRightText;
        mRightKey = (TextView) findViewById(R.id.tv_right_key);
        if (mTvRightText != null) {
            mRightKey.setVisibility(View.VISIBLE);
            mRightKey.setText(mTvRightText);
        }
    }

    public void setimageRightText(int image) {
        mImageRightKey = (ImageView) findViewById(R.id.image_right_key);
        mImageRightKey.setImageResource(image);
        mImageRightKey.setVisibility(View.VISIBLE);
    }

    public boolean isFindViewById() {
        return isFindViewById;
    }

    public void setFindViewById(boolean findViewById) {
        isFindViewById = findViewById;
    }

    public void setTitleStr(String titleStr) {
        if (isFindViewById && mTvTitleStr != null && !titleStr.isEmpty()) {
            if (titleStr.length() > 12) {
                mTvTitleStr.setText(titleStr.substring(0, 11));
            } else {
                mTvTitleStr.setText(titleStr);
            }
        }
        this.titleStr = titleStr;
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
