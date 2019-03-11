package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.SuperviseDetailBean;
import com.qmy.yzsw.bean.SuperviseListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.request.SuperviseSaveBean;
import com.qmy.yzsw.bean.uploadpicture.UploadPictureBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.LocationUtil;
import com.qmy.yzsw.utils.UploadPictureUtils;
import com.qmy.yzsw.widget.CashierInputFilter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SuperviseReleaseActivity extends BaseActivity {
    @BindView(R.id.oilName)
    EditText oilName;
    @BindView(R.id.oilAddress)
    EditText oilAddress;
    @BindView(R.id.size92Price)
    EditText size92Price;
    @BindView(R.id.size95Price)
    EditText size95Price;
    @BindView(R.id.size98Price)
    EditText size98Price;
    @BindView(R.id.size0Price)
    EditText size0Price;
    @BindView(R.id.size10Price)
    EditText size10Price;
    @BindView(R.id.size20Price)
    EditText size20Price;
    @BindView(R.id.latitudeLimit)
    EditText latitudeLimit;
    @BindView(R.id.longitudeLimit)
    EditText longitudeLimit;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.probledFeedback)
    EditText probledFeedback;
    @BindView(R.id.imgFile1)
    ImageView imgFile1;
    @BindView(R.id.imgFile2)
    ImageView imgFile2;
    private SuperviseSaveBean mSuperviseSaveBean = new SuperviseSaveBean();
    private LocationUtil mInstance;
    private SuperviseListBean mListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_supervise_release;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mListBean = getIntent().getParcelableExtra("listBean");
        size92Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        size95Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        size98Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        size0Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        size10Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        size20Price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        mInstance = LocationUtil.getInstance(mActivity);
        mInstance.startLocation();
        mInstance.setBaiduLocatinListener(new LocationUtil.BaiduLocationListener() {
            @Override
            public void onLocationChanged(BDLocation location) {
                mSuperviseSaveBean.setLatitudeLimit(String.valueOf(location.getLatitude()));
                mSuperviseSaveBean.setLongitudeLimit(String.valueOf(location.getLongitude()));
                if (mListBean != null) {
                    return;
                }
                latitudeLimit.setText(String.valueOf(location.getLatitude()));
                longitudeLimit.setText(String.valueOf(location.getLongitude()));
                mInstance.stopLocation();
            }
        });
    }

    private int mInt;

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("发布信息");
        setTvRightText("提交");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<UploadPictureBean> uploadPictureBeans = new ArrayList<>();
                if (imgFile1_path != null && !imgFile1_path.isEmpty()) {
                    uploadPictureBeans.add(new UploadPictureBean(17, imgFile1_path, ""));
                }
                if (imgFile2_path != null && !imgFile2_path.isEmpty()) {
                    uploadPictureBeans.add(new UploadPictureBean(17, imgFile2_path, ""));
                }
                UploadPictureUtils.uploadPicture(mActivity, uploadPictureBeans, new UploadPictureUtils.ConversionSuccess() {
                    @Override
                    public void FileDownloadSuccess(ArrayList<UploadPictureBean> list) {
                        for (UploadPictureBean pictureBean : list) {
                            if (StringUtils.equals(pictureBean.getFilePath(), imgFile1_path)) {
                                mSuperviseSaveBean.setImgFile1(pictureBean.getDownloadFile());
                            } else if (StringUtils.equals(pictureBean.getFilePath(), imgFile2_path)) {
                                mSuperviseSaveBean.setImgFile2(pictureBean.getDownloadFile());
                            }
                        }
                        save();
                    }
                });
            }
        });


        if (mListBean != null) {
            HttpUtils.superviseDetail(mActivity, mListBean.getId(), new DialogCallback<BaseBean<SuperviseDetailBean>>(mActivity) {
                @Override
                public void onSuccess(Response<BaseBean<SuperviseDetailBean>> response) {
                    SuperviseDetailBean data = response.body().getData();
                    oilName.setText(data.getOilName());
                    oilAddress.setText(data.getOilAddress());
                    size92Price.setText(data.getSize92Price());
                    size95Price.setText(data.getSize95Price());
                    size98Price.setText(data.getSize98Price());
                    size0Price.setText(data.getSize0Price());
                    size10Price.setText(data.getSize10Price());
                    size20Price.setText(data.getSize20Price());
                    probledFeedback.setText(data.getProbledFeedback());
                    GlideUtils.LoadImage(data.getMyfiles1(), imgFile1);
                    GlideUtils.LoadImage(data.getMyfiles2(), imgFile2);
                    latitudeLimit.setText(data.getLatitudeLimit());
                    longitudeLimit.setText(data.getLongitudeLimit());
                }
            });
        }
    }

    private void save() {
        if (mListBean != null) {
            mSuperviseSaveBean.setId(mListBean.getId());
        }
        mSuperviseSaveBean.setOilName(oilName.getText().toString().trim());
        mSuperviseSaveBean.setOilAddress(oilAddress.getText().toString().trim());
        mSuperviseSaveBean.setSize92Price(size92Price.getText().toString().trim());
        mSuperviseSaveBean.setSize95Price(size95Price.getText().toString().trim());
        mSuperviseSaveBean.setSize98Price(size98Price.getText().toString().trim());
        mSuperviseSaveBean.setSize0Price(size0Price.getText().toString().trim());
        mSuperviseSaveBean.setSize10Price(size10Price.getText().toString().trim());
        mSuperviseSaveBean.setSize20Price(size20Price.getText().toString().trim());
        mSuperviseSaveBean.setProbledFeedback(probledFeedback.getText().toString().trim());
        HttpUtils.superviseSave(mActivity, mSuperviseSaveBean, new JsonCallback<BaseBean>() {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                ToastUtils.showShort(response.body().getMsg());
                finish();
            }
        });
    }

    public static void start(BaseActivity activity, SuperviseListBean listBean) {
        Intent intent = new Intent(activity, SuperviseReleaseActivity.class);
        intent.putExtra("listBean", listBean);
        activity.startActivity(intent);
    }

    private boolean file = true;
    private String imgFile1_path;
    private String imgFile2_path;

    @OnClick({R.id.imgFile1, R.id.imgFile2})
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
        }
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
}
