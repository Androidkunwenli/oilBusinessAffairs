package com.qmy.yzsw.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.TransportReleaseBean;
import com.qmy.yzsw.widget.CashierInputFilter;

import java.util.ArrayList;

public class TransportReleaseAdapter extends BaseQuickAdapter<TransportReleaseBean, BaseViewHolder> {

    private Activity mActivity;

    public TransportReleaseAdapter(Activity activity) {
        super(R.layout.tiem_rec_list);
        mActivity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TransportReleaseBean item) {
        helper.setVisible(R.id.image_msg, helper.getAdapterPosition() == 0);
        helper.setImageResource(R.id.image_add, helper.getAdapterPosition() != 0 ? R.mipmap.ic_goods_delete : R.mipmap.ic_goods_add);
        Spinner spinner = (Spinner) helper.getView(R.id.spinner_choice_1);
        MySpinnerAdapter adapter = new MySpinnerAdapter(mActivity, item.getData());
        spinner.setAdapter(adapter);
        if (item.getOilStr() != null && !item.getOilStr().isEmpty()) {
            for (int i = 0; i < item.getData().length; i++) {
                if (item.getOilStr().equals(item.getData()[i])) {
                    spinner.setSelection(i, true);
                    break;
                }
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item.setOilStr(item.getData()[i]);
                item.setOil(item.getDataStr()[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        EditText view = (EditText) helper.getView(R.id.et_weight);
        view.setText(item.getWeight());
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setWeight(editable.toString());
            }
        });
        helper.getView(R.id.image_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.getLayoutPosition() == 0) {
                    if (getData().size() < item.getData().length) {
                        addData(new TransportReleaseBean());
                    }
                } else {
                    getData().remove(helper.getLayoutPosition());
                    notifyItemRemoved(helper.getLayoutPosition());
                }
            }
        });
    }
}
