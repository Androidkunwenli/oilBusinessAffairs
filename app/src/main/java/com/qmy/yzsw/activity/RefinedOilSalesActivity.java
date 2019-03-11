package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.RefinedOilSalesAdapter;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.OilsSaleInfoBean;
import com.qmy.yzsw.bean.RefinedOilSalesBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.OnClick;

public class RefinedOilSalesActivity extends BaseActivity {
    @BindView(R.id.tv_saleDate)
    TextView tvSaleDate;
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    private RefinedOilSalesAdapter adapter;
    private String id;
    private MySubmissionReportFormListBean bean;

    public static void start(BaseActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, RefinedOilSalesActivity.class));
    }

    public static void start(FragmentActivity activity, MySubmissionReportFormListBean bean) {
        Intent intent = new Intent(activity, RefinedOilSalesActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refined_oil_sales;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("加油站（点）成品油销售台账");
        bean = getIntent().getParcelableExtra("bean");
        if (bean != null) {
            id = bean.getId();
            HttpUtils.oilsSaleInfo(mActivity, bean.getId(), new JsonCallback<BaseBean<OilsSaleInfoBean>>() {
                @Override
                public void onSuccess(Response<BaseBean<OilsSaleInfoBean>> response) {
                    OilsSaleInfoBean data = response.body().getData();
                    tvSaleDate.setText(data.getSaleDate());
                    List<OilsSaleInfoBean.DetailListBean> detailList = data.getDetailList();
                    ArrayList<RefinedOilSalesBean> list = new ArrayList<>();
                    for (OilsSaleInfoBean.DetailListBean detailListBean : detailList) {
                        list.add(new RefinedOilSalesBean(detailListBean.getOilsType(), detailListBean.getSaleNum(), detailListBean.getPrice(), detailListBean.getTotalPrice()));
                    }
                    if (adapter != null) {
                        adapter.setNewData(list);
                    }
                    etRemark.setText(data.getRemark());
                }
            });
        }

    }

    @Override
    public void initView() {
        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new RefinedOilSalesAdapter(mActivity,bean!=null);
        recList.setAdapter(adapter);
        ArrayList<RefinedOilSalesBean> data = new ArrayList<>();
        data.add(new RefinedOilSalesBean());
        adapter.setNewData(data);
    }

    @OnClick({R.id.tv_saleDate, R.id.tv_submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_saleDate:
                TimePickerView pvTime1 = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        tvSaleDate.setText(timeStr);
                    }
                }).build();
                pvTime1.show();
                break;
            case R.id.tv_submission:
                if (tvSaleDate.getText().toString().isEmpty()) {
                    ToastUtils.showShort("销售日期不能为空！");
                    return;
                }
                if (etRemark.getText().toString().isEmpty()) {
                    ToastUtils.showShort("备注不能为空！");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                for (RefinedOilSalesBean bean : adapter.getData()) {
                    buffer.append(bean.getOilLabel()).append(",")
                            .append(bean.getOilSalesVolume()).append(",")
                            .append(bean.getOilUnitPrice()).append(",")
                            .append(bean.getOilTotalPrice()).append(";");
                }
                for (RefinedOilSalesBean refinedOilSalesBean : adapter.getData()) {
                    if (refinedOilSalesBean.getOilLabel().isEmpty()) {
                        ToastUtils.showShort("销售品类不能为空！");
                        return;
                    }
                    if (refinedOilSalesBean.getOilSalesVolume().isEmpty()) {
                        ToastUtils.showShort("销售（升）不能为空！");
                        return;
                    }
                    if (refinedOilSalesBean.getOilUnitPrice().isEmpty()) {
                        ToastUtils.showShort("销售单价（元/升）不能为空！");
                        return;
                    }
                    if (refinedOilSalesBean.getOilTotalPrice().isEmpty()) {
                        ToastUtils.showShort("销售总价不能为空！");
                        return;
                    }
                }
                HttpUtils.oilsSaleSave(mActivity, id,
                        tvSaleDate.getText().toString().trim(),
                        buffer.toString(),
                        etRemark.getText().toString().trim(),
                        new DialogCallback<BaseBean>(mActivity) {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                                ToastUtils.showShort(response.body().getMsg());
                                finish();
                            }
                        });
                break;
        }
    }
}
