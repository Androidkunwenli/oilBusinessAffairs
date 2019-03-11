package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class SubmissionReportFormActivity extends BaseActivity {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    private ArrayList<Integer> list = new ArrayList<>();
    private BaseQuickAdapter<Integer, BaseViewHolder> adapter;

    {
        list.add(R.mipmap.ic_refined_oil_sales);
        list.add(R.mipmap.ic_hidden_danger);
        list.add(R.mipmap.ic_fill_up_the_account);
        list.add(R.mipmap.ic_vehicle_urea);
    }

    public static void start(BaseActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, SubmissionReportFormActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_submission_report_form;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_submission_report_form, list) {
            @Override
            protected void convert(BaseViewHolder helper, Integer item) {
                helper.setImageResource(R.id.image_file, item);
            }
        };
        recList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0://成品油销售台账
                        RefinedOilSalesActivity.start(mActivity);
                        break;
                    case 1://安全隐患台账
                        HiddenDangerActivity.start(mActivity);
                        break;
                    case 2://加油站进油台账
                        FillUpTheAccountActivity.start(mActivity);
                        break;
                    case 3://车用尿素进货台账
                        VehicleUreaActivity.start(mActivity);
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("报表提交");
        setimageRightText(R.mipmap.ic_my_reportform);
        mImageRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySubmissionReportFormActivity.start(mActivity);
            }
        });
    }
}
