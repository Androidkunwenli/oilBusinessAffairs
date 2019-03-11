package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.ExpireDateGetBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.view.ScreenPopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LicenceRemindActivity extends BaseActivity {
    @BindView(R.id.tv_screen)
    TextView tvScreen;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.ff_screen)
    TagFlowLayout ffScreen;
    @BindView(R.id.image_delete)
    ImageView imageDelete;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.image_businesLicense)
    ImageView imageBusinesLicense;
    @BindView(R.id.businesLicense_nian)
    TextView businesLicenseNian;
    @BindView(R.id.businesLicense_yue)
    TextView businesLicenseYue;
    @BindView(R.id.businesLicense_ri)
    TextView businesLicenseRi;
    @BindView(R.id.image_legalPersonLicense)
    ImageView imageLegalPersonLicense;
    @BindView(R.id.legalPersonLicense_nian)
    TextView legalPersonLicenseNian;
    @BindView(R.id.legalPersonLicense_yue)
    TextView legalPersonLicenseYue;
    @BindView(R.id.legalPersonLicense_ri)
    TextView legalPersonLicenseRi;
    @BindView(R.id.image_dangerousChemicalCertificate)
    ImageView imageDangerousChemicalCertificate;
    @BindView(R.id.dangerousChemicalCertificate_nian)
    TextView dangerousChemicalCertificateNian;
    @BindView(R.id.dangerousChemicalCertificate_yue)
    TextView dangerousChemicalCertificateYue;
    @BindView(R.id.dangerousChemicalCertificate_ri)
    TextView dangerousChemicalCertificateRi;
    @BindView(R.id.image_productOilBusinessLicense)
    ImageView imageProductOilBusinessLicense;
    @BindView(R.id.productOilBusinessLicense_nian)
    TextView productOilBusinessLicenseNian;
    @BindView(R.id.productOilBusinessLicense_yue)
    TextView productOilBusinessLicenseYue;
    @BindView(R.id.productOilBusinessLicense_ri)
    TextView productOilBusinessLicenseRi;
    @BindView(R.id.businesLicenseExpire)
    TextView businesLicenseExpire;
    @BindView(R.id.legalPersonLicenseExpire)
    TextView legalPersonLicenseExpire;
    @BindView(R.id.dangerousChemicalCertificateExpire)
    TextView dangerousChemicalCertificateExpire;
    @BindView(R.id.productOilBusinessLicenseExpire)
    TextView productOilBusinessLicenseExpire;
    @BindView(R.id.ll_businesLicense)
    LinearLayout llBusinesLicense;
    @BindView(R.id.ll_legalPersonLicense)
    LinearLayout llLegalPersonLicense;
    @BindView(R.id.ll_dangerousChemicalCertificate)
    LinearLayout llDangerousChemicalCertificate;
    @BindView(R.id.ll_productOilBusinessLicense)
    LinearLayout llProductOilBusinessLicense;
    @BindView(R.id.ll_layout1)
    LinearLayout llLayout1;
    @BindView(R.id.ll_layout2)
    LinearLayout llLayout2;
    @BindView(R.id.ll_layout3)
    LinearLayout llLayout3;
    @BindView(R.id.ll_layout4)
    LinearLayout llLayout4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_licence_remind;
    }

    private List<String> mVals = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        ffScreen.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.item_tag_flow_layout,
                        ffScreen, false);
                tv.setBackgroundResource(R.drawable.dr_problem_feedback_theme);
                tv.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                tv.setText(o);
                return tv;
            }
        });
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("证照提醒");
        setTvRightText("保存");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtils.expireDateSave(mActivity,
                        businesLicenseNian.getText().toString().trim() + "-"
                                + businesLicenseYue.getText().toString().trim() + "-"
                                + businesLicenseRi.getText().toString().trim(),
                        legalPersonLicenseNian.getText().toString().trim() + "-"
                                + legalPersonLicenseYue.getText().toString().trim() + "-"
                                + legalPersonLicenseRi.getText().toString().trim(),
                        dangerousChemicalCertificateNian.getText().toString().trim() + "-"
                                + dangerousChemicalCertificateYue.getText().toString().trim() + "-"
                                + dangerousChemicalCertificateRi.getText().toString().trim(),
                        productOilBusinessLicenseNian.getText().toString().trim() + "-"
                                + productOilBusinessLicenseYue.getText().toString().trim() + "-"
                                + productOilBusinessLicenseRi.getText().toString().trim(),
                        new JsonCallback<BaseBean>() {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                                ToastUtils.showShort(response.body().getMsg());
                            }

                        });
            }
        });

        HttpUtils.expireDateGet(mActivity, new JsonCallback<BaseBean<ExpireDateGetBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<ExpireDateGetBean>> response) {
                try {
                    ExpireDateGetBean data = response.body().getData();
                    GlideUtils.LoadImage(data.getBusinesLicenseImg(), imageBusinesLicense);
                    GlideUtils.LoadImage(data.getDangerousChemicalCertificateImg(), imageDangerousChemicalCertificate);
                    GlideUtils.LoadImage(data.getLegalPersonLicenseImg(), imageLegalPersonLicense);
                    GlideUtils.LoadImage(data.getProductOilBusinessLicenseImg(), imageProductOilBusinessLicense);
                    String businesLicense = data.getBusinesLicense();
                    if (!businesLicense.isEmpty()) {
                        String[] busineslicense = businesLicense.split("-");
                        businesLicenseNian.setText(busineslicense[0]);
                        businesLicenseYue.setText(busineslicense[1]);
                        businesLicenseRi.setText(busineslicense[2]);
                    }
                    String dangerousChemicalCertificate = data.getDangerousChemicalCertificate();
                    if (!dangerousChemicalCertificate.isEmpty()) {
                        String[] dangerouschemicalcertificate = dangerousChemicalCertificate.split("-");
                        dangerousChemicalCertificateNian.setText(dangerouschemicalcertificate[0]);
                        dangerousChemicalCertificateYue.setText(dangerouschemicalcertificate[1]);
                        dangerousChemicalCertificateRi.setText(dangerouschemicalcertificate[2]);
                    }
                    String legalPersonLicense = data.getLegalPersonLicense();
                    if (!legalPersonLicense.isEmpty()) {
                        String[] legalpersonlicense = legalPersonLicense.split("-");
                        legalPersonLicenseNian.setText(legalpersonlicense[0]);
                        legalPersonLicenseYue.setText(legalpersonlicense[1]);
                        legalPersonLicenseRi.setText(legalpersonlicense[2]);
                    }
                    String productOilBusinessLicense1 = data.getProductOilBusinessLicense();
                    if (!productOilBusinessLicense1.isEmpty()) {
                        String[] productOilBusinessLicense = productOilBusinessLicense1.split("-");
                        productOilBusinessLicenseNian.setText(productOilBusinessLicense[0]);
                        productOilBusinessLicenseYue.setText(productOilBusinessLicense[1]);
                        productOilBusinessLicenseRi.setText(productOilBusinessLicense[2]);
                    }
                    String hasExpire = data.getHasExpire();
                    switch (hasExpire) {
                        case "0":
                            llLayout.setVisibility(View.GONE);
                            break;
                        case "1":
                            llLayout.setVisibility(View.VISIBLE);
                            tvRemind.setText("您已有证照即将过期，请及时上传新的证照！");
                            break;
                        case "-1":
                            llLayout.setVisibility(View.VISIBLE);
                            tvRemind.setText("您已有证照已经过期，请及时上传新的证照！");
                            break;
                    }
                    switch (data.getBusinesLicenseExpire()) {
                        case "0":
                            businesLicenseExpire.setVisibility(View.INVISIBLE);
                            break;
                        case "1":
                            businesLicenseExpire.setVisibility(View.VISIBLE);
                            businesLicenseExpire.setText("（您的证照即将过期！）");
                            break;
                        case "-1":
                            businesLicenseExpire.setVisibility(View.VISIBLE);
                            businesLicenseExpire.setText("（您的证照已经过期！）");
                            break;

                    }
                    switch (data.getLegalPersonLicenseExpire()) {
                        case "0":
                            legalPersonLicenseExpire.setVisibility(View.INVISIBLE);
                            break;
                        case "1":
                            legalPersonLicenseExpire.setVisibility(View.VISIBLE);
                            legalPersonLicenseExpire.setText("（您的证照即将过期！）");
                            break;
                        case "-1":
                            legalPersonLicenseExpire.setVisibility(View.VISIBLE);
                            legalPersonLicenseExpire.setText("（您的证照已经过期！）");
                            break;

                    }
                    switch (data.getDangerousChemicalCertificateExpire()) {
                        case "0":
                            dangerousChemicalCertificateExpire.setVisibility(View.INVISIBLE);
                            break;
                        case "1":
                            dangerousChemicalCertificateExpire.setVisibility(View.VISIBLE);
                            dangerousChemicalCertificateExpire.setText("（您的证照即将过期！）");
                            break;
                        case "-1":
                            dangerousChemicalCertificateExpire.setVisibility(View.VISIBLE);
                            dangerousChemicalCertificateExpire.setText("（您的证照已经过期！）");
                            break;

                    }
                    switch (data.getProductOilBusinessLicenseExpire()) {
                        case "0":
                            productOilBusinessLicenseExpire.setVisibility(View.INVISIBLE);
                            break;
                        case "1":
                            productOilBusinessLicenseExpire.setVisibility(View.VISIBLE);
                            productOilBusinessLicenseExpire.setText("（您的证照即将过期！）");
                            break;
                        case "-1":
                            productOilBusinessLicenseExpire.setVisibility(View.VISIBLE);
                            productOilBusinessLicenseExpire.setText("（您的证照已经过期！）");
                            break;

                    }

                } catch (NullPointerException e) {
                    e.getMessage();
                }
            }

        });
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, LicenceRemindActivity.class));
    }

    @OnClick({R.id.image_delete, R.id.image_businesLicense, R.id.image_legalPersonLicense,
            R.id.image_dangerousChemicalCertificate, R.id.image_productOilBusinessLicense,
            R.id.ll_businesLicense, R.id.ll_legalPersonLicense, R.id.ll_dangerousChemicalCertificate,
            R.id.ll_productOilBusinessLicense, R.id.tv_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_screen:
                ToastUtils.showShort("筛选");
                ScreenPopupWindow screenPopupWindow = new ScreenPopupWindow(mActivity, mVals, new ScreenPopupWindow.getDataOnClickListener() {
                    @Override
                    public void showData(List<String> data) {
                        if (data.isEmpty()) {
                            return;
                        }
                        mVals.clear();
                        mVals.addAll(data);
                        ffScreen.getAdapter().notifyDataChanged();
                        llLayout1.setVisibility(data.contains("营业执照") ? View.VISIBLE : View.INVISIBLE);
                        llLayout2.setVisibility(data.contains("法人资格证") ? View.VISIBLE : View.INVISIBLE);
                        llLayout3.setVisibility(data.contains("危化证") ? View.VISIBLE : View.INVISIBLE);
                        llLayout4.setVisibility(data.contains("成品油经营许可证") ? View.VISIBLE : View.INVISIBLE);
                    }
                });
                screenPopupWindow.showAsDropDown(ffMainLayout);
                break;
            case R.id.image_delete:
                llLayout.setVisibility(View.GONE);
                break;
            case R.id.image_businesLicense:
                break;
            case R.id.image_legalPersonLicense:
                break;
            case R.id.image_dangerousChemicalCertificate:
                break;
            case R.id.image_productOilBusinessLicense:
                break;
            case R.id.ll_businesLicense:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] timeArray = timeStr.split("-");
                        businesLicenseNian.setText(timeArray[0]);
                        businesLicenseYue.setText(timeArray[1]);
                        businesLicenseRi.setText(timeArray[2]);
                    }
                }).build();
                pvTime.show();
                break;
            case R.id.ll_legalPersonLicense://时间选择器
                TimePickerView pvTime1 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] timeArray = timeStr.split("-");
                        legalPersonLicenseNian.setText(timeArray[0]);
                        legalPersonLicenseYue.setText(timeArray[1]);
                        legalPersonLicenseRi.setText(timeArray[2]);
                    }
                }).build();
                pvTime1.show();
                break;
            case R.id.ll_dangerousChemicalCertificate://时间选择器
                TimePickerView pvTime2 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] timeArray = timeStr.split("-");
                        dangerousChemicalCertificateNian.setText(timeArray[0]);
                        dangerousChemicalCertificateYue.setText(timeArray[1]);
                        dangerousChemicalCertificateRi.setText(timeArray[2]);
                    }
                }).build();
                pvTime2.show();
                break;
            case R.id.ll_productOilBusinessLicense://时间选择器
                TimePickerView pvTime3 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] timeArray = timeStr.split("-");
                        productOilBusinessLicenseNian.setText(timeArray[0]);
                        productOilBusinessLicenseYue.setText(timeArray[1]);
                        productOilBusinessLicenseRi.setText(timeArray[2]);
                    }
                }).build();
                pvTime3.show();
                break;
        }
    }
}
