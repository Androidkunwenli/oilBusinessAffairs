package com.qmy.yzsw.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.TransportMsgAdapter;
import com.qmy.yzsw.bean.CarDetailBean;
import com.qmy.yzsw.bean.OilsDetailBean;
import com.qmy.yzsw.bean.TransportMsgBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class TransportMsgActivity extends BaseActivity {

    @BindView(R.id.et_enterpriseName)
    TextView etEnterpriseName;
    @BindView(R.id.et_enterpriceLeader)
    TextView etEnterpriceLeader;
    @BindView(R.id.et_enterpricePhone)
    TextView etEnterpricePhone;
    @BindView(R.id.et_carModel)
    TextView etCarModel;
    @BindView(R.id.ll_car_model)
    LinearLayout llCarModel;
    @BindView(R.id.et_carLicense)
    TextView etCarLicense;
    @BindView(R.id.ll_car_number)
    LinearLayout llCarNumber;
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.et_pickUpAddr)
    TextView etPickUpAddr;
    @BindView(R.id.ll_pick_up_address)
    LinearLayout llPickUpAddress;
    @BindView(R.id.et_sendAddr)
    TextView etSendAddr;
    @BindView(R.id.ll_receiving_address)
    LinearLayout llReceivingAddress;
    @BindView(R.id.imgFile1)
    ImageView imgFile1;
    @BindView(R.id.imgFile2)
    ImageView imgFile2;
    @BindView(R.id.ll_image)
    LinearLayout llImage;
    @BindView(R.id.otherRemark)
    TextView otherRemark;
    @BindView(R.id.ll_myself)
    LinearLayout ll_myself;
    @BindView(R.id.tv_receipt)
    TextView tv_receipt;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private CarDetailBean mCarDetailBean;
    private OilsDetailBean mOilsDetailBean;
    private TransportMsgAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transport_msg_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        mCarDetailBean = getIntent().getParcelableExtra("carDetailBean");
        mOilsDetailBean = getIntent().getParcelableExtra("oilsDetailBean");
        setTitleStr(mCarDetailBean != null && mOilsDetailBean == null ? "拉货详情" : "找车详情");
        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new TransportMsgAdapter(mActivity);
        recList.setAdapter(mAdapter);

        setTvRightText("举报");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("温馨提示")
                        .setMessage("您确认要举报此条信息吗？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id;
                                if (mCarDetailBean != null) {
                                    id = mCarDetailBean.getId();
                                    HttpUtils.carOperator(mActivity, id, String.valueOf(4), new DialogCallback<BaseBean>(mActivity) {
                                        @Override
                                        public void onSuccess(Response<BaseBean> response) {
                                            ToastUtils.showShort("举报" + response.body().getMsg());
                                        }
                                    });
                                } else if (mOilsDetailBean != null) {
                                    id = mOilsDetailBean.getId();
                                    HttpUtils.oilsOperator(mActivity, id, String.valueOf(4), new DialogCallback<BaseBean>(mActivity) {
                                        @Override
                                        public void onSuccess(Response<BaseBean> response) {
                                            ToastUtils.showShort("举报" + response.body().getMsg());
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    @Override
    public void initView() {
        if (mCarDetailBean != null) {
            llCarModel.setVisibility(View.VISIBLE);
            llCarNumber.setVisibility(View.VISIBLE);
            llPickUpAddress.setVisibility(View.GONE);
            llReceivingAddress.setVisibility(View.GONE);
            llImage.setVisibility(View.VISIBLE);
            mAdapter.setType(1);
            if (mCarDetailBean.getMyself() != null && !mCarDetailBean.getMyself().isEmpty()) {
                ll_myself.setVisibility(mCarDetailBean.getMyself().equals("1") ? View.VISIBLE : View.GONE);
            }
            HttpUtils.carDetail(mActivity, mCarDetailBean.getId(), new JsonCallback<BaseBean<CarDetailBean>>() {
                        @Override
                        public void onSuccess(Response<BaseBean<CarDetailBean>> response) {
                            CarDetailBean data = response.body().getData();
                            etEnterpriseName.setText(data.getEnterpriseName());
                            etEnterpriceLeader.setText(data.getEnterpriceLeader());
                            etEnterpricePhone.setText(data.getEnterpricePhone());
                            etCarModel.setText(data.getCarModel());
                            etCarLicense.setText(data.getCarLicense());
                            otherRemark.setText(data.getOtherRemark());
                            GlideUtils.LoadImage(data.getImg1(), imgFile1);
                            GlideUtils.LoadImage(data.getImg2(), imgFile2);
                            ArrayList<TransportMsgBean> list = new ArrayList<>();
                            if (!data.getNinetytwo().isEmpty()) {
                                list.add(new TransportMsgBean("92#", data.getNinetytwo()));
                            }
                            if (!data.getNinetyfive().isEmpty()) {
                                list.add(new TransportMsgBean("95#", data.getNinetyfive()));
                            }
                            if (!data.getNinetyeight().isEmpty()) {
                                list.add(new TransportMsgBean("98#", data.getNinetyeight()));
                            }
                            if (!data.getZero().isEmpty()) {
                                list.add(new TransportMsgBean("0 #", data.getZero()));
                            }
                            if (!data.getTen().isEmpty()) {
                                list.add(new TransportMsgBean("-10#", data.getTen()));
                            }
                            if (!data.getTwenty().isEmpty()) {
                                list.add(new TransportMsgBean("-20#", data.getTwenty()));
                            }
                            if (!data.getLng().isEmpty()) {
                                list.add(new TransportMsgBean("LNG", data.getLng()));
                            }
                            if (!data.getCng().isEmpty()) {
                                list.add(new TransportMsgBean("CNG", data.getCng()));
                            }
                            mAdapter.setNewData(list);
                        }
                    }
            );
        } else if (mOilsDetailBean != null) {
            llCarModel.setVisibility(View.GONE);
            llCarNumber.setVisibility(View.GONE);
            llPickUpAddress.setVisibility(View.VISIBLE);
            llReceivingAddress.setVisibility(View.VISIBLE);
            llImage.setVisibility(View.GONE);
            mAdapter.setType(2);
            if (mOilsDetailBean.getMyself() != null && !mOilsDetailBean.getMyself().isEmpty()) {
                ll_myself.setVisibility(mOilsDetailBean.getMyself().equals("1") ? View.VISIBLE : View.GONE);
            }
            HttpUtils.oilsDetail(mActivity, mOilsDetailBean.getId(), new JsonCallback<BaseBean<OilsDetailBean>>() {
                        @Override
                        public void onSuccess(Response<BaseBean<OilsDetailBean>> response) {
                            OilsDetailBean data = response.body().getData();
                            etEnterpriseName.setText(data.getEnterpriseName());
                            etEnterpriceLeader.setText(data.getEnterpriceLeader());
                            etEnterpricePhone.setText(data.getEnterpricePhone());
                            etPickUpAddr.setText(data.getPickUpAddr());
                            etSendAddr.setText(data.getSendAddr());
                            otherRemark.setText(data.getOtherRemark());
                            ArrayList<TransportMsgBean> list = new ArrayList<>();
                            if (!data.getNinetytwo().isEmpty()) {
                                list.add(new TransportMsgBean("92#", data.getNinetytwo()));
                            }
                            if (!data.getNinetyfive().isEmpty()) {
                                list.add(new TransportMsgBean("95#", data.getNinetyfive()));
                            }
                            if (!data.getNinetyeight().isEmpty()) {
                                list.add(new TransportMsgBean("98#", data.getNinetyeight()));
                            }
                            if (!data.getZero().isEmpty()) {
                                list.add(new TransportMsgBean("0 #", data.getZero()));
                            }
                            if (!data.getTen().isEmpty()) {
                                list.add(new TransportMsgBean("-10#", data.getTen()));
                            }
                            if (!data.getTwenty().isEmpty()) {
                                list.add(new TransportMsgBean("-20#", data.getTwenty()));
                            }
                            if (!data.getLng().isEmpty()) {
                                list.add(new TransportMsgBean("LNG", data.getLng()));
                            }
                            if (!data.getCng().isEmpty()) {
                                list.add(new TransportMsgBean("CNG", data.getCng()));
                            }
                            mAdapter.setNewData(list);
                        }
                    }
            );
        }
    }

    public static void start(FragmentActivity activity, CarDetailBean carDetailBean, OilsDetailBean oilsDetailBean) {
        Intent intent = new Intent(activity, TransportMsgActivity.class);
        intent.putExtra("carDetailBean", carDetailBean);
        intent.putExtra("oilsDetailBean", oilsDetailBean);
        activity.startActivity(intent);
    }

    @OnClick({R.id.tv_save, R.id.tv_delete, R.id.tv_receipt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                ToastUtils.showShort("保存");
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
                            ToastUtils.showShort("删除" + response.body().getMsg());
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
                        }
                    });
                } else if (mOilsDetailBean != null) {
                    HttpUtils.oilsOperator(mActivity, mOilsDetailBean.getId(), "2", new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            ToastUtils.showShort(response.body().getMsg());
                        }
                    });
                }
                break;
        }
    }
}
