package com.qmy.yzsw.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.ScreenBean;

import java.util.ArrayList;

public class ReportFormAdmPopupWindow extends PopupWindow {

    private EditText mEtSearch;
    private Context context;
    private BaseQuickAdapter<ScreenBean, BaseViewHolder> mAdapter;
    private TextView tvLayoutSort;
    private TextView tvLayoutFileResource;

    public ReportFormAdmPopupWindow(final Context context, ArrayList<ScreenBean> screenBeans, TextView tvLayoutSort, TextView tvLayoutFileResource, EditText mEtSearch) {
        super(context);
        this.context = context;
        this.tvLayoutSort = tvLayoutSort;
        this.tvLayoutFileResource = tvLayoutFileResource;
        this.mEtSearch = mEtSearch;
        mEtSearch.setCursorVisible(false);
        mEtSearch.setFocusable(false);
        mEtSearch.setFocusableInTouchMode(false);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.poptransparent)));
        View contentView = LayoutInflater.from(context).inflate(R.layout.activity_sort,
                null, false);
        setContentView(contentView);
        RecyclerView reclist = (RecyclerView) contentView.findViewById(R.id.rec_list);
        reclist.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new BaseQuickAdapter<ScreenBean, BaseViewHolder>(R.layout.item_screen, screenBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ScreenBean item) {
                helper.setImageResource(R.id.image_message, item.getScreenImage())
                        .setText(R.id.tv_name, item.getScreenStr())
                        .setTextColor(R.id.tv_name, context.getResources().getColor(item.isChoice() ? R.color.colorPrimary : R.color.gray))
                        .setVisible(R.id.image_choice, item.isChoice());
            }
        };
        reclist.setAdapter(mAdapter);
    }

    public void setListener(BaseQuickAdapter.OnItemClickListener listener) {
        if (listener != null && mAdapter != null) {
            mAdapter.setOnItemClickListener(listener);
        }
    }

    public BaseQuickAdapter<ScreenBean, BaseViewHolder> getAdapter() {
        return mAdapter;
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void dismiss() {
        if (tvLayoutSort != null) {
            tvLayoutSort.setTextColor(context.getResources().getColor(R.color.black));
            tvLayoutSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
        }
        if (tvLayoutFileResource != null) {
            tvLayoutFileResource.setTextColor(context.getResources().getColor(R.color.black));
            tvLayoutFileResource.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_default_screen_choice, 0);
        }
        if (mEtSearch != null) {
            mEtSearch.setCursorVisible(true);
            mEtSearch.setFocusable(true);
            mEtSearch.setFocusableInTouchMode(true);
        }
        super.dismiss();
    }
}
