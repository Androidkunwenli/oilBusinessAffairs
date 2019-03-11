package com.qmy.yzsw.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.FillUpTheAccountActivity;
import com.qmy.yzsw.activity.HiddenDangerActivity;
import com.qmy.yzsw.activity.RefinedOilSalesActivity;
import com.qmy.yzsw.activity.VehicleUreaActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.ScreenBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.view.ReportFormAdmPopupWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MySubmissionReportFormFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_layout_sort)
    TextView tvLayoutSort;
    @BindView(R.id.ll_layout_sort)
    LinearLayout llLayoutSort;
    @BindView(R.id.tv_layout_file_resource)
    TextView tvLayoutFileResource;
    @BindView(R.id.ll_layout_file_resource)
    LinearLayout llLayoutFileResource;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_stop_date)
    TextView tvStopDate;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.ll_time_date)
    LinearLayout llTimeDate;
    private BaseQuickAdapter<MySubmissionReportFormListBean, BaseViewHolder> adapter;
    private ArrayList<ScreenBean> fileResource = new ArrayList<>();

    {
        fileResource.add(new ScreenBean("", "全部", R.mipmap.ic_baobiao, 2, false));
        fileResource.add(new ScreenBean("1", "成品油销售台账", R.mipmap.ic_baobiao, 2, false));
        fileResource.add(new ScreenBean("2", "安全隐患台账", R.mipmap.ic_baobiao, 2, false));
        fileResource.add(new ScreenBean("3", "加油站进油台账", R.mipmap.ic_baobiao, 2, false));
        fileResource.add(new ScreenBean("4", "车用尿素进货台账", R.mipmap.ic_baobiao, 2, false));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseQuickAdapter<MySubmissionReportFormListBean, BaseViewHolder>(R.layout.item_my_submission) {
            @Override
            protected void convert(BaseViewHolder helper, MySubmissionReportFormListBean item) {
                helper.setText(R.id.tv_file_name, item.getName()).setText(R.id.tv_file_time, item.getCreateTime());
            }
        };
        recList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData();
            }
        }, recList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (llTimeDate.getVisibility() == View.VISIBLE) {
                    return;
                }
                MySubmissionReportFormListBean bean = (MySubmissionReportFormListBean) adapter.getData().get(position);
                switch (bean.getName()) {
                    case "成品油销售台账":
                        RefinedOilSalesActivity.start(getActivity(), bean);
                        break;
                    case "安全隐患台账":
                        HiddenDangerActivity.start(getActivity(), bean);
                        break;
                    case "加油站进油台账":
                        FillUpTheAccountActivity.start(getActivity(), bean);
                        break;
                    case "车用尿素进货台账":
                        VehicleUreaActivity.start(getActivity(), bean);
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 0;
        start = "";
        stop = "";
        initData();
    }

    public void initData() {
        HttpUtils.myList(getActivity(), fileResourceStr, page += 1, start, stop, etSearch.getText().toString().trim(), new JsonCallback<BaseBean<List<MySubmissionReportFormListBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<MySubmissionReportFormListBean>>> response) {
                BaseBean<List<MySubmissionReportFormListBean>> body = response.body();
                if (body.getCode() == 1) {
                    if (page == 1) {
                        adapter.setNewData(body.getData());
                    } else {
                        List<MySubmissionReportFormListBean> data = body.getData();
                        if (!data.isEmpty()) {
                            adapter.addData(data);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initView() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    getFileList();
                    return true;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSearch.getText().toString().trim().isEmpty()) {
                    getFileList();
                }
            }
        });
    }

    private void showPopup(final ArrayList<ScreenBean> screenBeans, final int indexType) {
        final ReportFormAdmPopupWindow popupWindow = new ReportFormAdmPopupWindow(getActivity(), screenBeans, tvLayoutSort, tvLayoutFileResource, etSearch);
        popupWindow.setListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (ScreenBean screenBean : screenBeans) {
                    screenBean.setChoice(false);
                }
                ScreenBean bean = (ScreenBean) adapter.getData().get(position);
                bean.setChoice(true);
                if (indexType == 1) {
                    tvLayoutSort.setText(bean.getScreenStr());
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
                } else if (indexType == 2) {
                    tvLayoutFileResource.setText(bean.getScreenStr());
                    tvLayoutFileResource.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
                }
                screenBeans.set(position, bean);
                adapter.notifyItemChanged(position);
                popupWindow.dismiss();
                getFileList();
            }
        });
        popupWindow.showAsDropDown(llLayout);
        if (indexType == 1) {
            tvLayoutSort.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
        } else if (indexType == 2) {
            tvLayoutFileResource.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvLayoutFileResource.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
        }
    }

    private String fileResourceStr;
    private String start;
    private String stop;

    private void getFileList() {
        for (ScreenBean screenBean : fileResource) {
            if (screenBean.isChoice()) {
                fileResourceStr = screenBean.getScreen();
            }
        }
        page = 0;
        initData();
    }

    @OnClick({R.id.ll_layout_sort, R.id.ll_layout_file_resource, R.id.tv_submission, R.id.tv_start_date, R.id.tv_stop_date})
    public void onViewClicked(View view) {
        KeyboardUtils.hideSoftInput(getActivity());
        switch (view.getId()) {
            case R.id.ll_layout_sort:
                if (llTimeDate.getVisibility() == View.VISIBLE) {
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
                    llTimeDate.setVisibility(View.GONE);
                } else {
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
                    llTimeDate.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_layout_file_resource:
                showPopup(fileResource, 2);
                if (llTimeDate.getVisibility() == View.VISIBLE) {
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
                    llTimeDate.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_start_date:
                TimePickerView build = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] split = timeStr.split("-");
                        tvStartDate.setText(split[0] + "年" + split[1] + "月" + split[2] + "日");
                        MySubmissionReportFormFragment.this.start = timeStr;
                    }
                }).build();
                build.show();
                break;
            case R.id.tv_stop_date:
                TimePickerView build1 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] split = timeStr.split("-");
                        tvStopDate.setText(split[0] + "年" + split[1] + "月" + split[2] + "日");
                        MySubmissionReportFormFragment.this.stop = timeStr;
                    }
                }).build();
                build1.show();
                break;
            case R.id.tv_submission:
                page = 0;
                initData();
                if (llTimeDate.getVisibility() == View.VISIBLE) {
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
                    llTimeDate.setVisibility(View.GONE);
                } else {
                    tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
                    llTimeDate.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
