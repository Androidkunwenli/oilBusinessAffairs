package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.HiddenDangerBean;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;

import java.util.Date;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HiddenDangerActivity extends BaseActivity {
    @BindView(R.id.tv_title_str)
    TextView tvTitleStr;
    @BindView(R.id.tv_checkDate)
    TextView tvCheckDate;
    @BindView(R.id.et_checkUser)
    EditText etCheckUser;
    @BindView(R.id.et_checkRemark)
    EditText etCheckRemark;
    @BindView(R.id.tv_rectifyDate)
    TextView tvRectifyDate;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.et_rectifyRemark)
    EditText etRectifyRemark;
    @BindView(R.id.et_reCheckUser)
    EditText etReCheckUser;
    @BindView(R.id.tv_reCheckDate)
    TextView tvReCheckDate;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    private String id = "";
    private MySubmissionReportFormListBean bean;

    public static void start(BaseActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, HiddenDangerActivity.class));
    }

    public static void start(FragmentActivity activity, MySubmissionReportFormListBean bean) {
        Intent intent = new Intent(activity, HiddenDangerActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hidden_danger;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bean = getIntent().getParcelableExtra("bean");
        if (bean != null) {
            id = bean.getId();
            HttpUtils.securityInfo(mActivity, bean.getId(), new DialogCallback<BaseBean<HiddenDangerBean>>(mActivity) {
                @Override
                public void onSuccess(Response<BaseBean<HiddenDangerBean>> response) {
                    HiddenDangerBean data = response.body().getData();
                    tvCheckDate.setText(data.getCheckDate());
                    etCheckUser.setText(data.getCheckUser());
                    etCheckRemark.setText(data.getCheckRemark());
                    tvRectifyDate.setText(data.getRectifyDate());
                    etRectifyRemark.setText(data.getRectifyRemark());
                    etReCheckUser.setText(data.getReCheckUser());
                    tvReCheckDate.setText(data.getReCheckDate());
                    etRemark.setText(data.getRemark());
                }
            });
        }
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("安全生产隐患排查治理台账");
    }


    @OnClick({R.id.tv_checkDate, R.id.tv_rectifyDate, R.id.tv_reCheckDate, R.id.tv_submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_checkDate:
                TimePickerView pvTime1 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        tvCheckDate.setText(timeStr);
                    }
                }).build();
                pvTime1.show();
                break;
            case R.id.tv_rectifyDate:
                TimePickerView pvTime2 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        tvRectifyDate.setText(timeStr);
                    }
                }).build();
                pvTime2.show();
                break;
            case R.id.tv_reCheckDate:
                TimePickerView pvTime3 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        tvReCheckDate.setText(timeStr);
                    }
                }).build();
                pvTime3.show();
                break;
            case R.id.tv_submission:
                HttpUtils.securitySave(mActivity, id, tvCheckDate.getText().toString().trim(), etCheckUser.getText().toString().trim(),
                        etCheckRemark.getText().toString().trim(), tvRectifyDate.getText().toString().trim(), etRectifyRemark.getText().toString().trim(),
                        etReCheckUser.getText().toString().trim(), tvReCheckDate.getText().toString().trim(), etRemark.getText().toString().trim(), new JsonCallback<BaseBean>() {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                                finish();
                            }
                        });
                break;
        }
    }

}
