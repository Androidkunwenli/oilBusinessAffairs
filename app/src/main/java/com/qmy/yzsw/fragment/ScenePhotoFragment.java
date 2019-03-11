package com.qmy.yzsw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.qmy.yzsw.adapter.ImageSceneAdapter;
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

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class ScenePhotoFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView mRecList;
    private int mPosition;
    private boolean mIsVisibleToUser;
    private ImageSceneAdapter mAdapter;
    private ImageUploadBean uploadBean = new ImageUploadBean();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scene_photo;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        Log.i(TAG, "setUserVisibleHint: " + isVisibleToUser);
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
    public void OnClickBean(final OnClickBean bean) {
        if (!mIsVisibleToUser) {
            return;
        }
        getImageUploadBean();
    }

    private int mInt = 0;

    /**
     * 图片上传
     */
    private synchronized void getImageUploadBean() {
        StringBuilder buffer = new StringBuilder();
        List<ImageUploadBean> data = mAdapter.getData();
        for (int i = 6; i < data.size(); i++) {
            String file15 = data.get(i).getFile15();
            if (file15 != null && !file15.isEmpty()) {
                buffer.append(file15).append(",");
            }
        }
        uploadBean.setFile15(buffer.toString());
        HttpUtils.sceneSave(getActivity(), uploadBean.getFile9(), uploadBean.getFile10(), uploadBean.getFile11(),
                uploadBean.getFile12(), uploadBean.getFile13(), uploadBean.getFile14(), uploadBean.getFile15(),
                new DialogCallback<BaseBean>(getActivity()) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        ToastUtils.showShort(response.body().getMsg());
                    }
                });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRecList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new ImageSceneAdapter(getActivity());
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
    public void initView() {
        final ArrayList<ImageUploadBean> data = new ArrayList<>();
        //;10站点全景照;11站点正面照;12站点左面照;13站点右面照;14便利店照;15营业室照;16站点场景其它
        HttpUtils.getSceneInfo(getActivity(), new JsonCallback<BaseBean<ImageUploadBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<ImageUploadBean>> response) {
                if (response.isSuccessful()) {
                    BaseBean<ImageUploadBean> body = response.body();
                    uploadBean = body.getData();
                    if (body.getCode() == 1) {
                        ImageUploadBean bean = new ImageUploadBean("站点全景", R.mipmap.ic_upload_photo_b, null, true, 10);
                        bean.setFile9(body.getData().getFile9());
                        bean.setSeeImage(bean.getFile9());
                        data.add(bean);
                        ImageUploadBean bean1 = new ImageUploadBean("站点正面", R.mipmap.ic_upload_photo_b, null, true, 11);
                        bean1.setFile10(body.getData().getFile10());
                        bean1.setSeeImage(bean1.getFile10());
                        data.add(bean1);
                        ImageUploadBean bean2 = new ImageUploadBean("站点左面", R.mipmap.ic_upload_photo_b, null, true, 12);
                        bean2.setFile11(body.getData().getFile11());
                        bean2.setSeeImage(bean2.getFile11());
                        data.add(bean2);
                        ImageUploadBean bean3 = new ImageUploadBean("站点右面", R.mipmap.ic_upload_photo_b, null, true, 13);
                        bean3.setFile12(body.getData().getFile12());
                        bean3.setSeeImage(bean3.getFile12());
                        data.add(bean3);
                        ImageUploadBean bean5 = new ImageUploadBean("便利店内全景", R.mipmap.ic_upload_photo_b, null, true, 14);
                        bean5.setFile13(body.getData().getFile13());
                        bean5.setSeeImage(bean5.getFile13());
                        data.add(bean5);
                        ImageUploadBean bean4 = new ImageUploadBean("营业室正面", R.mipmap.ic_upload_photo_b, null, true, 15);
                        bean4.setFile14(body.getData().getFile14());
                        bean4.setSeeImage(bean4.getFile14());
                        data.add(bean4);
                        String file15 = body.getData().getFile15();
                        if (file15 != null && !file15.isEmpty()) {
                            for (String str : file15.split(",")) {
                                if (!str.isEmpty()) {
                                    ImageUploadBean uploadBean = new ImageUploadBean("", R.mipmap.ic_add_image, null, false, 16);
                                    uploadBean.setFile15(str);
                                    uploadBean.setSeeImage(uploadBean.getFile15());
                                    data.add(uploadBean);
                                }
                            }
                        }
                        ImageUploadBean add = new ImageUploadBean("", R.mipmap.ic_add_image, null, false, 16);
                        add.setAdd(true);
                        data.add(add);
                        mAdapter.setNewData(data);
                    }
                }
            }
        });
    }

    private static final String TAG = "ScenePhotoFragment";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (!mIsVisibleToUser) {
                        return;
                    }
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "onActivityResult: 图片地址 ： " + new Gson().toJson(selectList));
                    if (selectList != null && selectList.size() != 0 && mAdapter != null) {
                        final ImageUploadBean uploadBean = mAdapter.getData().get(mPosition);
                        uploadBean.setUpload_image(selectList.get(0).getPath());
                        switch (mPosition) {
                            case 0:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 10, uploadBean.getFile10(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile9(response.body().getData());
                                    }
                                });
                                break;
                            case 1:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 11, uploadBean.getFile1(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile10(response.body().getData());
                                    }
                                });
                                break;
                            case 2:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 12, uploadBean.getFile11(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile11(response.body().getData());
                                    }
                                });
                                break;
                            case 3:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 13, uploadBean.getFile12(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile12(response.body().getData());
                                    }
                                });
                                break;
                            case 4:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 14, uploadBean.getFile13(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile13(response.body().getData());
                                    }
                                });
                                break;
                            case 5:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 15, uploadBean.getFile14(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        ScenePhotoFragment.this.uploadBean.setFile14(response.body().getData());
                                    }
                                });
                                break;
                            default:
                                KUtils.uploadFile(getActivity(), uploadBean.getUpload_image(), 16, uploadBean.getFile9(), new DialogCallback<BaseBean<String>>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<BaseBean<String>> response) {
                                        uploadBean.setOpType(16);
                                        uploadBean.setFile15(response.body().getData());
                                        uploadBean.setSeeImage(uploadBean.getFile15());
                                        if (mAdapter.getData().get(mAdapter.getData().size() - 1).getFile15() != null) {
                                            ImageUploadBean add = new ImageUploadBean("",
                                                    R.mipmap.ic_add_image,
                                                    null, false, 16);
                                            add.setAdd(true);
                                            mAdapter.addData(add);
                                        }
                                    }
                                });
                                break;
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
