package com.qmy.yzsw.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.FileListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.db.FileDaUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class OnlineFileActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.ff_layout)
    FrameLayout ffLayout;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    private FileListBean fileListBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_on_line_file;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        fileListBean = getIntent().getParcelableExtra("fileListBean");
        switch (getIntent().getIntExtra("type", 0)) {
            case 1:
                ffLayout.setVisibility(View.GONE);
                break;
            default:
                ffLayout.setVisibility(View.VISIBLE);
                break;
        }
        if (fileListBean == null) {
            ToastUtils.showShort("打开出错");
            finish();
        }
        setFindViewById(true);
        setTitleStr(fileListBean.getFileName());
        KUtils.startJavascript(webView);
        webView.setWebViewClient(new AppWebViewClients());
        webView.loadUrl(fileListBean.getFileHtml());

    }

    @OnClick({R.id.ff_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ff_layout:
                final ProgressDialog progressDialog = FileDaUtils.showUpdateAskDialog(mActivity, fileListBean.getFileName());
                FileDaUtils.getInstance(mActivity).getFilePath(fileListBean, new FileDaUtils.FileIdListener() {
                    @Override
                    public void onSuccess(String path, FileListBean bean) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        ToastUtils.showShort("下载成功 ：" + "download/" + bean.getFileName());
//                        new OpenFileUtil().openFile(mActivity, path);
                        HttpUtils.download(mActivity, fileListBean.getId(), new JsonCallback<BaseBean>() {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                            }
                        });
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
    }

    public class AppWebViewClients extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

    }

    @Override
    public void initView() {

    }

    public static void start(Activity mActivity, FileListBean fileListBean, int i) {
        Intent intent = new Intent(mActivity, OnlineFileActivity.class);
        intent.putExtra("fileListBean", fileListBean);
        intent.putExtra("type", i);
        mActivity.startActivity(intent);
    }
}
