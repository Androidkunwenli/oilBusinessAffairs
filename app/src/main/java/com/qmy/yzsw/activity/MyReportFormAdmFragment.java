package com.qmy.yzsw.activity;

import android.app.Activity;
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
import android.view.LayoutInflater;
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
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.ReportFormAdmAdapter;
import com.qmy.yzsw.bean.FileListBean;
import com.qmy.yzsw.bean.ScreenBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.db.FileDaUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.OpenFileUtil;
import com.qmy.yzsw.view.MoreDialogFragment;
import com.qmy.yzsw.view.ReportFormAdmPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyReportFormAdmFragment extends BaseFragment {
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
    private ProgressDialog progressDialog;
    private Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_report_from_adm;
    }

    private ArrayList<ScreenBean> fileResource = new ArrayList<>();
    private ArrayList<ScreenBean> sort = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity = getActivity();
        tv_submission_report_form.setVisibility(View.GONE);
        fileResource.add(new ScreenBean("", "全部", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("1", "商务局", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("2", "税务局", R.mipmap.ic_shangwuju, 2, false));
        fileResource.add(new ScreenBean("3", "消防局", R.mipmap.ic_shangwuju, 2, false));

        sort.add(new ScreenBean("1", "时间排序", R.mipmap.ic_time, 1, false));
        sort.add(new ScreenBean("2", "名称排序", R.mipmap.ic_mingcheng, 1, false));

        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ReportFormAdmAdapter(1);
        recList.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                final FileListBean fileListBean = (FileListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.image_download:
                        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.dialog_fragment_item_more, null);
                        final MoreDialogFragment fragment = new MoreDialogFragment(mActivity, inflate, true, true);
                        ((TextView) inflate.findViewById(R.id.tv_name)).setText(fileListBean.getFileName());
                        inflate.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UMWeb web = new UMWeb(fileListBean.getFileUrl());
                                web.setTitle(fileListBean.getFileName());
                                web.setDescription(fileListBean.getFileName());
                                switch (fileListBean.getFileExt()) {
                                    case "mp4":
                                        web.setThumb(new UMImage(mActivity, R.mipmap.mp4));
                                        break;
                                    case "docx":
                                        web.setThumb(new UMImage(mActivity, R.mipmap.word));
                                        break;
                                    case "pdf":
                                        web.setThumb(new UMImage(mActivity, R.mipmap.pdf));
                                        break;
                                    case "rar":
                                    case "zip":
                                        web.setThumb(new UMImage(mActivity, R.mipmap.ic_compress));
                                        break;
                                    default:
                                        web.setThumb(new UMImage(mActivity, R.mipmap.ic_file));
                                        break;
                                }
                                new ShareAction(mActivity).withMedia(web)
                                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                                                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                                        .setCallback(umShareListener).open();
                                fragment.dismiss();
                            }
                        });
                        inflate.findViewById(R.id.tv_mail).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog = FileDaUtils.showUpdateAskDialog(mActivity, fileListBean.getFileName());
                                FileDaUtils.getInstance(mActivity).getFilePath(fileListBean, new FileDaUtils.FileIdListener() {
                                    @Override
                                    public void onSuccess(String path, FileListBean bean) {
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                        KUtils.sendMail(mActivity, new File(path));
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
                                fragment.dismiss();
                            }
                        });
                        inflate.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HttpUtils.delete(mActivity, fileListBean.getId(), new JsonCallback<BaseBean>() {
                                    @Override
                                    public void onSuccess(Response<BaseBean> response) {
                                        ToastUtils.showShort(response.body().getMsg());
                                    }
                                });
                                if (fileListBean.getFilePath() != null && !fileListBean.getFilePath().isEmpty()) {
                                    File file = new File(fileListBean.getFilePath());
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                }
                                adapter.getData().remove(position);
                                adapter.notifyItemRemoved(position);
                                fragment.dismiss();
                            }
                        });
                        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragment.dismiss();
                            }
                        });
                        fragment.show();
                        break;
                    case R.id.ll_layout:
                        switch (fileListBean.getFileExt()) {
                            case "pdf":
                            case "doc":
                            case "ppt":
                            case "docx":
                            case "xlsx":
                                OnlineFileActivity.start(mActivity, fileListBean,1);
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

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

    @Override
    public void initView() {
        getView().findViewById(R.id.ff_main_layout).setVisibility(View.GONE);
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
    private String sortStr;

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
        HttpUtils.fileList(mActivity, 0, mEtSearch.getText().toString().trim(), fileResourceStr, sortStr, 1,
                new JsonCallback<BaseBean<List<FileListBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseBean<List<FileListBean>>> response) {
                        mAdapter.setNewData(response.body().getData());
                    }
                });
    }

    private void showPopup(final ArrayList<ScreenBean> screenBeans, final int indexType) {
        final ReportFormAdmPopupWindow popupWindow = new ReportFormAdmPopupWindow(mActivity, screenBeans, tvLayoutSort, tvLayoutFileResource, mEtSearch);
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

    @OnClick({R.id.ll_layout_sort, R.id.ll_layout_file_resource})
    public void onViewClicked(View view) {
        KeyboardUtils.hideSoftInput(mActivity);
        switch (view.getId()) {
            case R.id.ll_layout_sort:
                showPopup(sort, 1);
                break;
            case R.id.ll_layout_file_resource:
                showPopup(fileResource, 2);
                break;
        }
    }
}
