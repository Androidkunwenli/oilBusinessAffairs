package com.qmy.yzsw.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.InformationEntryActivity;
import com.qmy.yzsw.activity.LeaseReleaseActivity;
import com.qmy.yzsw.activity.LicenceRemindActivity;
import com.qmy.yzsw.activity.LicenceUploadActivity;
import com.qmy.yzsw.activity.LocationAdministrationActivity;
import com.qmy.yzsw.activity.ReportFormAdmActivity;
import com.qmy.yzsw.activity.SuperviseAdmActivity;
import com.qmy.yzsw.activity.TransportReleaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.OilStationAdapter;

import butterknife.BindView;

public class OilStationFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_oil_station;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        OilStationAdapter adapter = new OilStationAdapter(getActivity());
        recList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i(TAG, "onItemClick: " + OilStationAdapter.str[position]);
                switch (position) {
                    case 0://信息录入
                        InformationEntryActivity.start(getActivity(), true);
                        break;
                    case 1://证照上传
                        LicenceUploadActivity.start(getActivity(), 1);
                        break;
                    case 2://证照提醒
                        LicenceRemindActivity.start(getActivity());
                        break;
                    case 3://定位管理
                        LocationAdministrationActivity.start(getActivity());
                        break;
                    case 4://监督管理
                        SuperviseAdmActivity.start(getActivity());
                        break;
                    case 5://报表管理
                        ReportFormAdmActivity.start(getActivity());
                        break;
                    case 6://租赁发布
                        LeaseReleaseActivity.start(getActivity(), null, null);
                        break;
                    case 7://我要找车
                        TransportReleaseActivity.start(getActivity(), 2, null, null);
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {

    }
}
