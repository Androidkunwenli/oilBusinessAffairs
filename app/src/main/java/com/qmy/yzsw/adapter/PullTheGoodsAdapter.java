package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.CarDetailBean;

public class PullTheGoodsAdapter extends BaseQuickAdapter<CarDetailBean, BaseViewHolder> {

    private String str;
    private int myself;

    public PullTheGoodsAdapter(FragmentActivity activity, int myself, String str) {
        super(R.layout.item_pull_the_goods);
        this.myself = myself;
        this.str = str;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarDetailBean item) {
        helper.setText(R.id.createTime, item.getCreateTime())
                .setText(R.id.carCapacity, item.getCarCapacity())
                .setText(R.id.carModel, item.getCarModel())
                .setText(R.id.enterpriseName, item.getEnterpriseName());
        if (myself == 1) {
            helper.setText(R.id.tv_state, StringUtils.equals("2", item.getState()) ? str : "")
                    .setGone(R.id.image_state, StringUtils.equals(item.getMyself(), "1") && !StringUtils.equals("2", item.getState()))
                    .addOnClickListener(R.id.image_state);
        }
    }
}
