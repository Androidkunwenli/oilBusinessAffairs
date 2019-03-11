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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.ScreenBean;

import java.util.ArrayList;
import java.util.Date;

public class ReportFormTimePopupWindow extends PopupWindow {

    private EditText mEtSearch;
    private Context context;
    private TextView tvLayoutSort;
    private TextView tvLayoutFileResource;
    private final TextView tv_start_date;
    private final TextView tv_stop_date;

    public ReportFormTimePopupWindow(final Context context, TextView tvLayoutSort, TextView tvLayoutFileResource, EditText mEtSearch, final OnClickListener listener) {
        super(context);
        this.context = context;
        this.tvLayoutSort = tvLayoutSort;
        this.tvLayoutFileResource = tvLayoutFileResource;
        this.mEtSearch = mEtSearch;
        mEtSearch.setCursorVisible(false);
        mEtSearch.setFocusable(false);
        mEtSearch.setFocusableInTouchMode(false);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.poptransparent)));
        View contentView = View.inflate(context, R.layout.activity_time, null);
        setContentView(contentView);
        tv_start_date = contentView.findViewById(R.id.tv_start_date);
        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView build = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] split = timeStr.split("-");
                        tv_start_date.setText(split[0] + "年" + split[1] + "月" + split[2] + "日");
                    }
                }).build();
                build.show();
            }
        });
        tv_stop_date = contentView.findViewById(R.id.tv_stop_date);
        tv_stop_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView build = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String timeStr = TimeUtils.date2String(date).split(" ")[0];
                        String[] split = timeStr.split("-");
                        tv_stop_date.setText(split[0] + "年" + split[1] + "月" + split[2] + "日");
                    }
                }).build();
                build.show();
            }
        });
        if (listener != null) {
            contentView.findViewById(R.id.tv_submission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String start = tv_start_date.getText().toString().trim();
                    String stop = tv_stop_date.getText().toString().trim();
                    if (start.isEmpty()) {
                        ToastUtils.showShort("开始时间不能为空！");
                        return;
                    }
                    if (stop.isEmpty()) {
                        ToastUtils.showShort("结束时间不能为空！");
                        return;
                    }
                    listener.onClick(start, stop);
                    dismiss();
                }
            });
        }

    }

    public interface OnClickListener {
        void onClick(String start, String stop);
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
