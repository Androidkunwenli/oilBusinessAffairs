package com.qmy.yzsw.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.AboutUsBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_sysName)
    TextView tvSysName;
    @BindView(R.id.tv_sysLink)
    TextView tvSysLink;
    @BindView(R.id.tv_sysWchat)
    TextView tvSysWchat;
    @BindView(R.id.tv_sysWebUrl)
    TextView tvSysWebUrl;
    @BindView(R.id.tv_sysAddress)
    TextView tvSysAddress;
    @BindView(R.id.image_message)
    ImageView image_message;
    @BindView(R.id.tv_content_str)
    TextView tv_content_str;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    private AboutUsBean mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("关于我们");
    }

    @Override
    public void initView() {
        HttpUtils.aboutUs(mActivity, new JsonCallback<BaseBean<AboutUsBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<AboutUsBean>> response) {
                mData = response.body().getData();
//                tvSysName.setText(data.getSysName());
//                tvSysLink.setText(data.getSysLink());
//                tvSysWchat.setText(data.getSysWchat());
//                tvSysWebUrl.setText(data.getSysWebUrl());
//                tvSysAddress.setText(data.getSysAddress());

                SpannableStringBuilder span = new SpannableStringBuilder("缩进" + mData.getSysRemark());
                span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                Glide.with(mActivity).load(mData.getSysLogoUrl()).apply(new RequestOptions().placeholder(R.mipmap.ic_login_logo)).into(image_message);
                tv_content_str.setText(span);
                tv_phone.setText("客服电话： " + mData.getSysLink());
            }
        });
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, AboutUsActivity.class));
    }


    @OnClick({R.id.tv_sysName, R.id.tv_sysLink, R.id.tv_sysWchat, R.id.tv_sysWebUrl, R.id.tv_sysAddress, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sysName:
                break;
            case R.id.tv_sysLink:
                break;
            case R.id.tv_sysWchat:
                break;
            case R.id.tv_sysWebUrl:
                break;
            case R.id.tv_sysAddress:
                break;
            case R.id.tv_phone:
                if (mData != null) {
                    KUtils.callPhone(mActivity, mData.getSysLink());
                }
                break;
        }
    }

}
