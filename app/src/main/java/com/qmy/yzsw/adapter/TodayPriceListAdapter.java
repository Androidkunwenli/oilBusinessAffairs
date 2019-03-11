package com.qmy.yzsw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.TodayPriceListBean;
import com.qmy.yzsw.utils.KUtils;

import java.text.DecimalFormat;

public class TodayPriceListAdapter extends BaseQuickAdapter<TodayPriceListBean, BaseViewHolder> {
    public TodayPriceListAdapter(BaseActivity activity) {
        super(R.layout.item_today_price_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayPriceListBean item) {
        /*距离*/
        String distanceStr;
        int distanceInt = item.getDistance();
        float size = distanceInt / 1000f;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        distanceStr = df.format(size) + "km";//返回的是String类型的
        helper.setText(R.id.tv_title_str, item.getOilName())
                .setText(R.id.tv_content_str, item.getOilAddress())
                .setText(R.id.tv_price, "¥ " + item.getOilPrice())
                .setText(R.id.tv_distance_str, distanceStr)
                .setGone(R.id.image_modify_oil_price, item.getMyself().equals("1"))
                .addOnClickListener(R.id.ll_layout)
                .addOnClickListener(R.id.image_modify_oil_price)
                .addOnClickListener(R.id.image_navigation);

    }
}
