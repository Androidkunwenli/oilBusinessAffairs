package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.platform.comapi.map.gesture.Base;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.FillUpTheAccountAdapter;
import com.qmy.yzsw.adapter.MySpinnerAdapter;
import com.qmy.yzsw.bean.FillUpTheAccountBean;
import com.qmy.yzsw.bean.FillUpTheAccountImageBean;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.fragment.CertificatesPhotoFragment;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.util.SnackbarUtils.dismiss;

public class FillUpTheAccountActivity extends BaseActivity {
    @BindView(R.id.tv_imcomeDate)
    TextView tvImcomeDate;
    @BindView(R.id.et_oilsType)
    EditText etOilsType;
    @BindView(R.id.et_providerLicense)
    EditText etProviderLicense;
    @BindView(R.id.et_providerTel)
    EditText etProviderTel;
    @BindView(R.id.et_providerSendNo)
    EditText etProviderSendNo;
    @BindView(R.id.et_dangerousNo)
    EditText etDangerousNo;
    @BindView(R.id.et_carNo)
    EditText etCarNo;
    @BindView(R.id.et_driverNo)
    EditText etDriverNo;
    @BindView(R.id.et_incomeNum)
    EditText etIncomeNum;
    @BindView(R.id.et_density)
    EditText etDensity;
    @BindView(R.id.et_litre)
    TextView etLitre;
    @BindView(R.id.et_intoPotNo)
    EditText etIntoPotNo;
    @BindView(R.id.et_sendUser)
    EditText etSendUser;
    @BindView(R.id.et_checkUser)
    EditText etCheckUser;
    @BindView(R.id.et_stationLeader)
    EditText etStationLeader;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.spinner_oilsType)
    Spinner spinnerOilsType;
    @BindView(R.id.spinner_oil)
    Spinner spinnerOil;
    private FillUpTheAccountBean bean = new FillUpTheAccountBean();
    private FillUpTheAccountAdapter adapter;
    private int position;
    private FillUpTheAccountImageBean fill;
    private int anInt;

    public static void start(BaseActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, FillUpTheAccountActivity.class));
    }

    public static void start(FragmentActivity activity, MySubmissionReportFormListBean bean) {
        Intent intent = new Intent(activity, FillUpTheAccountActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fill_up_the_account;
    }

    private String[][] str = new String[][]{{"92#", "95#", "98#"}, {"0#", "-10#", "-20#"}};
    private String[] oil = new String[]{"汽油", "柴油"};

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new FillUpTheAccountAdapter(mActivity);
        recList.setAdapter(adapter);
        ArrayList<FillUpTheAccountImageBean> data = new ArrayList<>();
        data.add(new FillUpTheAccountImageBean(null, "进油随发票", 25));
        data.add(new FillUpTheAccountImageBean(null, "油品质量合格证明", 26));
        data.add(new FillUpTheAccountImageBean(null, "进油配送单", 27));
        data.add(new FillUpTheAccountImageBean(null, "", 28, true));
        adapter.setNewData(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                fill = (FillUpTheAccountImageBean) adapter.getData().get(position);
                FillUpTheAccountActivity.this.position = position;
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dismiss();
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etIncomeNum.getText().toString().trim().isEmpty() && !etDensity.getText().toString().trim().isEmpty()) {
                    double div = KUtils.div(KUtils.mul(
                            Double.valueOf(etIncomeNum.getText().toString().trim()), 1000),
                            Double.valueOf(etDensity.getText().toString().trim()));
                    etLitre.setText(String.valueOf(String.format("%.2f", KUtils.mul(div, 1000))));
                } else {
                    etLitre.setText("");
                }
            }
        };
        etIncomeNum.addTextChangedListener(watcher);
        etDensity.addTextChangedListener(watcher);
    }

    private MySubmissionReportFormListBean formListBean;

    @Override
    public void initView() {
        formListBean = getIntent().getParcelableExtra("bean");
        setFindViewById(true);
        setTitleStr("加油站（点）进油台账");
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(mActivity, oil);
        spinnerOil.setAdapter(adapter1);
        spinnerOil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anInt = position;
                MySpinnerAdapter adapter2 = new MySpinnerAdapter(mActivity, str[anInt]);
                spinnerOilsType.setAdapter(adapter2);
                spinnerOilsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        bean.setOilsType(str[anInt][position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (bean != null) {
                    for (int i = 0; i < str[anInt].length; i++) {
                        if (StringUtils.equals(bean.getOilsType(), str[anInt][i])) {
                            spinnerOilsType.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (formListBean != null) {
            HttpUtils.oilsIncomeInfo(mActivity, formListBean.getId(), new DialogCallback<BaseBean<FillUpTheAccountBean>>(mActivity) {
                @Override
                public void onSuccess(Response<BaseBean<FillUpTheAccountBean>> response) {
                    bean = response.body().getData();
                    tvImcomeDate.setText(bean.getImcomeDate());
                    etProviderLicense.setText(bean.getProviderLicense());
                    etProviderTel.setText(bean.getProviderTel());
                    etProviderSendNo.setText(bean.getProviderSendNo());
                    etDangerousNo.setText(bean.getDangerousNo());
                    etCarNo.setText(bean.getCarNo());
                    etDriverNo.setText(bean.getDriverNo());
                    etIncomeNum.setText(KUtils.setFormatNum(bean.getIncomeNum()));
                    etDensity.setText(KUtils.setFormatNum(bean.getDensity()));
                    etLitre.setText(KUtils.setFormatNum(bean.getLitre()));
                    etIntoPotNo.setText(bean.getIntoPotNo());
                    etSendUser.setText(bean.getSendUser());
                    etCheckUser.setText(bean.getCheckUser());
                    etStationLeader.setText(bean.getStationLeader());
                    etRemark.setText(bean.getRemark());
                    ArrayList<FillUpTheAccountImageBean> data = new ArrayList<>();
                    data.add(new FillUpTheAccountImageBean(bean.getInvoiceImg(), "进油随发票", 25));
                    data.add(new FillUpTheAccountImageBean(bean.getSendImg(), "进油配送单", 27));
                    data.add(new FillUpTheAccountImageBean(bean.getQualificationImg(), "油品质量合格证明", 26));
                    for (String str : bean.getOtherImgs().split(",")) {
                        if (!str.isEmpty()) {
                            data.add(new FillUpTheAccountImageBean(str, "", 28, true));
                        }
                    }
                    data.add(new FillUpTheAccountImageBean(null, "", 28, true));
                    if (adapter != null) {
                        adapter.setNewData(data);
                    }
                    for (String s : str[0]) {
                        if (bean.getOilsType().equals(s)) {
                            anInt = 0;
                        }
                    }
                    for (String s : str[1]) {
                        if (bean.getOilsType().equals(s)) {
                            anInt = 1;
                        }
                    }
                    spinnerOil.setSelection(anInt);
                    for (int i = 0; i < str[anInt].length; i++) {
                        if (StringUtils.equals(bean.getOilsType(), str[anInt][i])) {
                            spinnerOilsType.setSelection(i);
                        }
                    }
                }
            });
        }

    }

    @OnClick({R.id.tv_submission, R.id.tv_imcomeDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submission:
                bean.setImcomeDate(tvImcomeDate.getText().toString().trim());
                bean.setProviderLicense(etProviderLicense.getText().toString().trim());
                bean.setProviderTel(etProviderTel.getText().toString().trim());
                bean.setProviderSendNo(etProviderSendNo.getText().toString().trim());
                bean.setDangerousNo(etDangerousNo.getText().toString().trim());
                bean.setCarNo(etCarNo.getText().toString().trim());
                bean.setDriverNo(etDriverNo.getText().toString().trim());
                bean.setIncomeNum(etIncomeNum.getText().toString().trim());
                bean.setDensity(etDensity.getText().toString().trim());
                bean.setLitre(etLitre.getText().toString().trim());
                bean.setIntoPotNo(etIntoPotNo.getText().toString().trim());
                bean.setSendUser(etSendUser.getText().toString().trim());
                bean.setCheckUser(etCheckUser.getText().toString().trim());
                bean.setStationLeader(etStationLeader.getText().toString().trim());
                bean.setRemark(etRemark.getText().toString().trim());
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < adapter.getData().size(); i++) {
                    if (0 == i) {
                        bean.setInvoiceImg(adapter.getData().get(0).getImageupload());
                    } else if (1 == i) {
                        bean.setQualificationImg(adapter.getData().get(1).getImageupload());
                    } else if (2 == i) {
                        bean.setSendImg(adapter.getData().get(2).getImageupload());
                    } else {
                        String imageupload = adapter.getData().get(i).getImageupload();
                        if (imageupload != null && !imageupload.isEmpty()) {
                            buffer.append(imageupload).append(",");
                        }
                    }
                }
                bean.setOtherImgs(buffer.toString());
                HttpUtils.oilsIncomeSave(mActivity, bean, new DialogCallback<BaseBean>(mActivity) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        ToastUtils.showShort(response.body().getMsg());
                        finish();
                    }
                });
                break;
            case R.id.tv_imcomeDate:
                TimePickerView build = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        tvImcomeDate.setText(timeStr);
                    }
                }).build();
                build.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() != 0 && adapter != null) {

                        final String path = selectList.get(0).getPath();
                        KUtils.uploadFile(mActivity, path, fill.getType(), fill.getImageupload(), new DialogCallback<BaseBean<String>>(mActivity) {
                            @Override
                            public void onSuccess(Response<BaseBean<String>> response) {
                                fill.setImagePath(path);
                                if (fill.isAdd() && adapter.getData().size() < 10) {
                                    fill.setImageupload(response.body().getData());
                                    if (adapter.getData().get(adapter.getData().size() - 1).getImageupload() != null) {
                                        adapter.getData().add(new FillUpTheAccountImageBean(null, "", 28, true));
                                    }
                                } else {
                                    fill.setImageupload(response.body().getData());
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
            }
        }
    }
}
