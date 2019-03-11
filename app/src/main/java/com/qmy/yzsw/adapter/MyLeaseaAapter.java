package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.LeaseReleaseActivity;
import com.qmy.yzsw.bean.LeaseBean;

public class MyLeaseaAapter extends BaseQuickAdapter<LeaseBean, BaseViewHolder> {

    private String mLeasetype;
    private FragmentActivity mActivity;

    public MyLeaseaAapter(FragmentActivity activity, String leasetype) {
        super(R.layout.item_my_lease);
        mActivity = activity;
        mLeasetype = leasetype;
    }

    //approveStatus (integer, optional): 审核状态：1审核通过；0待审核;-1拒绝通过 ,
    //myself (string, optional): 是否自己：1是；0否
    @Override
    protected void convert(BaseViewHolder helper, final LeaseBean item) {
        helper.setText(R.id.tv_oil_name, item.getName())
                .setText(R.id.tv_oil_address, item.getAddress())
                .setText(R.id.tv_time, item.getPublishTime())
                .setGone(R.id.image_no_complete, StringUtils.equals(item.getLeasetype1(), "1"))
                .addOnClickListener(R.id.image_no_complete);

        switch (item.getLeasetype1()) {
            case "1":
                helper.setText(R.id.tv_complete, "");
                break;
//            case "0":
//                helper.setText(R.id.tv_complete, "待审核   ");
//                break;
            case "2":
                helper.setText(R.id.tv_complete, "已成交");
                break;
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaseReleaseActivity.start(mActivity, item, mLeasetype);
            }
        });
    }
}
