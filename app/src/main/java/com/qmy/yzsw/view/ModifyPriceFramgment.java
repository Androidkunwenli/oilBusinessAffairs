package com.qmy.yzsw.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmy.yzsw.R;
import com.qmy.yzsw.widget.CashierInputFilter;


/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class ModifyPriceFramgment extends DialogFragment {
    private TextView btn_cancle;
    private TextView tv_title_str;
    private TextView btn_ok;
    public EditText et_price;
    private View.OnClickListener mListener;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        btn_cancle = (TextView) v.findViewById(R.id.btn_cancle);
        tv_title_str = (TextView) v.findViewById(R.id.tv_title_str);
        btn_ok = (TextView) v.findViewById(R.id.btn_ok);
        et_price = (EditText) v.findViewById(R.id.et_price);
        et_price.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        tv_title_str.setText(title);
        if (mListener != null) {
            btn_cancle.setOnClickListener(mListener);
            btn_ok.setOnClickListener(mListener);
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setOnItemChildClickListener(String trim, View.OnClickListener listener) {
        mListener = listener;
        title = trim;
    }
}
