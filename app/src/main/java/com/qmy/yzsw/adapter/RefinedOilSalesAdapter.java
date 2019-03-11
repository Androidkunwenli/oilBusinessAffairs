package com.qmy.yzsw.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.RefinedOilSalesBean;
import com.qmy.yzsw.bean.TransportReleaseBean;
import com.qmy.yzsw.utils.KUtils;

public class RefinedOilSalesAdapter extends BaseQuickAdapter<RefinedOilSalesBean, BaseViewHolder> {

    private boolean aBoolean;
    private BaseActivity mActivity;
    private String[] str1 = new String[]{"汽油", "柴油"};
    private String[][] str2 = new String[][]{{"92#", "95#", "98#"}, {"0#", "-10#", "-20#"}};

    public RefinedOilSalesAdapter(BaseActivity mActivity, boolean b) {
        super(R.layout.item_refined_oilsales_adapter);
        this.mActivity = mActivity;
        aBoolean = b;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RefinedOilSalesBean item) {
        helper.setImageResource(R.id.image_add, helper.getAdapterPosition() != 0 ? R.mipmap.ic_goods_delete : R.mipmap.ic_goods_add);
        final Spinner spinner_choice_1 = (Spinner) helper.getView(R.id.spinner_choice_1);
        final Spinner spinner_choice_2 = (Spinner) helper.getView(R.id.spinner_choice_2);
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(mActivity, str1);
        spinner_choice_1.setAdapter(adapter1);
        spinner_choice_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int index;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                MySpinnerAdapter adapter2 = new MySpinnerAdapter(mActivity, str2[index]);
                spinner_choice_2.setAdapter(adapter2);
                spinner_choice_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        item.setOilLabel(str2[index][i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                if (aBoolean && item.getOilLabel() != null && !item.getOilLabel().isEmpty()) {
                    for (String s : str2[0]) {
                        if (item.getOilLabel().equals(s)) {
                            index = 0;
                        }
                    }
                    for (String s : str2[1]) {
                        if (item.getOilLabel().equals(s)) {
                            index = 1;
                        }
                    }
                    for (int i = 0; i < str2[index].length; i++) {
                        if (item.getOilLabel().equals(str2[index][i])) {
                            spinner_choice_2.setSelection(i, true);
                            break;
                        }
                    }
                    spinner_choice_1.setSelection(index, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText et_weight = (EditText) helper.getView(R.id.et_weight);
        et_weight.setText(KUtils.setFormatNum(item.getOilSalesVolume()));
        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setOilSalesVolume(editable.toString());
            }
        });
        EditText et_oil_unit_price = (EditText) helper.getView(R.id.et_oil_unit_price);
        et_oil_unit_price.setText(KUtils.setFormatNum(item.getOilUnitPrice()));
        et_oil_unit_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setOilUnitPrice(editable.toString());
            }
        });
        EditText et_oil_total_price = (EditText) helper.getView(R.id.et_oil_total_price);
        et_oil_total_price.setText(KUtils.setFormatNum(item.getOilTotalPrice()));
        et_oil_total_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setOilTotalPrice(editable.toString());
            }
        });
        helper.getView(R.id.image_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.getLayoutPosition() == 0) {
                    addData(new RefinedOilSalesBean());
                } else {
                    getData().remove(helper.getLayoutPosition());
                    notifyItemRemoved(helper.getLayoutPosition());
                }
            }
        });
    }
}
