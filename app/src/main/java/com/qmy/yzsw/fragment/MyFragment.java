package com.qmy.yzsw.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AlertDialogLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.AboutUsActivity;
import com.qmy.yzsw.activity.CollectionActivity;
import com.qmy.yzsw.activity.InformationEntryActivity;
import com.qmy.yzsw.activity.LeaseActivity;
import com.qmy.yzsw.activity.LicenceUploadActivity;
import com.qmy.yzsw.activity.LoginActivity;
import com.qmy.yzsw.activity.ModifyOilPriceActivity;
import com.qmy.yzsw.activity.MyLeaseActivity;
import com.qmy.yzsw.activity.SettingActivity;
import com.qmy.yzsw.activity.TransportActivity;
import com.qmy.yzsw.activity.TransportReleaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.bean.MyIndexBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.view.EditNameFramgment;
import com.youth.xframe.common.XActivityStack;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseFragment {
    @BindView(R.id.image_user_head)
    CircleImageView imageUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_youzhanxinxi)
    TextView tvYouzhanxinxi;
    @BindView(R.id.tv_youzhenzhengzhao)
    TextView tvYouzhenzhengzhao;
    @BindView(R.id.tv_xiugaiyoujia)
    TextView tvXiugaiyoujia;
    @BindView(R.id.tv_wodezulin)
    TextView tvWodezulin;
    @BindView(R.id.tv_wodezhaoche)
    TextView tvWodezhaoche;
    @BindView(R.id.tv_lahuoguanli)
    TextView tvLahuoguanli;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;
    @BindView(R.id.ll_layout)
    LinearLayout ll_layout;
    private EditNameFramgment mEditNameFramgment;
    private String mHeadPortrait;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_layout.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenHeight() / 4;
        ll_layout.setLayoutParams(layoutParams);
        HttpUtils.myIndex(getActivity(), new JsonCallback<BaseBean<MyIndexBean>>() {

            @Override
            public void onSuccess(Response<BaseBean<MyIndexBean>> response) {
                MyIndexBean data = response.body().getData();
                mHeadPortrait = data.getHeadPortrait();
                Glide.with(getActivity()).load(mHeadPortrait).apply(new RequestOptions().error(R.mipmap.ic_logo)).into(imageUserHead);
                tvUserName.setText(data.getNickname());
                tvUserPhone.setText(data.getMobileNo());
            }
        });
    }

    @Override
    public void initView() {
        mEditNameFramgment = new EditNameFramgment();
        mEditNameFramgment.setOnItemChildClickListener("修改昵称", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_cancle:
                        mEditNameFramgment.dismiss();
                        break;
                    case R.id.btn_ok:
                        final String price = mEditNameFramgment.et_price.getText().toString().trim();
                        if (price.isEmpty()) {
                            ToastUtils.showShort("请输入要修改的昵称");
                            return;
                        }
                        HttpUtils.nickNameSave(getActivity(), price, new JsonCallback<BaseBean>() {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                                tvUserName.setText(price);
                                mEditNameFramgment.dismiss();
                            }
                        });
                        break;
                }
            }
        });

    }

    @OnClick({R.id.image_user_head, R.id.tv_user_name, R.id.tv_user_phone, R.id.tv_youzhanxinxi,
            R.id.tv_youzhenzhengzhao, R.id.tv_xiugaiyoujia, R.id.tv_wodezulin, R.id.tv_wodezhaoche,
            R.id.tv_lahuoguanli, R.id.tv_collection, R.id.tv_setting, R.id.tv_phone, R.id.tv_sign_out, R.id.tv_user_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_edit:
                if (mEditNameFramgment != null) {
                    mEditNameFramgment.show(getActivity().getSupportFragmentManager(), "mEditNameFramgment");
                }
                break;
            case R.id.image_user_head:
                PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_user_name:
                break;
            case R.id.tv_user_phone:
                break;
            case R.id.tv_youzhanxinxi:
                InformationEntryActivity.start(getActivity(), false);
                break;
            case R.id.tv_youzhenzhengzhao:
                LicenceUploadActivity.start(getActivity(),1);
                break;
            case R.id.tv_xiugaiyoujia:
                ModifyOilPriceActivity.start(getActivity());
                break;
            case R.id.tv_wodezulin:
                MyLeaseActivity.start(getActivity(), 1);
                break;
            case R.id.tv_wodezhaoche:
                TransportActivity.start(getActivity(), 1, 1, true);
                break;
            case R.id.tv_lahuoguanli:
                TransportActivity.start(getActivity(), 1, 0, true);
                break;
            case R.id.tv_collection:
                CollectionActivity.start(getActivity());
                break;
            case R.id.tv_setting:
                SettingActivity.start(getActivity());
                break;
            case R.id.tv_phone:
                AboutUsActivity.start(getActivity());
                break;
            case R.id.tv_sign_out:
                new AlertDialog.Builder(getActivity())
                        .setTitle("温馨提示")
                        .setMessage("确定退出登陆吗?")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HttpUtils.loginOut(getActivity());
                                SPUtils.getInstance().clear();
                                LoginActivity.start(getActivity());
                                JPushInterface.stopPush(getActivity());//停止推送消息
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
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
                        final String path = selectList.get(0).getPath();
                        KUtils.uploadFile(getActivity(), path, 21, mHeadPortrait, new DialogCallback<BaseBean<String>>(getActivity()) {
                            @Override
                            public void onSuccess(Response<BaseBean<String>> response) {
                                final String imgHead = response.body().getData();
                                HttpUtils.imgHeadSave(getActivity(), imgHead, new JsonCallback<BaseBean>() {
                                    @Override
                                    public void onSuccess(Response<BaseBean> response) {
                                        ToastUtils.showShort(response.body().getMsg());
                                        Glide.with(getActivity())
                                                .load(imgHead)
                                                .apply(new RequestOptions().placeholder(R.mipmap.ic_logo)
                                                        .error(R.mipmap.ic_logo))
                                                .into(imageUserHead);
                                    }
                                });
                            }
                        });
                    }
                    break;
            }
        }
    }
}
