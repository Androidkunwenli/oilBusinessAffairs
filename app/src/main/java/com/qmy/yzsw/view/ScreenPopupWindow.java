package com.qmy.yzsw.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qmy.yzsw.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class ScreenPopupWindow extends PopupWindow {

    private final View tv_cancel;
    private final View tv_confirm;
    private TagFlowLayout flScreen;//未选择
    private TagFlowLayout flYesScreen;//已选择
    private List<String> mVals;
    private List<String> mVals2;

    public ScreenPopupWindow(final Context context, List<String> mValsTwo, final getDataOnClickListener listener) {
        super(context);
        this.mVals = new ArrayList<>();
        this.mVals2 = new ArrayList<>();
        mVals2.addAll(mValsTwo);
        this.mVals.add("营业执照");
        this.mVals.add("法人资格证");
        this.mVals.add("危化证");
        this.mVals.add("成品油经营许可证");
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.poptransparent)));
        View contentView = LayoutInflater.from(context).inflate(R.layout.activity_screen,
                null, false);
        flScreen = contentView.findViewById(R.id.fl_screen);
        flYesScreen = contentView.findViewById(R.id.fl_yes_screen);
        flScreen.setAdapter(new TagAdapter<String>(ScreenPopupWindow.this.mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tag_flow_layout,
                        flScreen, false);
                tv.setText(o);
                return tv;
            }
        });
        flYesScreen.setAdapter(new TagAdapter<String>(mVals2) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tag_flow_layout,
                        flYesScreen, false);
                tv.setBackgroundResource(R.drawable.dr_problem_feedback_theme);
                tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                tv.setText(o);
                return tv;
            }
        });
        flScreen.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (!mVals2.contains(ScreenPopupWindow.this.mVals.get(position))) {
                    mVals2.add(ScreenPopupWindow.this.mVals.get(position));
                    flYesScreen.getAdapter().notifyDataChanged();
                }
                return true;
            }
        });
        flYesScreen.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mVals2.contains(mVals2.get(position))) {
                    mVals2.remove(mVals2.get(position));
                    flYesScreen.getAdapter().notifyDataChanged();
                }
                return true;
            }
        });
        tv_cancel = contentView.findViewById(R.id.tv_cancel);
        tv_confirm = contentView.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.showData(mVals2);
                    dismiss();
                }
            }
        });
        setContentView(contentView);
    }

    public interface getDataOnClickListener {
        void showData(List<String> data);
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
}
