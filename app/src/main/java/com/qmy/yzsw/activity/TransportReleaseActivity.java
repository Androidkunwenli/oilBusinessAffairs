package com.qmy.yzsw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.TransportReleaseAdapter;
import com.qmy.yzsw.bean.CarDetailBean;
import com.qmy.yzsw.bean.OilsDetailBean;
import com.qmy.yzsw.bean.TransportMsgBean;
import com.qmy.yzsw.bean.TransportReleaseBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.uploadpicture.UploadPictureBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.UploadPictureUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransportReleaseActivity extends BaseActivity {
    @BindView(R.id.rb_carSave)
    RadioButton rbCarSave;
    @BindView(R.id.rb_oilsSave)
    RadioButton rbOilsSave;
    @BindView(R.id.rg_save)
    RadioGroup rgSave;
    @BindView(R.id.ll_pick_up_address)
    LinearLayout llPickUpAddress;
    @BindView(R.id.ll_receiving_address)
    LinearLayout llReceivingAddress;
    @BindView(R.id.imgFile1)
    ImageView imgFile1;
    @BindView(R.id.imgFile2)
    ImageView imgFile2;
    @BindView(R.id.ll_image)
    LinearLayout llImage;
    @BindView(R.id.ll_car_model)
    LinearLayout llCarModel;
    @BindView(R.id.ll_car_number)
    LinearLayout llCarNumber;
    @BindView(R.id.rec_list)
    RecyclerView mRecList;
    @BindView(R.id.otherRemark)
    EditText otherRemark;
    @BindView(R.id.et_enterpriseName)
    EditText etEnterpriseName;
    @BindView(R.id.et_enterpriceLeader)
    EditText etEnterpriceLeader;
    @BindView(R.id.et_enterpricePhone)
    EditText etEnterpricePhone;
    @BindView(R.id.et_carModel)
    EditText etCarModel;
    @BindView(R.id.et_carLicense)
    EditText etCarLicense;
    @BindView(R.id.et_pickUpAddr)
    EditText etPickUpAddr;
    @BindView(R.id.et_sendAddr)
    EditText etSendAddr;
    @BindView(R.id.ll_myself)
    LinearLayout ll_myself;
    @BindView(R.id.tv_receipt)
    TextView tv_receipt;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private boolean type = true;
    private TransportReleaseAdapter mAdapter;
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transport_release;
    }

    private CarDetailBean mCarDetailBean;
    private OilsDetailBean mOilsDetailBean;
    private int mInt;
    private boolean file = true;
    private String imgFile1_path;
    private String imgFile2_path;

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTvRightText("保存");
        mRecList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TransportReleaseAdapter(mActivity);
        mRecList.setAdapter(mAdapter);
        mCarDetailBean = getIntent().getParcelableExtra("carDetailBean");
        mOilsDetailBean = getIntent().getParcelableExtra("oilsDetailBean");
        if (mCarDetailBean == null && mOilsDetailBean == null) {
            setTitleStr("发布信息");
            mCarDetailBean = new CarDetailBean();
            mOilsDetailBean = new OilsDetailBean();
            ll_myself.setVisibility(View.GONE);
            ArrayList<TransportReleaseBean> list = new ArrayList<>();
            list.add(new TransportReleaseBean());
            mAdapter.setNewData(list);
        } else {
            mRightKey.setVisibility(View.GONE);
            rgSave.setVisibility(View.GONE);
            setTitleStr(mCarDetailBean != null && mOilsDetailBean == null ? "拉货详情" : "找车详情");
            tv_receipt.setText(mCarDetailBean != null && mOilsDetailBean == null ? "已接单" : "已成交");
            if ((mCarDetailBean != null && StringUtils.equals("1", mCarDetailBean.getMyself())) ||
                    (mOilsDetailBean != null && StringUtils.equals("1", mOilsDetailBean.getMyself()))) {
                ll_myself.setVisibility(View.VISIBLE);
            } else {
                ll_myself.setVisibility(View.GONE);
            }
            if (mCarDetailBean != null) {
                HttpUtils.carDetail(mActivity, mCarDetailBean.getId(), new JsonCallback<BaseBean<CarDetailBean>>() {
                            @Override
                            public void onSuccess(Response<BaseBean<CarDetailBean>> response) {
                                CarDetailBean data = response.body().getData();
                                mCarDetailBean = data;
                                etEnterpriseName.setText(data.getEnterpriseName());
                                etEnterpriceLeader.setText(data.getEnterpriceLeader());
                                etEnterpricePhone.setText(data.getEnterpricePhone());
                                etCarModel.setText(data.getCarModel());
                                etCarLicense.setText(data.getCarLicense());
                                otherRemark.setText(data.getOtherRemark());
                                GlideUtils.LoadImage(data.getImg1(), imgFile1);
                                GlideUtils.LoadImage(data.getImg2(), imgFile2);
                                ArrayList<TransportReleaseBean> list = new ArrayList<>();
                                if (!data.getNinetytwo().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetytwo", "92#", data.getNinetytwo()));
                                }
                                if (!data.getNinetyfive().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetyfive", "95#", data.getNinetyfive()));
                                }
                                if (!data.getNinetyeight().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetyeight", "98#", data.getNinetyeight()));
                                }
                                if (!data.getZero().isEmpty()) {
                                    list.add(new TransportReleaseBean("zero", "0#", data.getZero()));
                                }
                                if (!data.getTen().isEmpty()) {
                                    list.add(new TransportReleaseBean("ten", "-10#", data.getTen()));
                                }
                                if (!data.getTwenty().isEmpty()) {
                                    list.add(new TransportReleaseBean("twenty", "-20#", data.getTwenty()));
                                }
                                if (!data.getLng().isEmpty()) {
                                    list.add(new TransportReleaseBean("lng", "LNG", data.getLng()));
                                }
                                if (!data.getCng().isEmpty()) {
                                    list.add(new TransportReleaseBean("cng", "CNG", data.getCng()));
                                }
                                mAdapter.setNewData(list);
                            }
                        }
                );
            } else if (mOilsDetailBean != null) {
                HttpUtils.oilsDetail(mActivity, mOilsDetailBean.getId(), new JsonCallback<BaseBean<OilsDetailBean>>() {
                            @Override
                            public void onSuccess(Response<BaseBean<OilsDetailBean>> response) {
                                OilsDetailBean data = response.body().getData();
                                mOilsDetailBean = data;
                                etEnterpriseName.setText(data.getEnterpriseName());
                                etEnterpriceLeader.setText(data.getEnterpriceLeader());
                                etEnterpricePhone.setText(data.getEnterpricePhone());
                                etPickUpAddr.setText(data.getPickUpAddr());
                                etSendAddr.setText(data.getSendAddr());
                                otherRemark.setText(data.getOtherRemark());
                                ArrayList<TransportReleaseBean> list = new ArrayList<>();
                                //"ninetytwo", "ninetyfive", "ninetyeight", "zero", "ten", "twenty", "lng", "cng"
                                if (!data.getNinetytwo().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetytwo", "92#", data.getNinetytwo()));
                                }
                                if (!data.getNinetyfive().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetyfive", "95#", data.getNinetyfive()));
                                }
                                if (!data.getNinetyeight().isEmpty()) {
                                    list.add(new TransportReleaseBean("ninetyeight", "98#", data.getNinetyeight()));
                                }
                                if (!data.getZero().isEmpty()) {
                                    list.add(new TransportReleaseBean("zero", "0#", data.getZero()));
                                }
                                if (!data.getTen().isEmpty()) {
                                    list.add(new TransportReleaseBean("ten", "-10#", data.getTen()));
                                }
                                if (!data.getTwenty().isEmpty()) {
                                    list.add(new TransportReleaseBean("twenty", "-20#", data.getTwenty()));
                                }
                                if (!data.getLng().isEmpty()) {
                                    list.add(new TransportReleaseBean("lng", "LNG", data.getLng()));
                                }
                                if (!data.getCng().isEmpty()) {
                                    list.add(new TransportReleaseBean("cng", "CNG", data.getCng()));
                                }
                                mAdapter.setNewData(list);
                            }
                        }
                );
            }
        }
    }


    @Override
    public void initView() {
        mType = getIntent().getIntExtra("type", 0);
        if (mType != 0) {
            switch (mType) {
                case 2://发布找车信息接口
                    type = false;
                    rbCarSave.setChecked(false);
                    rbOilsSave.setChecked(true);
                    llImage.setVisibility(View.GONE);
                    llPickUpAddress.setVisibility(View.VISIBLE);
                    llReceivingAddress.setVisibility(View.VISIBLE);
                    llCarModel.setVisibility(View.GONE);
                    llCarNumber.setVisibility(View.GONE);
                    otherRemark.setHint("可填写是否加急，用车时间等问题");
                    break;
                case 1://发布拉货信息接口
                    type = true;
                    rbCarSave.setChecked(true);
                    rbOilsSave.setChecked(false);
                    llImage.setVisibility(View.VISIBLE);
                    llPickUpAddress.setVisibility(View.GONE);
                    llReceivingAddress.setVisibility(View.GONE);
                    llCarModel.setVisibility(View.VISIBLE);
                    llCarNumber.setVisibility(View.VISIBLE);
                    otherRemark.setHint("可填写运输路线，车辆空闲时间，优势等说明");
                    break;
            }
        }
        rgSave.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rb_carSave://发布拉货信息接口
                        type = true;
                        llImage.setVisibility(View.VISIBLE);
                        llPickUpAddress.setVisibility(View.GONE);
                        llReceivingAddress.setVisibility(View.GONE);
                        llCarModel.setVisibility(View.VISIBLE);
                        llCarNumber.setVisibility(View.VISIBLE);
                        otherRemark.setHint("可填写运输路线，车辆空闲时间，优势等说明");
                        break;
                    case R.id.rb_oilsSave://发布找车信息接口
                        type = false;
                        llImage.setVisibility(View.GONE);
                        llPickUpAddress.setVisibility(View.VISIBLE);
                        llReceivingAddress.setVisibility(View.VISIBLE);
                        llCarModel.setVisibility(View.GONE);
                        llCarNumber.setVisibility(View.GONE);
                        otherRemark.setHint("可填写是否加急，用车时间等问题");
                        break;
                }
            }
        });
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
    }

    private void saveInfo() {
        if (type) {
            mCarDetailBean.setEnterpriseName(etEnterpriseName.getText().toString().trim());
            mCarDetailBean.setEnterpriceLeader(etEnterpriceLeader.getText().toString().trim());
            mCarDetailBean.setEnterpricePhone(etEnterpricePhone.getText().toString().trim());
            mCarDetailBean.setCarModel(etCarModel.getText().toString().trim());
            mCarDetailBean.setCarLicense(etCarLicense.getText().toString().trim());
            if (mAdapter.getData().size() != 0) {
                if (mAdapter.getData().get(0).getWeight().isEmpty()) {
                    ToastUtils.showShort("请输入可运输货品！");
                    return;
                }
            }
            for (TransportReleaseBean bean : mAdapter.getData()) {
                switch (bean.getOil()) {
                    case "ninetytwo":
                        mCarDetailBean.setNinetytwo(bean.getWeight());
                        break;
                    case "ninetyfive":
                        mCarDetailBean.setNinetyfive(bean.getWeight());
                        break;
                    case "ninetyeight":
                        mCarDetailBean.setNinetyeight(bean.getWeight());
                        break;
                    case "zero":
                        mCarDetailBean.setZero(bean.getWeight());
                        break;
                    case "ten":
                        mCarDetailBean.setTen(bean.getWeight());
                        break;
                    case "twenty":
                        mCarDetailBean.setTwenty(bean.getWeight());
                        break;
                    case "lng":
                        mCarDetailBean.setLng(bean.getWeight());
                        break;
                    case "cng":
                        mCarDetailBean.setCng(bean.getWeight());
                        break;
                }
            }
            mCarDetailBean.setOtherRemark(otherRemark.getText().toString().trim());
            ArrayList<UploadPictureBean> uploadPictureBeans = new ArrayList<>();
            if (imgFile1_path != null && !imgFile1_path.isEmpty()) {
                uploadPictureBeans.add(new UploadPictureBean(19, imgFile1_path, ""));
            } else {
                if (mCarDetailBean.getImg1() == null || mCarDetailBean.getImg1().isEmpty()) {
                    ToastUtils.showShort("请上传行驶证！");
                    return;
                }
            }
            if (imgFile2_path != null && !imgFile2_path.isEmpty()) {
                uploadPictureBeans.add(new UploadPictureBean(20, imgFile2_path, ""));
            } else {
                if (mCarDetailBean.getImg2() == null || mCarDetailBean.getImg2().isEmpty()) {
                    ToastUtils.showShort("请上传危险品运输证！");
                    return;
                }
            }
            UploadPictureUtils.uploadPicture(mActivity, uploadPictureBeans, new UploadPictureUtils.ConversionSuccess() {
                @Override
                public void FileDownloadSuccess(ArrayList<UploadPictureBean> list) {
                    for (UploadPictureBean pictureBean : list) {
                        if (StringUtils.equals(pictureBean.getFilePath(), imgFile1_path)) {
                            mCarDetailBean.setImg1(pictureBean.getDownloadFile());
                        } else if (StringUtils.equals(pictureBean.getFilePath(), imgFile2_path)) {
                            mCarDetailBean.setImg2(pictureBean.getDownloadFile());
                        }
                    }
                    HttpUtils.carSave(mActivity, mCarDetailBean, new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            finish();
                        }
                    });
                }
            });

        } else {
            mOilsDetailBean.setEnterpriseName(etEnterpriseName.getText().toString().trim());
            mOilsDetailBean.setEnterpriceLeader(etEnterpriceLeader.getText().toString().trim());
            mOilsDetailBean.setEnterpricePhone(etEnterpricePhone.getText().toString().trim());
            if (mAdapter.getData().size() != 0) {
                if (mAdapter.getData().get(0).getWeight().isEmpty()) {
                    ToastUtils.showShort("请输入可运输货品！");
                    return;
                }
            }
            for (TransportReleaseBean bean : mAdapter.getData()) {
                switch (bean.getOil()) {
                    case "ninetytwo":
                        mOilsDetailBean.setNinetytwo(bean.getWeight());
                        break;
                    case "ninetyfive":
                        mOilsDetailBean.setNinetyfive(bean.getWeight());
                        break;
                    case "ninetyeight":
                        mOilsDetailBean.setNinetyeight(bean.getWeight());
                        break;
                    case "zero":
                        mOilsDetailBean.setZero(bean.getWeight());
                        break;
                    case "ten":
                        mOilsDetailBean.setTen(bean.getWeight());
                        break;
                    case "twenty":
                        mOilsDetailBean.setTwenty(bean.getWeight());
                        break;
                    case "lng":
                        mOilsDetailBean.setLng(bean.getWeight());
                        break;
                    case "cng":
                        mOilsDetailBean.setCng(bean.getWeight());
                        break;
                }
            }
            mOilsDetailBean.setOtherRemark(otherRemark.getText().toString().trim());
            mOilsDetailBean.setPickUpAddr(etPickUpAddr.getText().toString().trim());
            mOilsDetailBean.setSendAddr(etSendAddr.getText().toString().trim());
            HttpUtils.oilsSave(mActivity, mOilsDetailBean, new DialogCallback<BaseBean>(mActivity) {
                @Override
                public void onSuccess(Response<BaseBean> response) {
                    finish();
                }
            });
        }
    }

    public static void start(Activity activity, int type, CarDetailBean carDetailBean, OilsDetailBean oilsDetailBean) {
        Intent intent = new Intent(activity, TransportReleaseActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("carDetailBean", carDetailBean);
        intent.putExtra("oilsDetailBean", oilsDetailBean);
        activity.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() != 0) {
                        if (file) {
                            imgFile1_path = selectList.get(0).getPath();
                            GlideUtils.LoadImage(imgFile1_path, imgFile1);
                        } else {
                            imgFile2_path = selectList.get(0).getPath();
                            GlideUtils.LoadImage(imgFile2_path, imgFile2);
                        }
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.imgFile1, R.id.imgFile2, R.id.tv_save, R.id.tv_delete, R.id.tv_receipt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgFile1:
                file = true;
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.imgFile2:
                file = false;
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_save:
                saveInfo();
                break;
            case R.id.tv_delete:
                if (mCarDetailBean != null) {
                    HttpUtils.carOperator(mActivity, mCarDetailBean.getId(), "0", new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            ToastUtils.showShort("删除" + response.body().getMsg());
                            finish();
                        }
                    });
                } else if (mOilsDetailBean != null) {
                    HttpUtils.oilsOperator(mActivity, mOilsDetailBean.getId(), "0", new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            ToastUtils.showShort(response.body().getMsg());
                            finish();
                        }
                    });
                }
                break;
            case R.id.tv_receipt:
                if (mCarDetailBean != null) {
                    HttpUtils.carOperator(mActivity, mCarDetailBean.getId(), "2", new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            ToastUtils.showShort(response.body().getMsg());
                            finish();
                        }
                    });
                } else if (mOilsDetailBean != null) {
                    HttpUtils.oilsOperator(mActivity, mOilsDetailBean.getId(), "2", new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            ToastUtils.showShort(response.body().getMsg());
                            finish();
                        }
                    });
                }
                break;
        }
    }
}
