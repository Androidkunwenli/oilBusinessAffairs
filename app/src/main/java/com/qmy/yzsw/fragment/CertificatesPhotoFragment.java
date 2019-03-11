package com.qmy.yzsw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.ImageAdapter;
import com.qmy.yzsw.bean.FillUpTheAccountImageBean;
import com.qmy.yzsw.bean.ImageUploadBean;
import com.qmy.yzsw.bean.OnClickBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.uploadpicture.UploadPictureBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.UploadPictureUtils;
import com.qmy.yzsw.view.SeeImageFramgment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class CertificatesPhotoFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView mRecList;
    private int mPosition;
    private boolean mIsVisibleToUser;
    private ImageAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_certificates_photo;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        Log.i(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRecList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new ImageAdapter(getActivity());
        mRecList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                SeeImageFramgment framgment = new SeeImageFramgment();
                framgment.setUpload((ImageUploadBean) adapter.getData().get(position));
                framgment.setPosition(position);
                framgment.setClickListener(new SeeImageFramgment.OnDeleteClickListener() {
                    @Override
                    public void onDelete(int position) {
                        // TODO: 2019/3/11 删除
                    }
                });
                framgment.show(getFragmentManager(), "framgment");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnClickBean(OnClickBean bean) {
        if (!mIsVisibleToUser) {
            return;
        }
        getImageUploadBean();
    }

    private int mInt;
    private ImageUploadBean uploadBean = new ImageUploadBean();

    /**
     * 图片上传
     */
    private synchronized void getImageUploadBean() {
        StringBuilder buffer = new StringBuilder();
        List<ImageUploadBean> data = mAdapter.getData();
        for (int i = 9; i < data.size(); i++) {
            buffer.append(data.get(i).getFile10()).append(",");
        }
        uploadBean.setFile10(buffer.toString());
        HttpUtils.certificateSave(getActivity(), uploadBean, new DialogCallback<BaseBean>(getActivity()) {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                if (response.isSuccessful()) {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });
    }

    @Override
    public void initView() {
        final ArrayList<ImageUploadBean> data = new ArrayList<>();
        HttpUtils.getCertificateInfo(getActivity(), new JsonCallback<BaseBean<ImageUploadBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<ImageUploadBean>> response) {
                if (response.isSuccessful()) {
                    BaseBean<ImageUploadBean> body = response.body();
                    uploadBean = body.getData();
                    if (body.getCode() == 1) {
                        ImageUploadBean bean = new ImageUploadBean("营业执照", R.mipmap.ic_upload_photo_b, null, true, 1);
                        bean.setFile1(body.getData().getFile1());
                        bean.setSeeImage(bean.getFile1());
                        data.add(bean);
                        ImageUploadBean bean1 = new ImageUploadBean("法人身份证（正面）", R.mipmap.ic_upload_photo_b, null, true, 2);
                        bean1.setFile2(body.getData().getFile2());
                        bean1.setSeeImage(bean1.getFile2());
                        data.add(bean1);
                        ImageUploadBean bean2 = new ImageUploadBean("法人身份证（背面）", R.mipmap.ic_upload_photo_b, null, true, 3);
                        bean2.setFile3(body.getData().getFile3());
                        bean2.setSeeImage(bean2.getFile3());
                        data.add(bean2);
                        ImageUploadBean bean3 = new ImageUploadBean("危险化学品经营许可证", R.mipmap.ic_upload_photo_b, null, true, 4);
                        bean3.setFile4(body.getData().getFile4());
                        bean3.setSeeImage(bean3.getFile4());
                        data.add(bean3);
                        ImageUploadBean bean4 = new ImageUploadBean("成品油许可证", R.mipmap.ic_upload_photo_b, null, true, 5);
                        bean4.setFile5(body.getData().getFile5());
                        bean4.setSeeImage(bean4.getFile5());
                        data.add(bean4);
                        ImageUploadBean bean5 = new ImageUploadBean("法人资格证", R.mipmap.ic_upload_photo_b, null, true, 6);
                        bean5.setFile6(body.getData().getFile6());
                        bean5.setSeeImage(bean5.getFile6());
                        data.add(bean5);
                        ImageUploadBean bean6 = new ImageUploadBean("消防证", R.mipmap.ic_upload_photo_b, null, true, 7);
                        bean6.setFile7(body.getData().getFile7());
                        bean6.setSeeImage(bean6.getFile7());
                        data.add(bean6);
                        ImageUploadBean bean7 = new ImageUploadBean("规划证", R.mipmap.ic_upload_photo, null, false, 8);
                        bean7.setFile8(body.getData().getFile8());
                        bean7.setSeeImage(bean7.getFile8());
                        data.add(bean7);
                        ImageUploadBean bean8 = new ImageUploadBean("土地证", R.mipmap.ic_upload_photo, null, false, 9);
                        bean8.setFile9(body.getData().getFile9());
                        bean8.setSeeImage(bean8.getFile9());
                        data.add(bean8);
                        String file10 = body.getData().getFile10();
                        if (file10 != null && !file10.isEmpty()) {
                            for (String str : file10.split(",")) {
                                if (!str.isEmpty() && str.contains("jpg")) {
                                    ImageUploadBean uploadBean = new ImageUploadBean("", R.mipmap.ic_add_image, null, false, 29);
                                    uploadBean.setFile10(str);
                                    uploadBean.setSeeImage(uploadBean.getFile10());
                                    data.add(uploadBean);
                                }
                            }
                        }
                        ImageUploadBean add = new ImageUploadBean("", R.mipmap.ic_add_image, null, false, 29);
                        add.setAdd(true);
                        data.add(add);
                        mAdapter.setNewData(data);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (!mIsVisibleToUser) {
                        return;
                    }
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() != 0 && mAdapter != null) {
                        final ImageUploadBean uploadBean = mAdapter.getData().get(mPosition);
                        uploadBean.setUpload_image(selectList.get(0).getPath());
                        switch (mPosition) {
                            case 0:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 1, uploadBean.getFile1(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile1(response.body().getData());
                                    }
                                });
                                break;
                            case 1:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 2, uploadBean.getFile2(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile2(response.body().getData());
                                    }
                                });
                                break;
                            case 2:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 3, uploadBean.getFile3(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile3(response.body().getData());
                                    }
                                });
                                break;
                            case 3:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 4, uploadBean.getFile4(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile4(response.body().getData());
                                    }
                                });
                                break;
                            case 4:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 5, uploadBean.getFile5(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile5(response.body().getData());
                                    }
                                });
                                break;
                            case 5:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 6, uploadBean.getFile6(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile6(response.body().getData());
                                    }
                                });
                                break;
                            case 6:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 7, uploadBean.getFile7(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile7(response.body().getData());
                                    }
                                });
                                break;
                            case 7:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 8, uploadBean.getFile8(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile8(response.body().getData());
                                    }
                                });
                                break;
                            case 8:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 9, uploadBean.getFile9(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        CertificatesPhotoFragment.this.uploadBean.setFile9(response.body().getData());
                                    }
                                });
                                break;
                            default:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 29, uploadBean.getFile9(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        uploadBean.setOpType(29);
                                        uploadBean.setFile10(response.body().getData());
                                        uploadBean.setSeeImage(uploadBean.getFile10());
                                        if (mAdapter.getData().get(mAdapter.getData().size() - 1).getFile10() != null) {
                                            ImageUploadBean add = new ImageUploadBean("",
                                                    R.mipmap.ic_add_image,
                                                    null, false, 29);
                                            add.setAdd(true);
                                            mAdapter.addData(add);
                                        }
                                    }
                                });
                                break;
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    private static final String TAG = "CertificatesPhotoFragme";
}
