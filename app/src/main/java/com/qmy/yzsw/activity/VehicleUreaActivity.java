package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.qmy.yzsw.bean.FillUpTheAccountImageBean;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.VehicleUreaBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.util.SnackbarUtils.dismiss;

public class VehicleUreaActivity extends BaseActivity {

    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.tv_imcomeDate)
    TextView tvImcomeDate;
    @BindView(R.id.et_providerName)
    EditText etProviderName;
    @BindView(R.id.et_providerArea)
    EditText etProviderArea;
    @BindView(R.id.et_brand)
    EditText etBrand;
    @BindView(R.id.et_spec)
    EditText etSpec;
    @BindView(R.id.et_incomeNum)
    EditText etIncomeNum;
    @BindView(R.id.et_grade)
    EditText etGrade;
    @BindView(R.id.et_batchNo)
    EditText etBatchNo;
    @BindView(R.id.et_qualificationNo)
    EditText etQualificationNo;
    @BindView(R.id.et_licenseNo)
    EditText etLicenseNo;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.spinner_choice_1)
    Spinner spinnerChoice1;
    @BindView(R.id.spinner_choice_2)
    Spinner spinnerChoice2;
    private FillUpTheAccountImageBean fill;
    private VehicleUreaBean bean = new VehicleUreaBean();

    public static void start(BaseActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, VehicleUreaActivity.class));
    }


    public static void start(FragmentActivity activity, MySubmissionReportFormListBean bean) {
        Intent intent = new Intent(activity, VehicleUreaActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);

    }

    private FillUpTheAccountAdapter adapter;
    private String[] str = new String[]{"吨", "kg/桶"};
    private String[] str1 = new String[]{"吨", "桶"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_urea;
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("加油站（点）车用尿素进货台账");
        recList.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new FillUpTheAccountAdapter(mActivity);
        recList.setAdapter(adapter);
        final ArrayList<FillUpTheAccountImageBean> data = new ArrayList<>();
        data.add(new FillUpTheAccountImageBean(null, "进货发票", 22));
        data.add(new FillUpTheAccountImageBean(null, "产品质量合格证明材料", 23));
        data.add(new FillUpTheAccountImageBean(null, "", 24, true));
        adapter.setNewData(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                fill = (FillUpTheAccountImageBean) adapter.getData().get(position);
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dismiss();
            }
        });
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(mActivity, str);
        MySpinnerAdapter adapter2 = new MySpinnerAdapter(mActivity, str1);
        spinnerChoice1.setAdapter(adapter1);
        spinnerChoice2.setAdapter(adapter2);
        spinnerChoice1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bean.setSpecUnit(String.valueOf(i + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerChoice2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bean.setIncomeUnit(String.valueOf(i + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private MySubmissionReportFormListBean formListBean;

    @Override
    public void initData(Bundle savedInstanceState) {
        formListBean = getIntent().getParcelableExtra("bean");
        if (formListBean != null) {
            HttpUtils.ureaInfo(mActivity, formListBean.getId(), new DialogCallback<BaseBean<VehicleUreaBean>>(mActivity) {
                @Override
                public void onSuccess(Response<BaseBean<VehicleUreaBean>> response) {
                    bean = response.body().getData();
                    tvImcomeDate.setText(bean.getImcomeDate());
                    etProviderName.setText(bean.getProviderName());
                    etProviderArea.setText(bean.getProviderArea());
                    etBrand.setText(bean.getBrand());
                    etSpec.setText(KUtils.setFormatNum(bean.getSpec()));
                    etIncomeNum.setText(KUtils.setFormatNum(bean.getIncomeNum()));
                    etGrade.setText(bean.getGrade());
                    etBatchNo.setText(bean.getBatchNo());
                    etQualificationNo.setText(bean.getQualificationNo());
                    etLicenseNo.setText(bean.getLicenseNo());
                    etRemark.setText(bean.getRemark());
                    if (StringUtils.equals(bean.getSpecUnit(), "1")) {
                        spinnerChoice1.setSelection(0, true);
                    } else if (StringUtils.equals(bean.getSpecUnit(), "2")) {
                        spinnerChoice1.setSelection(1, true);
                    }
                    if (StringUtils.equals(bean.getIncomeUnit(), "1")) {
                        spinnerChoice2.setSelection(0, true);
                    } else if (StringUtils.equals(bean.getIncomeUnit(), "2")) {
                        spinnerChoice2.setSelection(1, true);
                    }
                    ArrayList<FillUpTheAccountImageBean> data = new ArrayList<>();
                    data.add(new FillUpTheAccountImageBean(bean.getInvoiceImg(), "进货发票", 22));
                    data.add(new FillUpTheAccountImageBean(bean.getQualificationImg(), "产品质量合格证明材料", 23));
                    for (String str : bean.getOtherImgs().split(",")) {
                        if (!str.isEmpty()) {
                            data.add(new FillUpTheAccountImageBean(str, "", 24, true));
                        }
                    }
                    data.add(new FillUpTheAccountImageBean(null, "", 24, true));
                    if (adapter != null) {
                        adapter.setNewData(data);
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
                bean.setProviderName(etProviderName.getText().toString().trim());
                bean.setProviderArea(etProviderArea.getText().toString().trim());
                bean.setBrand(etBrand.getText().toString().trim());
                bean.setSpec(etSpec.getText().toString().trim());
                bean.setIncomeNum(etIncomeNum.getText().toString().trim());
                bean.setGrade(etGrade.getText().toString().trim());
                bean.setBatchNo(etBatchNo.getText().toString().trim());
                bean.setQualificationNo(etQualificationNo.getText().toString().trim());
                bean.setLicenseNo(etLicenseNo.getText().toString().trim());
                bean.setRemark(etRemark.getText().toString().trim());
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < adapter.getData().size(); i++) {
                    if (0 == i) {
                        bean.setInvoiceImg(adapter.getData().get(0).getImageupload());
                    } else if (1 == i) {
                        bean.setQualificationImg(adapter.getData().get(1).getImageupload());
                    } else {
                        String imageupload = adapter.getData().get(i).getImageupload();
                        if (imageupload != null && !imageupload.isEmpty()) {
                            buffer.append(imageupload).append(",");
                        }
                    }
                }
                bean.setOtherImgs(buffer.toString());
                HttpUtils.ureaSave(mActivity, bean, new DialogCallback<BaseBean>(mActivity) {
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
