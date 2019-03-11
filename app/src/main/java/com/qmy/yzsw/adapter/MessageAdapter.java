package com.qmy.yzsw.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.SmsBean;

public class MessageAdapter extends BaseQuickAdapter<SmsBean, BaseViewHolder> {
    public MessageAdapter(BaseActivity activity) {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmsBean item) {
        helper.setText(R.id.tv_msg, item.getSmsContent())
                .setText(R.id.tv_time, "系统消息 " + item.getSendTime());
    }
}
