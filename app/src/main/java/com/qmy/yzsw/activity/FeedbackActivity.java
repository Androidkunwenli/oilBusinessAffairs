package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("反馈意见");
    }

    @Override
    public void initView() {

    }

    public static void start(BaseActivity activity) {
        activity.startActivity(new Intent(activity, FeedbackActivity.class));
    }


    @OnClick(R.id.tv_submission)
    public void onViewClicked() {
        if (etContent.getText().toString().trim().isEmpty()) {
            ToastUtils.showShort("请填写您的宝贵意见，谢谢配合！");
            return;
        }
        HttpUtils.complaint(mActivity, etContent.getText().toString().trim(), new JsonCallback<BaseBean>() {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                ToastUtils.showShort(response.body().getMsg());
                finish();
            }
        });
    }
}
