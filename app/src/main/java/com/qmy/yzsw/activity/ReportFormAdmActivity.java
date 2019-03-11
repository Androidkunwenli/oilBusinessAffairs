package com.qmy.yzsw.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.ReportFormAdmAdapter;
import com.qmy.yzsw.bean.FileListBean;
import com.qmy.yzsw.bean.ScreenBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.db.FileDaUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.OpenFileUtil;
import com.qmy.yzsw.view.ReportFormAdmPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportFormAdmActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.ll_layout)
    LinearLayout ll_layout;
    @BindView(R.id.tv_layout_sort)
    TextView tvLayoutSort;
    @BindView(R.id.ll_layout_sort)
    LinearLayout llLayoutSort;
    @BindView(R.id.tv_layout_file_resource)
    TextView tvLayoutFileResource;
    @BindView(R.id.ll_layout_file_resource)
    LinearLayout llLayoutFileResource;
    @BindView(R.id.tv_submission_report_form)
    TextView tv_submission_report_form;
    private ReportFormAdmAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private ReportFormAdmPopupWindow popupWindow;
    private ProgressDialog progressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_report_from_adm;
    }

    private ArrayList<ScreenBean> fileResource = new ArrayList<>();
    private ArrayList<ScreenBean> sort = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        setimageRightText(R.mipmap.ic_my_reportform);
        mImageRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySubmissionReportFormActivity.start(mActivity);
            }
        });
        fileResource.add(new ScreenBean("", "全部", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("1", "商务局", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("2", "税务局", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("3", "消防局", R.mipmap.ic_shangwuju, 2, false));

        sort.add(new ScreenBean("1", "时间排序", R.mipmap.ic_time, 1, false));
        sort.add(new ScreenBean("2", "名称排序", R.mipmap.ic_mingcheng, 1, false));

        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ReportFormAdmAdapter(0);
        recList.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final FileListBean fileListBean = (FileListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.image_download:
                        progressDialog = FileDaUtils.showUpdateAskDialog(mActivity, fileListBean.getFileName());
                        FileDaUtils.getInstance(mActivity).getFilePath(fileListBean, new FileDaUtils.FileIdListener() {
                            @Override
                            public void onSuccess(String path, FileListBean bean) {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                ToastUtils.showShort("下载成功 ：" + "download/" + bean.getFileName());
                                HttpUtils.download(mActivity, fileListBean.getId(), new JsonCallback<BaseBean>() {
                                    @Override
                                    public void onSuccess(Response<BaseBean> response) {
                                    }
                                });
//                        new OpenFileUtil().openFile(mActivity, path);
                            }

                            @Override
                            public void downloadProgress(Progress progress) {
                                if (progressDialog != null) {
                                    if (progress.fraction == 1.0) {
                                        progressDialog.dismiss();
                                    } else {
                                        progressDialog.setProgress((int) (progress.fraction * 10000));
                                    }
                                }
                            }

                            @Override
                            public void onError() {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                            }

                        });

                        break;
                    case R.id.ll_layout:
                        switch (fileListBean.getFileExt()) {
                            case "pdf":
                            case "doc":
                            case "ppt":
                            case "docx":
                            case "xlsx":
                                OnlineFileActivity.start(mActivity, fileListBean, 0);
                                break;
                            case "mp4":
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                String type = "video/* ";
                                Uri uri = Uri.parse(fileListBean.getFileUrl());
                                intent.setDataAndType(uri, type);
                                startActivity(intent);
                                break;
                            case "rar":
                            case "zip":
                            default:
                                progressDialog = FileDaUtils.showUpdateAskDialog(mActivity, fileListBean.getFileName());
                                FileDaUtils.getInstance(mActivity).getFilePath(fileListBean, new FileDaUtils.FileIdListener() {
                                    @Override
                                    public void onSuccess(String path, FileListBean bean) {
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                        new OpenFileUtil().openFile(mActivity, path);
                                    }

                                    @Override
                                    public void downloadProgress(Progress progress) {
                                        if (progressDialog != null) {
                                            if (progress.fraction == 1.0) {
                                                progressDialog.dismiss();
                                            } else {
                                                progressDialog.setProgress((int) (progress.fraction * 10000));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                                break;
                        }
                        break;
                }

            }
        });
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("报表管理");
        getFileList();

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    getFileList();
                    return true;
                }
                return false;
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtSearch.getText().toString().trim().isEmpty()) {
                    getFileList();
                }
            }
        });
    }

    private static final String TAG = "ReportFormAdmActivity";

    private String fileResourceStr;
    private String sortStr = "1";

    private void getFileList() {
        for (ScreenBean screenBean : fileResource) {
            if (screenBean.isChoice()) {
                fileResourceStr = screenBean.getScreen();
            }
        }
        for (ScreenBean screenBean : sort) {
            if (screenBean.isChoice()) {
                sortStr = screenBean.getScreen();
            }
        }
        HttpUtils.fileList(mActivity, 0, mEtSearch.getText().toString().trim(), fileResourceStr, sortStr, 0,
                new JsonCallback<BaseBean<List<FileListBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseBean<List<FileListBean>>> response) {
                        mAdapter.setNewData(response.body().getData());
                    }
                });
    }

    public static void start(FragmentActivity activity) {
        Intent intent = new Intent(activity, ReportFormAdmActivity.class);
        activity.startActivity(intent);
    }

    private void showPopup(final ArrayList<ScreenBean> screenBeans, final int indexType) {
        popupWindow = new ReportFormAdmPopupWindow(mActivity, screenBeans, tvLayoutSort, tvLayoutFileResource, mEtSearch);
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
        popupWindow.showAsDropDown(ll_layout);
        if (indexType == 1) {
            tvLayoutSort.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
        } else if (indexType == 2) {
            tvLayoutFileResource.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvLayoutFileResource.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_screen_choice, 0);
        }
    }

    @OnClick({R.id.ll_layout_sort, R.id.ll_layout_file_resource, R.id.tv_submission_report_form})
    public void onViewClicked(View view) {
        KeyboardUtils.hideSoftInput(mActivity);
        switch (view.getId()) {
            case R.id.ll_layout_sort:
                showPopup(sort, 1);
                break;
            case R.id.ll_layout_file_resource:
                showPopup(fileResource, 2);
                break;
            case R.id.tv_submission_report_form:
                SubmissionReportFormActivity.start(mActivity);
                break;
        }
    }
}
