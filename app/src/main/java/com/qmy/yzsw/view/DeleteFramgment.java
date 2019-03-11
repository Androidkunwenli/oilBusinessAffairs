package com.qmy.yzsw.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qmy.yzsw.R;


/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class DeleteFramgment extends DialogFragment {
    private TextView btn_cancle;
    private TextView btn_ok;
    private View.OnClickListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delete_name, container, false);
        btn_cancle = (TextView) v.findViewById(R.id.btn_cancle);
        btn_ok = (TextView) v.findViewById(R.id.btn_ok);
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

    public void setOnItemChildClickListener(View.OnClickListener listener) {
        mListener = listener;
    }
}
