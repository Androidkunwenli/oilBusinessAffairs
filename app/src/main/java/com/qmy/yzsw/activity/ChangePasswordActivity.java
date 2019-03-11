package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
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

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.et_old_pass)
    EditText etOldPass;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("更改密码");
    }

    @Override
    public void initView() {

    }

    public static void start(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ChangePasswordActivity.class));
    }


    @OnClick(R.id.tv_submission)
    public void onViewClicked() {
        String etOldPassStr = etOldPass.getText().toString().trim();
        String etPassStr = etPass.getText().toString().trim();
        final String etNewPassStr = etNewPass.getText().toString().trim();

        if (etOldPassStr.isEmpty()) {
            ToastUtils.showShort("请输入旧密码");
            return;
        }
        if (etPass.length() < 6 || etPassStr.isEmpty()) {
            ToastUtils.showShort("请输入新密码（至少6位）");
            return;
        }
        if (etNewPass.length() < 6 || etNewPassStr.isEmpty()) {
            ToastUtils.showShort("请确认新密码（至少6位）");
            return;
        }
        if (!StringUtils.equals(etPassStr, etNewPassStr)) {
            ToastUtils.showShort("两次输入密码不一致");
            return;
        }
        HttpUtils.modifyPass(mActivity, etOldPassStr, etNewPassStr, new JsonCallback<BaseBean>() {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                SPUtils.getInstance("yzsw").put("pass", etNewPassStr);
                ToastUtils.showShort(response.body().getMsg());

                finish();
            }
        });
    }
}
