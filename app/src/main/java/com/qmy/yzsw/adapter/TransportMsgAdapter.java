package com.qmy.yzsw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.TransportMsgBean;

public class TransportMsgAdapter extends BaseQuickAdapter<TransportMsgBean, BaseViewHolder> {

    private int mType;

    public TransportMsgAdapter(Activity activity) {
        super(R.layout.item_transport_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransportMsgBean item) {
        helper.setText(R.id.tv_weight, item.getWeight())
                .setText(R.id.spinner_choice_1, item.getOilName());
    }

    public void setType(int type) {
        this.mType = type;
    }
}
