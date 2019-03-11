package com.qmy.yzsw.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.InformationBean;
import com.qmy.yzsw.bean.TodayPriceListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OilMsgActivity extends BaseActivity {

    @BindView(R.id.tv_oil_name)
    TextView tvOilName;
    @BindView(R.id.tv_oil_address)
    TextView tvOilAddress;
    @BindView(R.id.ninetytwo)
    TextView tvNinetytwo;
    @BindView(R.id.ninetyfive)
    TextView tvNinetyfive;
    @BindView(R.id.ninetyeight)
    TextView tvNinetyeight;
    @BindView(R.id.zero)
    TextView tvZero;
    @BindView(R.id.ten)
    TextView tvTen;
    @BindView(R.id.twenty)
    TextView tvTwenty;
    @BindView(R.id.lng)
    TextView tvLng;
    @BindView(R.id.cng)
    TextView tvCng;
    @BindView(R.id.charging)
    TextView tvCharging;
    private TodayPriceListBean mTodayPriceListBean;
    private double mCurrentLon;
    private double mCurrentLat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_oil_msg;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTodayPriceListBean = getIntent().getParcelableExtra("todayPriceListBean");
        mCurrentLat = getIntent().getDoubleExtra("currentLat", 0);
        mCurrentLon = getIntent().getDoubleExtra("currentLon", 0);
        setimageRightText(R.mipmap.image_white_navigation);
        mImageRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KUtils.startNavi(mActivity,
                        new LatLng(mCurrentLat, mCurrentLon),
                        "",
                        new LatLng(mTodayPriceListBean.getLatitude(),
                                mTodayPriceListBean.getLongitude()),
                        "");
            }
        });
    }


    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("油站详情");
        HttpUtils.detail(mActivity, mTodayPriceListBean.getId(), new DialogCallback<BaseBean<InformationBean>>(mActivity) {
            @Override
            public void onSuccess(Response<BaseBean<InformationBean>> response) {
                InformationBean mBean = response.body().getData();
                tvOilName.setText(mBean.getEnterpriseName());
                tvOilAddress.setText(mBean.getOiladdress());
                mBean = response.body().getData();
                String ninetytwo = mBean.getNinetytwo();
                tvNinetytwo.setText(ninetytwo.equals("-1") ? "无" : ninetytwo + "元/升");
                String ninetyfive = mBean.getNinetyfive();
                tvNinetyfive.setText(ninetyfive.equals("-1") ? "无" : ninetyfive + "元/升");
                String ninetyeight = mBean.getNinetyeight();
                tvNinetyeight.setText(ninetyeight.equals("-1") ? "无" : ninetyeight + "元/升");
                String zero = mBean.getZero();
                tvZero.setText(zero.equals("-1") ? "无" : zero + "元/升");
                String ten = mBean.getTen();
                tvTen.setText(ten.equals("-1") ? "无" : ten + "元/升");
                String twenty = mBean.getTwenty();
                tvTwenty.setText(twenty.equals("-1") ? "无" : twenty + "元/升");
                String lng = mBean.getLng();
                tvLng.setText(lng.equals("-1") ? "无" : lng + "元/kg");
                String cng = mBean.getCng();
                tvCng.setText(cng.equals("-1") ? "无" : cng + "元/m³");
                String charging = mBean.getCharging();
                tvCharging.setText(charging.equals("-1") ? "无" : charging + "元/度");
            }
        });
    }

    public static void start(BaseActivity activity, TodayPriceListBean todayPriceListBean, double currentLat, double currentLon) {
        Intent intent = new Intent(activity, OilMsgActivity.class);
        intent.putExtra("todayPriceListBean", todayPriceListBean);
        intent.putExtra("currentLat", currentLat);
        intent.putExtra("currentLon", currentLon);
        activity.startActivity(intent);
    }
}
