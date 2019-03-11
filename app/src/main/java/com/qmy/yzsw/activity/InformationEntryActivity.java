package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.InformationBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.CardInputEditText;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.view.MyTextWatcher;
import com.qmy.yzsw.widget.CashierInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationEntryActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, MyTextWatcher.TextWatcherListener {
    @BindView(R.id.enterpriseName)
    EditText enterpriseName;
    @BindView(R.id.enterpricePhone)
    CardInputEditText enterpricePhone;
    @BindView(R.id.job)
    EditText job;
    @BindView(R.id.enterpriceLeader)
    EditText enterpriceLeader;
    @BindView(R.id.leaderPhone)
    EditText leaderPhone;
    @BindView(R.id.gasolineCount)
    EditText gasolineCount;
    @BindView(R.id.gasolineMoney)
    EditText gasolineMoney;
    @BindView(R.id.dieseloilCount)
    EditText dieseloilCount;
    @BindView(R.id.dieseloilMoney)
    EditText dieseloilMoney;
    @BindView(R.id.supplierOne)
    EditText supplierOne;
    @BindView(R.id.supplierTwo)
    EditText supplierTwo;
    @BindView(R.id.supplierThree)
    EditText supplierThree;
    @BindView(R.id.car)
    CheckBox car;
    @BindView(R.id.supermarket)
    CheckBox supermarket;
    @BindView(R.id.quickrepair)
    CheckBox quickrepair;
    @BindView(R.id.fastfood)
    CheckBox fastfood;
    @BindView(R.id.other)
    EditText other;
    @BindView(R.id.retailgasoline)
    CheckBox retailgasoline;
    @BindView(R.id.retaildieseloil)
    CheckBox retaildieseloil;
    @BindView(R.id.wholesalegasoline)
    CheckBox wholesalegasoline;
    @BindView(R.id.wholesaledieseloil)
    CheckBox wholesaledieseloil;
    @BindView(R.id.association_1)
    RadioButton association1;
    @BindView(R.id.association_0)
    RadioButton association0;
    @BindView(R.id.association_2)
    RadioButton association2;
    @BindView(R.id.association)
    RadioGroup association;
    @BindView(R.id.naturalgas)
    LinearLayout naturalgas;
    @BindView(R.id.naturalgas_1)
    CheckBox naturalgas1;
    @BindView(R.id.hascng)
    CheckBox hascng;
    @BindView(R.id.haslcng)
    CheckBox haslcng;
    @BindView(R.id.haslng)
    CheckBox haslng;
    @BindView(R.id.naturalgas_0)
    CheckBox naturalgas0;
    @BindView(R.id.ev_1)
    RadioButton ev1;
    @BindView(R.id.ev_2)
    RadioButton ev2;
    @BindView(R.id.ev_0)
    RadioButton ev0;
    @BindView(R.id.ev)
    RadioGroup ev;
    @BindView(R.id.brand)
    EditText brand;
    @BindView(R.id.brandcount)
    EditText brandcount;
    @BindView(R.id.knock)
    EditText knock;
    @BindView(R.id.oiltankGasoline)
    EditText oiltankGasoline;
    @BindView(R.id.gasolinestere)
    EditText gasolinestere;
    @BindView(R.id.oiltankDieseloil)
    EditText oiltankDieseloil;
    @BindView(R.id.dieselstere)
    EditText dieselstere;
    @BindView(R.id.ninetytwo)
    EditText ninetytwo;
    @BindView(R.id.ninetytwo_1)
    CheckBox ninetytwo1;
    @BindView(R.id.ninetyfive)
    EditText ninetyfive;
    @BindView(R.id.ninetyfive_1)
    CheckBox ninetyfive1;
    @BindView(R.id.ninetyeight)
    EditText ninetyeight;
    @BindView(R.id.ninetyeight_1)
    CheckBox ninetyeight1;
    @BindView(R.id.zero)
    EditText zero;
    @BindView(R.id.zero_1)
    CheckBox zero1;
    @BindView(R.id.ten)
    EditText ten;
    @BindView(R.id.ten_1)
    CheckBox ten1;
    @BindView(R.id.twenty)
    EditText twenty;
    @BindView(R.id.twenty_1)
    CheckBox twenty1;
    @BindView(R.id.lng)
    EditText lng;
    @BindView(R.id.lng_1)
    CheckBox lng1;
    @BindView(R.id.cng)
    EditText cng;
    @BindView(R.id.cng_1)
    CheckBox cng1;
    @BindView(R.id.charging)
    EditText charging;
    @BindView(R.id.charging_1)
    CheckBox charging1;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.image_delete)
    ImageView imageDelete;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private boolean mIsEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_information_entry;
    }

    private InformationBean mInformationBean = new InformationBean();

    private boolean update = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        mIsEdit = getIntent().getBooleanExtra("isEdit", true);
        setFindViewById(true);
        setTitleStr(mIsEdit ? "信息录入" : "油站信息");
        if (mIsEdit) {
            update = true;
            setTvRightText("提交");
            mRightKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestSubmission();
                }
            });
        } else {
            update = false;
            setTvRightText("修改");
            mRightKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (update) {
                        requestSubmission();
                        update = !update;
                    } else {
                        setTvRightText("提交");
                        update = !update;
                        mIsEdit = true;
                        setUpView();
                    }
                }
            });
        }

        gasolineCount.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        gasolineMoney.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        dieseloilCount.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        dieseloilMoney.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        gasolinestere.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        dieselstere.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ninetytwo.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ninetyfive.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ninetyeight.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        zero.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        ten.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        twenty.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        lng.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        cng.setFilters(new CashierInputFilter[]{new CashierInputFilter()});
        charging.setFilters(new CashierInputFilter[]{new CashierInputFilter()});

        HttpUtils.getOilInfo(mActivity, new JsonCallback<BaseBean<InformationBean>>() {
            @Override
            public void onSuccess(Response<BaseBean<InformationBean>> response) {
                mInformationBean = response.body().getData();
                if (mInformationBean.getApproveRemark() != null && !mInformationBean.getApproveRemark().trim().isEmpty()) {
                    llLayout.setVisibility(View.VISIBLE);
                    tvRemind.setText(mInformationBean.getApproveRemark());
                } else {
                    llLayout.setVisibility(View.GONE);
                }

                if (mInformationBean.getEnterpriseName().isEmpty()) {
                    return;
                }
                enterpriseName.setText(mInformationBean.getEnterpriseName());
                enterpricePhone.setText(mInformationBean.getEnterpricePhone());
                job.setText(mInformationBean.getJob());
                enterpriceLeader.setText(mInformationBean.getEnterpriceLeader());
                leaderPhone.setText(mInformationBean.getLeaderPhone());
                gasolineMoney.setText(mInformationBean.getGasolineMoney());
                gasolineCount.setText(mInformationBean.getGasolineCount());
                dieseloilMoney.setText(mInformationBean.getDieseloilMoney());
                dieseloilCount.setText(mInformationBean.getDieseloilCount());
                supplierOne.setText(mInformationBean.getSupplierOne());
                supplierTwo.setText(mInformationBean.getSupplierTwo());
                supplierThree.setText(mInformationBean.getSupplierThree());
                car.setChecked(StringUtils.equals(mInformationBean.getCar(), "1"));
                supermarket.setChecked(StringUtils.equals(mInformationBean.getSupermarket(), "1"));
                quickrepair.setChecked(StringUtils.equals(mInformationBean.getQuickrepair(), "1"));
                fastfood.setChecked(StringUtils.equals(mInformationBean.getFastfood(), "1"));
                other.setText(mInformationBean.getOther());
                retailgasoline.setChecked(StringUtils.equals(mInformationBean.getRetailgasoline(), "1"));
                retaildieseloil.setChecked(StringUtils.equals(mInformationBean.getRetaildieseloil(), "1"));
                wholesalegasoline.setChecked(StringUtils.equals(mInformationBean.getWholesalegasoline(), "1"));
                wholesaledieseloil.setChecked(StringUtils.equals(mInformationBean.getWholesaledieseloil(), "1"));
                brand.setText(mInformationBean.getBrand());
                brandcount.setText(mInformationBean.getBrandcount());
                knock.setText(mInformationBean.getKnock());
                oiltankGasoline.setText(mInformationBean.getOiltankGasoline());
                oiltankDieseloil.setText(mInformationBean.getOiltankDieseloil());
                gasolinestere.setText(mInformationBean.getGasolinestere());
                dieselstere.setText(mInformationBean.getDieselstere());
                if (StringUtils.equals(mInformationBean.getNinetytwo(), "-1")) {
                    ninetytwo1.setChecked(true);
                } else {
                    ninetytwo1.setChecked(false);
                    ninetytwo.setText(mInformationBean.getNinetytwo());
                }
                if (StringUtils.equals(mInformationBean.getNinetyfive(), "-1")) {
                    ninetyfive1.setChecked(true);
                } else {
                    ninetyfive1.setChecked(false);
                    ninetyfive.setText(mInformationBean.getNinetyfive());
                }
                if (StringUtils.equals(mInformationBean.getNinetyeight(), "-1")) {
                    ninetyeight1.setChecked(true);
                } else {
                    ninetyeight1.setChecked(false);
                    ninetyeight.setText(mInformationBean.getNinetyeight());
                }
                if (StringUtils.equals(mInformationBean.getZero(), "-1")) {
                    zero1.setChecked(true);
                } else {
                    zero1.setChecked(false);
                    zero.setText(mInformationBean.getZero());
                }
                if (StringUtils.equals(mInformationBean.getTen(), "-1")) {
                    ten1.setChecked(true);
                } else {
                    ten1.setChecked(false);
                    ten.setText(mInformationBean.getTen());
                }
                if (StringUtils.equals(mInformationBean.getTwenty(), "-1")) {
                    twenty1.setChecked(true);
                } else {
                    twenty1.setChecked(false);
                    twenty.setText(mInformationBean.getTwenty());
                }
                if (StringUtils.equals(mInformationBean.getLng(), "-1")) {
                    lng1.setChecked(true);
                } else {
                    lng1.setChecked(false);
                    lng.setText(mInformationBean.getLng());
                }
                if (StringUtils.equals(mInformationBean.getCng(), "-1")) {
                    cng1.setChecked(true);
                } else {
                    cng1.setChecked(false);
                    cng.setText(mInformationBean.getCng());
                }
                if (StringUtils.equals(mInformationBean.getCharging(), "-1")) {
                    charging1.setChecked(true);
                } else {
                    charging1.setChecked(false);
                    charging.setText(mInformationBean.getCharging());
                }
                switch (mInformationBean.getEv()) {
                    case "1":
                        ev1.setChecked(true);
                        break;
                    case "0":
                        ev0.setChecked(true);
                        break;
                    case "2":
                        ev2.setChecked(true);
                        break;
                }
                switch (mInformationBean.getAssociation()) {
                    case "1":
                        association1.setChecked(true);
                        break;
                    case "0":
                        association0.setChecked(true);
                        break;
                    case "2":
                        association2.setChecked(true);
                        break;
                }
//                naturalgas (string, optional): 是否有增加天然气产品需求：1是；0否 ,
//                hascng (string, optional): 已有CNG：1是；0否 ,
//                haslcng (string, optional): 已有L-CNG：1是；0否 ,
//                haslng (string, optional): 已有LNG：1是；0否 ,
                hascng.setChecked(StringUtils.equals(mInformationBean.getHascng(), "1"));
                haslcng.setChecked(StringUtils.equals(mInformationBean.getHaslcng(), "1"));
                haslng.setChecked(StringUtils.equals(mInformationBean.getHaslng(), "1"));

                String naturalgas = mInformationBean.getNaturalgas();
                if (naturalgas.isEmpty()) {
                    naturalgas1.setChecked(false);
                    naturalgas0.setChecked(false);
                } else {
                    boolean checked = StringUtils.equals(naturalgas, "1");
                    if (checked) {
                        naturalgas1.setChecked(true);
                        naturalgas0.setChecked(false);
                    } else {
                        naturalgas1.setChecked(false);
                        naturalgas0.setChecked(true);
                    }
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llLayout.setVisibility(View.GONE);
            }
        });
    }

    //提交入参
    private void requestSubmission() {
        mInformationBean.setEnterpriseName(enterpriseName.getText().toString().trim());
        mInformationBean.setEnterpricePhone(enterpricePhone.getText().toString().trim());
        mInformationBean.setJob(job.getText().toString().trim());
        mInformationBean.setEnterpriceLeader(enterpriceLeader.getText().toString().trim());
        mInformationBean.setLeaderPhone(leaderPhone.getText().toString().trim());
        mInformationBean.setGasolineMoney(gasolineMoney.getText().toString().trim());
        mInformationBean.setGasolineCount(gasolineCount.getText().toString().trim());
        mInformationBean.setDieseloilMoney(dieseloilMoney.getText().toString().trim());
        mInformationBean.setDieseloilCount(dieseloilCount.getText().toString().trim());
        mInformationBean.setSupplierOne(supplierOne.getText().toString().trim());
        mInformationBean.setSupplierTwo(supplierTwo.getText().toString().trim());
        mInformationBean.setSupplierThree(supplierThree.getText().toString().trim());
        mInformationBean.setCar(car.isChecked() ? "1" : "0");
        mInformationBean.setSupermarket(supermarket.isChecked() ? "1" : "0");
        mInformationBean.setQuickrepair(quickrepair.isChecked() ? "1" : "0");
        mInformationBean.setFastfood(fastfood.isChecked() ? "1" : "0");
        mInformationBean.setOther(other.getText().toString().trim());
        mInformationBean.setRetailgasoline(retailgasoline.isChecked() ? "1" : "0");
        mInformationBean.setRetaildieseloil(retaildieseloil.isChecked() ? "1" : "0");
        mInformationBean.setWholesalegasoline(wholesalegasoline.isChecked() ? "1" : "0");
        mInformationBean.setWholesaledieseloil(wholesaledieseloil.isChecked() ? "1" : "0");
        mInformationBean.setBrand(brand.getText().toString().trim());
        mInformationBean.setBrandcount(brandcount.getText().toString().trim());
        mInformationBean.setKnock(knock.getText().toString().trim());
        mInformationBean.setOiltankGasoline(oiltankGasoline.getText().toString().trim());
        mInformationBean.setOiltankDieseloil(oiltankDieseloil.getText().toString().trim());
        mInformationBean.setGasolinestere(gasolinestere.getText().toString().trim());
        mInformationBean.setDieselstere(dieselstere.getText().toString().trim());
        mInformationBean.setNinetytwo(ninetytwo1.isChecked() ? "-1" : ninetytwo.getText().toString().trim());
        mInformationBean.setNinetyfive(ninetyfive1.isChecked() ? "-1" : ninetyfive.getText().toString().trim());
        mInformationBean.setNinetyeight(ninetyeight1.isChecked() ? "-1" : ninetyeight.getText().toString().trim());
        mInformationBean.setZero(zero1.isChecked() ? "-1" : zero.getText().toString().trim());
        mInformationBean.setTen(ten1.isChecked() ? "-1" : ten.getText().toString().trim());
        mInformationBean.setTwenty(twenty1.isChecked() ? "-1" : twenty.getText().toString().trim());
        mInformationBean.setLng(lng1.isChecked() ? "-1" : lng.getText().toString().trim());
        mInformationBean.setCng(cng1.isChecked() ? "-1" : cng.getText().toString().trim());
        mInformationBean.setCharging(charging1.isChecked() ? "-1" : charging.getText().toString().trim());

        HttpUtils.saveInfo(mActivity, mInformationBean, new DialogCallback<BaseBean>(mActivity) {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                ToastUtils.showShort(response.body().getMsg());
                if (!update) {
                    mIsEdit = false;
                }
                if (!mIsEdit) {
                    setTvRightText("修改");
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void initView() {
        lng1.setOnCheckedChangeListener(this);
        twenty1.setOnCheckedChangeListener(this);
        ten1.setOnCheckedChangeListener(this);
        zero1.setOnCheckedChangeListener(this);
        ninetyeight1.setOnCheckedChangeListener(this);
        ninetyfive1.setOnCheckedChangeListener(this);
        ninetytwo1.setOnCheckedChangeListener(this);
        haslng.setOnCheckedChangeListener(this);
        haslcng.setOnCheckedChangeListener(this);
        hascng.setOnCheckedChangeListener(this);
        wholesaledieseloil.setOnCheckedChangeListener(this);
        wholesalegasoline.setOnCheckedChangeListener(this);
        retaildieseloil.setOnCheckedChangeListener(this);
        retailgasoline.setOnCheckedChangeListener(this);
        fastfood.setOnCheckedChangeListener(this);
        quickrepair.setOnCheckedChangeListener(this);
        supermarket.setOnCheckedChangeListener(this);
        car.setOnCheckedChangeListener(this);
        cng1.setOnCheckedChangeListener(this);
        charging1.setOnCheckedChangeListener(this);
        naturalgas1.setOnCheckedChangeListener(this);
        naturalgas0.setOnCheckedChangeListener(this);
        association.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.association_0:
                        mInformationBean.setAssociation("0");
                        break;
                    case R.id.association_1:
                        mInformationBean.setAssociation("1");
                        break;
                    case R.id.association_2:
                        mInformationBean.setAssociation("2");
                        break;
                }
            }
        });
        ev.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.ev_1:
                        mInformationBean.setEv("1");
                        break;
                    case R.id.ev_0:
                        mInformationBean.setEv("0");
                        break;
                    case R.id.ev_2:
                        mInformationBean.setEv("2");
                        break;
                }
            }
        });
        ninetytwo.addTextChangedListener(new MyTextWatcher(1, this));
        ninetyfive.addTextChangedListener(new MyTextWatcher(2, this));
        ninetyeight.addTextChangedListener(new MyTextWatcher(3, this));
        zero.addTextChangedListener(new MyTextWatcher(4, this));
        ten.addTextChangedListener(new MyTextWatcher(5, this));
        twenty.addTextChangedListener(new MyTextWatcher(6, this));
        lng.addTextChangedListener(new MyTextWatcher(7, this));
        cng.addTextChangedListener(new MyTextWatcher(8, this));
        charging.addTextChangedListener(new MyTextWatcher(9, this));

        setUpView();
    }

    private void setUpView() {
        isEditTextEdit(charging,
                cng,
                lng,
                twenty,
                ten,
                zero,
                ninetyeight,
                ninetyfive,
                ninetytwo,
                oiltankGasoline,
                gasolinestere,
                oiltankDieseloil,
                dieselstere,
                knock,
                brandcount,
                brand,
                other,
                supplierThree,
                supplierTwo,
                supplierOne,
                dieseloilCount,
                dieseloilMoney,
                gasolineCount,
                gasolineMoney,
                leaderPhone,
                enterpriceLeader,
                job,
                enterpricePhone,
                enterpriseName
        );
        isCheckBoxEdit(car,
                supermarket,
                quickrepair,
                fastfood,
                retailgasoline,
                retaildieseloil,
                wholesalegasoline,
                wholesaledieseloil,
                naturalgas1,
                hascng,
                haslcng,
                haslng,
                naturalgas0,
                ninetytwo1,
                ninetyfive1,
                ninetyeight1,
                zero1,
                ten1,
                twenty1,
                lng1,
                cng1,
                charging1);
        isRadioButtonEdit(association1, association0, association2, ev1, ev2, ev0);
    }

    /**
     * 设置edittext时候可编辑
     */
    private void isEditTextEdit(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setCursorVisible(mIsEdit);
            editText.setFocusable(mIsEdit);
            editText.setFocusableInTouchMode(mIsEdit);
        }
    }

    /**
     * 设置edittext时候可编辑
     */
    private void isCheckBoxEdit(CheckBox... checkBoxes) {
        for (CheckBox editText : checkBoxes) {
            editText.setClickable(mIsEdit);
        }
    }

    /**
     * 设置edittext时候可编辑
     */
    private void isRadioButtonEdit(RadioButton... buttons) {
        for (RadioButton editText : buttons) {
            editText.setClickable(mIsEdit);
        }
    }

    public static void start(FragmentActivity activity, boolean isEdit) {
        Intent intent = new Intent(activity, InformationEntryActivity.class);
        intent.putExtra("isEdit", isEdit);
        activity.startActivity(intent);
    }

    @Override
    public void afterTextChanged(Editable s, int index) {
        switch (index) {
            case 1:
                ninetytwo1.setChecked(false);
                break;
            case 2:
                ninetyfive1.setChecked(false);
                break;
            case 3:
                ninetyeight1.setChecked(false);
                break;
            case 4:
                zero1.setChecked(false);
                break;
            case 5:
                ten1.setChecked(false);
                break;
            case 6:
                twenty1.setChecked(false);
                break;
            case 7:
                lng1.setChecked(false);
                break;
            case 8:
                cng1.setChecked(false);
                break;
            case 9:
                charging1.setChecked(false);
                break;
        }
    }

    //选择框监听
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.car:
                break;
            case R.id.supermarket:
                break;
            case R.id.quickrepair:
                break;
            case R.id.fastfood:
                break;
            case R.id.retailgasoline:
                break;
            case R.id.retaildieseloil:
                break;
            case R.id.wholesalegasoline:
                break;
            case R.id.wholesaledieseloil:
                break;
            case R.id.naturalgas_1:
                naturalgas0.setChecked(false);
                naturalgas1.setChecked(b);
                mInformationBean.setNaturalgas(b ? "1" : "");
                break;
            case R.id.naturalgas_0:
                naturalgas1.setChecked(false);
                naturalgas0.setChecked(b);
                mInformationBean.setNaturalgas(b ? "0" : "");
                break;
            case R.id.hascng:
                hascng.setChecked(b);
                mInformationBean.setHascng(b ? "1" : "0");
                break;
            case R.id.haslcng:
                haslcng.setChecked(b);
                mInformationBean.setHaslcng(b ? "1" : "0");
                break;
            case R.id.haslng:
                haslng.setChecked(b);
                mInformationBean.setHaslng(b ? "1" : "0");
                break;
            case R.id.ninetytwo_1:
                ninetytwo.setText("");
                break;
            case R.id.ninetyfive_1:
                ninetyfive.setText("");
                break;
            case R.id.ninetyeight_1:
                ninetyeight.setText("");
                break;
            case R.id.zero_1:
                zero.setText("");
                break;
            case R.id.ten_1:
                ten.setText("");
                break;
            case R.id.twenty_1:
                twenty.setText("");
                break;
            case R.id.lng_1:
                lng.setText("");
                break;
            case R.id.cng_1:
                cng.setText("");
                break;
            case R.id.charging_1:
                charging.setText("");
                break;
        }
    }
}
