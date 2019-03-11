package com.qmy.yzsw.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.SuperviseListBean;
import com.qmy.yzsw.utils.GlideUtils;

public class SuperviseAdmAdapter extends BaseQuickAdapter<SuperviseListBean, BaseViewHolder> {
    public SuperviseAdmAdapter(BaseActivity activity) {
        super(R.layout.item_supervise_adm);
    }

    @Override
    protected void convert(BaseViewHolder helper, SuperviseListBean item) {
        GlideUtils.LoadImage(item.getMyfiles1(), (ImageView) helper.getView(R.id.image_message));
        helper.setText(R.id.tv_oil_name, item.getOilName()).setText(R.id.tv_oil_address, item.getOilAddress());
    }
}
