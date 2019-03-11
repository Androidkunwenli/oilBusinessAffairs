package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.LeaseReleaseActivity;
import com.qmy.yzsw.bean.LeaseBean;
import com.qmy.yzsw.utils.GlideUtils;

public class LeaseAdapter extends BaseQuickAdapter<LeaseBean, BaseViewHolder> {

    private  String mLeasetype;
    private FragmentActivity mActivity;

    public LeaseAdapter(FragmentActivity activity, String leasetype) {
        super(R.layout.item_lease);
        mActivity = activity;
        mLeasetype = leasetype;
    }

    @Override
    protected void convert(BaseViewHolder helper, final LeaseBean item) {
        GlideUtils.LoadImage(item.getImgUrl(), (ImageView) helper.getView(R.id.image_message));
        helper.setText(R.id.tv_oil_name, item.getName())
                .setText(R.id.tv_oil_address, item.getAddress())
                .setText(R.id.tv_oil_time, item.getPublishTime());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaseReleaseActivity.start(mActivity,item,mLeasetype);
            }
        });
    }
}
