package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPassActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.tv_get_sms)
    TextView tvGetSms;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("忘记密码");
    }

    public static void start(LoginActivity loginActivity) {
        loginActivity.startActivity(new Intent(loginActivity, ForgetPassActivity.class));
    }

    @OnClick({R.id.tv_get_sms, R.id.tv_submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:
                String phone = etPhone.getText().toString().trim();
                if (phone.isEmpty()) {
                    ToastUtils.showShort("请输入手机号码");
                    return;
                }
                HttpUtils.getSms(mActivity, phone, 2, new JsonCallback<BaseBean>() {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        if (response.isSuccessful()) {
                            BaseBean body = response.body();
                            if (body != null) {
                                if (body.getCode() == 1) {
                                    KUtils.setRegisterTimeCount(tvGetSms, 60000, 1000);
                                }
                                ToastUtils.showShort(body.getMsg());
                            }
                        }
                    }
                });
                break;
            case R.id.tv_submission:
                String phone1 = etPhone.getText().toString().trim();
                if (phone1.isEmpty()) {
                    ToastUtils.showShort("请输入手机号码");
                    return;
                }
                String sms = etSms.getText().toString().trim();
                if (sms.isEmpty()) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                String pass = etPass.getText().toString().trim();
                if (pass.isEmpty()) {
                    ToastUtils.showShort("请输入新密码（至少6位）");
                    return;
                }
                String newPass = etNewPass.getText().toString().trim();
                if (newPass.isEmpty()) {
                    ToastUtils.showShort("请确认新密码（至少6位）");
                    return;
                }
                if (!newPass.equals(pass)) {
                    ToastUtils.showShort("两次密码输入不一致");
                    return;
                }
                HttpUtils.forgetPassword(mActivity, sms, phone1, newPass, new JsonCallback<BaseBean>() {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        if (response.isSuccessful()) {
                            BaseBean body = response.body();
                            if (body != null) {
                                if (body.getCode() == 1) {
                                    finish();
                                }
                                ToastUtils.showShort(body.getMsg());
                            }
                        }
                    }
                });
                break;
        }
    }
}
