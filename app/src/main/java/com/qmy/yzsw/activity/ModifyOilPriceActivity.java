package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.MyPriceListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.widget.CashierInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyOilPriceActivity extends BaseActivity {
    @BindView(R.id.tv_ninetytwo)
    TextView tvNinetytwo;
    @BindView(R.id.ninetytwo)
    EditText ninetytwo;
    @BindView(R.id.tv_ninetyfive)
    TextView tvNinetyfive;
    @BindView(R.id.ninetyfive)
    EditText ninetyfive;
    @BindView(R.id.tv_ninetyeight)
    TextView tvNinetyeight;
    @BindView(R.id.ninetyeight)
    EditText ninetyeight;
    @BindView(R.id.tv_zero)
    TextView tvZero;
    @BindView(R.id.zero)
    EditText zero;
    @BindView(R.id.tv_ten)
    TextView tvTen;
    @BindView(R.id.ten)
    EditText ten;
    @BindView(R.id.tv_twenty)
    TextView tvTwenty;
    @BindView(R.id.twenty)
    EditText twenty;
    @BindView(R.id.tv_cng)
    TextView tvCng;
    @BindView(R.id.cng)
    EditText cng;
    @BindView(R.id.tv_lng)
    TextView tvLng;
    @BindView(R.id.lng)
    EditText lng;
    @BindView(R.id.tv_charging)
    TextView tvCharging;
    @BindView(R.id.charging)
    EditText charging;
    private MyPriceListBean mBean = new MyPriceListBean();

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_oil_price;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        ninetytwo.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ninetyfive.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ninetyeight.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        zero.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ten.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        twenty.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        lng.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        cng.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        charging.setFilters(new CashierInputFilter[]{new CashierInputFilter()});

        HttpUtils.myPriceList(mActivity, new JsonCallback<BaseBean<MyPriceListBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<MyPriceListBean>> response) {
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

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("修改油价");
        setTvRightText("提交");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ninetytwo1 = ninetytwo.getText().toString().trim();
                if (!ninetytwo1.isEmpty()) {
                    mBean.setNinetytwo(ninetytwo1);
                }
                String ninetyfive1 = ninetyfive.getText().toString().trim();
                if (!ninetyfive1.isEmpty()) {
                    mBean.setNinetyfive(ninetyfive1);
                }
                String ninetyeight1 = ninetyeight.getText().toString().trim();
                if (!ninetyeight1.isEmpty()) {
                    mBean.setNinetyeight(ninetyeight1);
                }
                String zero1 = zero.getText().toString().trim();
                if (!zero1.isEmpty()) {
                    mBean.setZero(zero1);
                }
                String ten1 = ten.getText().toString().trim();
                if (!ten1.isEmpty()) {
                    mBean.setTen(ten1);
                }
                String twenty1 = twenty.getText().toString().trim();
                if (!twenty1.isEmpty()) {
                    mBean.setTwenty(twenty1);
                }
                String lng1 = lng.getText().toString().trim();
                if (!lng1.isEmpty()) {
                    mBean.setLng(lng1);
                }
                String cng1 = cng.getText().toString().trim();
                if (!cng1.isEmpty()) {
                    mBean.setCng(cng1);
                }
                String charging1 = charging.getText().toString().trim();
                if (!charging1.isEmpty()) {
                    mBean.setCharging(charging1);
                }
                HttpUtils.savePriceList(mActivity, mBean, new JsonCallback<BaseBean>() {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        ToastUtils.showShort(response.body().getMsg());
                        finish();
                    }
                });
            }
        });
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, ModifyOilPriceActivity.class));
    }

}
