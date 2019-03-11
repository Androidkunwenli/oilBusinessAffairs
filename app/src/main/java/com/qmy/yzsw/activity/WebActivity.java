package com.qmy.yzsw.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.NewDetailBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.config.Urls;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.rec_list)
    RecyclerView mRecList;
    @BindView(R.id.bt_collection)
    TextView btCollection;
    @BindView(R.id.bt_share)
    TextView btShare;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private boolean isWebView;
    private TextView mTvTitleStr;
    private TextView mTvContentStr;
    private HotNewsBean mBean;
    private NewDetailBean mBodyData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        mBean = intent.getParcelableExtra("bean");
        if (url != null) {
            startUrl(url, null);
        } else if (mBean != null) {
            startNewDetail(mBean);
        }

    }

    /**
     * @param bean 加载新闻详情
     */
    private void startNewDetail(final HotNewsBean bean) {
        setTitleStr(bean.getTitle());
        mWebView.setVisibility(View.GONE);
        mRecList.setVisibility(View.VISIBLE);
        mRecList.setLayoutManager(new LinearLayoutManager(mActivity));
        final BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_new_detail_image) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                Glide.with(mActivity.getApplicationContext())
                        .load(item)
                        .into((ImageView) helper.getView(R.id.image_message));
            }
        };
        adapter.addHeaderView(getInflate());
        mRecList.setAdapter(adapter);
        HttpUtils.newDetail(mActivity, bean.getNewsId(), new JsonCallback<BaseBean<NewDetailBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<NewDetailBean>> response) {
                if (response.isSuccessful()) {
                    BaseBean<NewDetailBean> body = response.body();
                    if (body.getCode() == 1) {
                        startUrl(body.getData().getShareUrl(), body);
                        mBodyData = body.getData();
                        String[] split = mBodyData.getImgsUrl().split(",");
                        List<String> list = Arrays.asList(split);
                        adapter.setNewData(list);
                        mTvTitleStr.setText(mBodyData.getTitle());
                        mTvContentStr.setText(mBodyData.getAddtime() + " " + "浏览" + mBodyData.getViewCnt() + "次");
                        isCollection = StringUtils.equals(mBodyData.getIsCollect(), "1");
                        btCollection.setCompoundDrawablesWithIntrinsicBounds(
                                isCollection ? R.mipmap.ic_web_collection : R.mipmap.ic_collection,
                                0, 0, 0);
                        btCollection.setText(isCollection ? "取消收藏" : "收藏");
                    } else {
                        ToastUtils.showShort(body.getMsg());
                    }
                }
            }
        });
    }

    private View getInflate() {
        View inflate = View.inflate(mActivity, R.layout.item_new_detail_layout, null);
        mTvTitleStr = inflate.findViewById(R.id.tv_title_str);
        mTvContentStr = inflate.findViewById(R.id.tv_content_str);
        return inflate;
    }

    /**
     * 加载URL
     */
    private void startUrl(String url, final BaseBean<NewDetailBean> body) {
        mWebView.setVisibility(View.VISIBLE);
        mRecList.setVisibility(View.GONE);
        KUtils.startJavascript(mWebView);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (body != null) {
                    setTitleStr(body.getData().getTitle());
                } else {
                    if (KUtils.isContainChinese(view.getTitle())) {
                        setTitleStr(view.getTitle());
                    }
                }
                view.loadUrl(url);
                // + KUtils.markByContainQuestion(url) + "t=" + System.currentTimeMillis() + Math.random()
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (body != null) {
                    setTitleStr(body.getData().getTitle());
                } else {
                    if (KUtils.isContainChinese(view.getTitle())) {
                        setTitleStr(view.getTitle());
                    }
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (body != null) {
                    setTitleStr(body.getData().getTitle());
                } else {
                    if (KUtils.isContainChinese(view.getTitle())) {
                        setTitleStr(view.getTitle());
                    }
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);

            }
        });
    }

    @Override
    public void initView() {
        mShareListener = new CustomShareListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(FragmentActivity activity, String linkUrl) {
        if (linkUrl.isEmpty()) {
            return;
        }
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("URL", linkUrl);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, HotNewsBean bean) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    private boolean isCollection;

    @OnClick({R.id.bt_collection, R.id.bt_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_collection:
                isCollection = !isCollection;
                btCollection.setCompoundDrawablesWithIntrinsicBounds(
                        isCollection ? R.mipmap.ic_web_collection : R.mipmap.ic_collection,
                        0, 0, 0);
                btCollection.setText(isCollection ? "取消收藏" : "收藏");
                HttpUtils.newsOperate(mActivity, mBean.getNewsId(), 2, new JsonCallback<BaseBean>() {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        ToastUtils.showShort(isCollection ? "收藏成功" : "取消收藏成功");
                    }
                });
                break;
            case R.id.bt_share:
                if (mBean != null) {
                    UMWeb web = new UMWeb(mBodyData.getShareUrl());
                    web.setTitle(mBodyData.getShareTitle());//标题
                    if (mBodyData.getImgsUrl() != null && !mBodyData.getImgsUrl().isEmpty()) {
                        UMImage umImage = new UMImage(mActivity, mBodyData.getImgsUrl());
                        web.setThumb(umImage);  //缩略图
                    } else {
                        UMImage umImage = new UMImage(mActivity, R.mipmap.ic_yzsw_logo);
                        web.setThumb(umImage);  //缩略图
                    }
                    web.setDescription(mBodyData.getSharedescrib());//描述
                    new ShareAction(WebActivity.this).withMedia(web)
                            .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                                    SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                            .setCallback(mShareListener).open();
                }
                break;
        }
    }

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    private class CustomShareListener implements UMShareListener {

        private WeakReference<WebActivity> mActivity;

        private CustomShareListener(WebActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            HttpUtils.newsOperate(WebActivity.this, mBean.getNewsId(), 3, new JsonCallback<BaseBean>() {
                @Override
                public void onSuccess(Response<BaseBean> response) {

                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
