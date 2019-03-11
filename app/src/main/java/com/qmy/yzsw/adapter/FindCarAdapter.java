package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.OilsDetailBean;

public class FindCarAdapter extends BaseQuickAdapter<OilsDetailBean, BaseViewHolder> {

    private String str;
    private int myself;

    public FindCarAdapter(FragmentActivity activity, int myself, String str) {
        super(R.layout.item_find_car);
        this.myself = myself;
        this.str = str;
    }

    @Override
    protected void convert(BaseViewHolder helper, OilsDetailBean item) {
        helper.setText(R.id.sendAddr, item.getSendAddr())
                .setText(R.id.pickUpAddr, item.getPickUpAddr())
                .setText(R.id.createTime, item.getCreateTime())
                .setText(R.id.carCapacity, item.getCarCapacity())
                .setText(R.id.enterpriseName, item.getEnterpriseName());
        if (myself == 1) {
            helper.setText(R.id.tv_state, StringUtils.equals("2", item.getState()) ? str : "")
                    .setGone(R.id.image_state, StringUtils.equals(item.getMyself(), "1") && !StringUtils.equals("2", item.getState()))
                    .addOnClickListener(R.id.image_state);
        }

    }
}
