package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class OilStationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private List<String> mInts;
    private int[] arr = new int[]{R.mipmap.ic_xinxiluru, R.mipmap.ic_zhengjianzhao, R.mipmap.ic_zhengjiantixing, R.mipmap.ic_dingweiguanli, R.mipmap.ic_jianduguanli, R.mipmap.ic_baobiaoguanli, R.mipmap.ic_fabuzulin, R.mipmap.ic_woyaozhaoche};
    public static String[] str = new String[]{"信息录入", "证照上传", "证照提醒", "定位管理", "监督管理", "报表管理", "租赁发布", "我要找车",};

    public OilStationAdapter(FragmentActivity activity) {
        super(R.layout.item_oil_station);
        setNewData(Arrays.asList(str));
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView view = helper.getView(R.id.tv_content);
        view.setText(str[helper.getAdapterPosition()]);
        helper.setImageResource(R.id.image_message, arr[helper.getAdapterPosition()]);
    }
}
