package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.common.XActivityStack;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.cb_remember_pass)
    CheckBox cbRememberPass;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_pass)
    TextView tvForgetPass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String phone = SPUtils.getInstance("yzsw").getString("phone");
        if (!phone.isEmpty()) {
            etPhone.setText(phone);
        }
        String pass = SPUtils.getInstance("yzsw").getString("pass");
        if (!pass.isEmpty()) {
            etPass.setText(pass);
            cbRememberPass.setChecked(true);
            isSave = true;
        }
    }

    @Override
    public void initView() {
        cbRememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSave = b;
            }
        });
    }

    private boolean isSave = false;

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forget_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String phone = etPhone.getText().toString().trim();
                if (phone.isEmpty()) {
                    ToastUtils.showShort("请输入手机号码");
                    return;
                }
                String pass = etPass.getText().toString().trim();
                if (pass.isEmpty()) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (isSave) {
                    SPUtils.getInstance("yzsw").put("phone", phone);
                    SPUtils.getInstance("yzsw").put("pass", pass);
                } else {
                    SPUtils.getInstance("yzsw").remove("phone");
                    SPUtils.getInstance("yzsw").remove("pass");
                }
                //设置别名
                JPushInterface.setAlias(mActivity, 0, phone);
                HttpUtils.login(mActivity, phone, pass, new JsonCallback<BaseBean>() {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getCode() == 1) {
                                    SPUtils.getInstance().put("token", response.body().getData().toString());
                                    MainActivity.start(LoginActivity.this);
                                    finish();
                                }
                                ToastUtils.showShort(response.body().getMsg());
                            }
                        }
                    }
                });
                break;
            case R.id.tv_register:
                RegisterActivity.start(LoginActivity.this);
                break;
            case R.id.tv_forget_pass:
                ForgetPassActivity.start(LoginActivity.this);
                break;
        }
    }

    public static void start(FragmentActivity activity) {
        SPUtils.getInstance().remove("token");
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }
}
