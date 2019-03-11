package com.qmy.yzsw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.JsonBean;
import com.qmy.yzsw.bean.LeaseBean;
import com.qmy.yzsw.bean.LeaseInfoBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.request.LeaseSaveBean;
import com.qmy.yzsw.bean.uploadpicture.UploadPictureBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideUtils;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.UploadPictureUtils;
import com.qmy.yzsw.view.DeleteFramgment;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LeaseReleaseActivity extends BaseActivity {
    @BindView(R.id.tv_return)
    ImageView tvReturn;
    @BindView(R.id.tv_title_str)
    TextView tvTitleStr;
    @BindView(R.id.tv_right_key)
    TextView tvRightKey;
    @BindView(R.id.rg_lease_lease)
    RadioButton rgLeaseLease;
    @BindView(R.id.rg_lease_turn)
    RadioButton rgLeaseTurn;
    @BindView(R.id.rg_lease)
    RadioGroup rgLease;
    @BindView(R.id.oilname)
    EditText oilname;
    @BindView(R.id.provinceName_cityName_countyName)
    TextView provinceNameCityNameCountyName;
    @BindView(R.id.oiladdress)
    EditText oiladdress;
    @BindView(R.id.ll_leasetime)
    LinearLayout ll_leasetime;
    @BindView(R.id.leasetime)
    TextView leasetime;
    @BindView(R.id.leasemoney)
    EditText leasemoney;
    @BindView(R.id.contacts)
    EditText contacts;
    @BindView(R.id.contactphone)
    EditText contactphone;
    @BindView(R.id.imgFile1)
    ImageView imgFile1;
    @BindView(R.id.imgFile2)
    ImageView imgFile2;
    @BindView(R.id.tv_submission)
    TextView tvSubmission;
    @BindView(R.id.tv_leasemoney)
    TextView tv_leasemoney;
    @BindView(R.id.ll_lease_turn)
    LinearLayout ll_lease_turn;
    @BindView(R.id.ll_myself)
    LinearLayout llmyself;
    @BindView(R.id.tv_opType_1)
    TextView tvopType1;
    @BindView(R.id.tv_opType_2)
    TextView tvopType2;
    @BindView(R.id.tv_company)
    TextView tv_company;
    private LeaseBean leaseBean;
    private String mLeasetype;
    private LeaseInfoBean mLeaseInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lease_release;
    }

    private boolean islease = true;
    private LeaseSaveBean saveBean = new LeaseSaveBean();

    @Override
    public void initData(Bundle savedInstanceState) {
        leaseBean = getIntent().getParcelableExtra("item");
        mLeasetype = getIntent().getStringExtra("leasetype");
        rgLease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rg_lease_lease://出租
                        islease = true;
                        ll_leasetime.setVisibility(View.VISIBLE);
                        tv_leasemoney.setText("租金价格 ：");
                        tv_company.setText("万元/年");
                        setTitleStr("租赁发布");
                        break;
                    case R.id.rg_lease_turn://转让
                        islease = false;
                        ll_leasetime.setVisibility(View.GONE);
                        tv_leasemoney.setText("转让价格 ：");
                        tv_company.setText("万元");
                        setTitleStr("转让发布");
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("租赁发布");
        initJsonData();
        initJsonTime();
        boolean isleaseBean = leaseBean != null;
        if (isleaseBean) {
            KUtils.editTextable(oilname, leaseBean.getMyself().equals("1"));
            KUtils.editTextable(oiladdress, leaseBean.getMyself().equals("1"));
            KUtils.editTextable(leasemoney, leaseBean.getMyself().equals("1"));
            KUtils.editTextable(contacts, leaseBean.getMyself().equals("1"));
            KUtils.editTextable(contactphone, leaseBean.getMyself().equals("1"));
            ll_lease_turn.setVisibility(View.GONE);
            tvSubmission.setVisibility(View.GONE);
            //类型：1租赁；2转让
            if (mLeasetype != null && !mLeasetype.isEmpty()) {
                islease = StringUtils.equals(mLeasetype, "1");
                setTitleStr(StringUtils.equals(mLeasetype, "1") ? "出租详情" : "转让详情");
                tv_leasemoney.setText(StringUtils.equals(mLeasetype, "1") ? "租金价格 ：" : "转让价格 ：");
                tv_company.setText(StringUtils.equals(mLeasetype, "1") ? "万元/年" : "万元");
            }
            llmyself.setVisibility(StringUtils.equals(leaseBean.getMyself(), "1") ? View.VISIBLE : View.GONE);
            HttpUtils.leaseDetail(mActivity, leaseBean.getId(), new JsonCallback<BaseBean<LeaseInfoBean>>() {
                @Override
                public void onSuccess(Response<BaseBean<LeaseInfoBean>> response) {
                    mLeaseInfoBean = response.body().getData();
                    oilname.setText(mLeaseInfoBean.getOilname());
                    provinceNameCityNameCountyName.setText(mLeaseInfoBean.getProvinceName() + mLeaseInfoBean.getCityName() + mLeaseInfoBean.getCountyName());
                    oiladdress.setText(mLeaseInfoBean.getOiladdress());
                    if (mLeaseInfoBean.getLeasetime() != null && !mLeaseInfoBean.getLeasetime().isEmpty()) {
                        leasetime.setText(mLeaseInfoBean.getLeasetime());
                        ll_leasetime.setVisibility(View.VISIBLE);
                    } else {
                        ll_leasetime.setVisibility(View.GONE);
                    }
                    leasemoney.setText(mLeaseInfoBean.getLeasemoney());
                    contacts.setText(mLeaseInfoBean.getContacts());
                    contactphone.setText(mLeaseInfoBean.getContactphone());
                    GlideUtils.LoadImage(mLeaseInfoBean.getPicture1(), imgFile1);
                    GlideUtils.LoadImage(mLeaseInfoBean.getPicture2(), imgFile2);
                }
            });
        }
    }

    public static void start(FragmentActivity activity, LeaseBean item, String leasetype) {
        Intent intent = new Intent(activity, LeaseReleaseActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("leasetype", leasetype);
        activity.startActivity(intent);
    }

    private boolean file = true;
    private String imgFile1_path;
    private String imgFile2_path;
    private String provinceName = "";
    private String CityName = "";
    private String CountyName = "";
    private int mInt;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private ArrayList<JsonBean> options1time = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2time = new ArrayList<>();

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    private void initJsonTime() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "time.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1time = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
            }
            /**
             * 年月
             */
            options2time.add(CityList);

        }
    }

    @OnClick({R.id.imgFile1, R.id.imgFile2, R.id.tv_submission, R.id.provinceName_cityName_countyName,
            R.id.leasetime, R.id.tv_opType_1, R.id.tv_opType_2, R.id.tv_save})
    public void onViewClicked(View view) {
        if (leaseBean != null && !leaseBean.getMyself().equals("1")) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_opType_1:
                if (leaseBean != null) {
                    final DeleteFramgment framgment = new DeleteFramgment();
                    framgment.setOnItemChildClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.btn_cancle:
                                    framgment.dismiss();
                                    break;
                                case R.id.btn_ok:
                                    HttpUtils.leaseUpdateState(mActivity, leaseBean.getId(), 1, new DialogCallback<BaseBean>(mActivity) {
                                        @Override
                                        public void onSuccess(Response<BaseBean> response) {
                                            framgment.dismiss();
                                            finish();
                                        }
                                    });
                                    break;
                            }

                        }
                    });
                    framgment.show(getSupportFragmentManager(), "framgment");
                }
                break;
            case R.id.tv_opType_2:
                if (leaseBean != null) {
                    HttpUtils.leaseUpdateState(mActivity, leaseBean.getId(), 2, new DialogCallback<BaseBean>(mActivity) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            finish();
                        }
                    });
                }
                break;
            case R.id.provinceName_cityName_countyName:
                KeyboardUtils.hideSoftInput(mActivity);
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        //返回的分别是三个级别的选中位置
                        provinceName = options1Items.get(options1).getPickerViewText();
                        CityName = options2Items.get(options1).get(option2);
                        CountyName = options3Items.get(options1).get(option2).get(options3);
                        provinceNameCityNameCountyName.setText(provinceName + " " + CityName + " " + CountyName);
                    }
                })
                        .setCancelColor(R.color.gray)
                        .setSubmitColor(R.color.gray)
                        .build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
                break;
            case R.id.leasetime:
                KeyboardUtils.hideSoftInput(mActivity);
                //时间选择器
                //条件选择器
                OptionsPickerView pickerView = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        leasetime.setText(options1time.get(options1).getPickerViewText() + " " + options2time.get(options1).get(option2));
                    }
                })
                        .setCancelColor(R.color.gray)
                        .setSubmitColor(R.color.gray)
                        .build();
                pickerView.setPicker(options1time, options2time);
                pickerView.show();
                break;
            case R.id.imgFile1:
                //类型：1租赁；2转让
                if (mLeasetype != null && !mLeasetype.isEmpty() && mLeaseInfoBean != null
                        && mLeaseInfoBean.getPicture1() != null && tvopType1.getVisibility() == View.GONE) {
                    ImageActivity.start(mActivity, mLeaseInfoBean.getPicture1(), mLeaseInfoBean.getOilname());
                } else {
                    file = true;
                    PictureSelector.create(mActivity)
                            .openGallery(PictureMimeType.ofImage())
                            .selectionMode(PictureConfig.SINGLE)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
                break;
            case R.id.imgFile2:
                if (mLeasetype != null && !mLeasetype.isEmpty() && mLeaseInfoBean != null
                        && mLeaseInfoBean.getPicture2() != null && tvopType1.getVisibility() == View.GONE) {
                    ImageActivity.start(mActivity, mLeaseInfoBean.getPicture2(), mLeaseInfoBean.getOilname());
                } else {
                    file = false;
                    PictureSelector.create(mActivity)
                            .openGallery(PictureMimeType.ofImage())
                            .selectionMode(PictureConfig.SINGLE)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
                break;
            case R.id.tv_save:
            case R.id.tv_submission:
                ArrayList<UploadPictureBean> uploadPictureBeans = new ArrayList<>();
                if (imgFile1_path != null && !imgFile1_path.isEmpty()) {
                    uploadPictureBeans.add(new UploadPictureBean(18, imgFile1_path, ""));
                }
                if (imgFile2_path != null && !imgFile2_path.isEmpty()) {
                    uploadPictureBeans.add(new UploadPictureBean(18, imgFile2_path, ""));
                }
                UploadPictureUtils.uploadPicture(mActivity, uploadPictureBeans, new UploadPictureUtils.ConversionSuccess() {
                    @Override
                    public void FileDownloadSuccess(ArrayList<UploadPictureBean> list) {
                        for (UploadPictureBean pictureBean : list) {
                            if (StringUtils.equals(pictureBean.getFilePath(), imgFile1_path)) {
                                saveBean.setImgFile1(pictureBean.getDownloadFile());
                            } else if (StringUtils.equals(pictureBean.getFilePath(), imgFile2_path)) {
                                saveBean.setImgFile2(pictureBean.getDownloadFile());
                            }
                        }
                        save();
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() != 0) {
                        if (file) {
                            imgFile1_path = selectList.get(0).getPath();
                            GlideUtils.LoadImage(imgFile1_path, imgFile1);
                        } else {
                            imgFile2_path = selectList.get(0).getPath();
                            GlideUtils.LoadImage(imgFile2_path, imgFile2);
                        }
                    }
                    break;
            }
        }
    }

    private void save() {
        if (leaseBean != null) {
            saveBean.setId(leaseBean.getId());
        }
        saveBean.setOilname(oilname.getText().toString().trim());
        saveBean.setProvinceName(provinceName);
        saveBean.setCityName(CityName);
        saveBean.setCountyName(CountyName);
        saveBean.setOiladdress(oiladdress.getText().toString().trim());
        saveBean.setLeasetime(leasetime.getText().toString().trim());
        saveBean.setLeasemoney(leasemoney.getText().toString().trim());
        saveBean.setContacts(contacts.getText().toString().trim());
        saveBean.setContactphone(contactphone.getText().toString().trim());
        HttpUtils.leasesaveaAndmakeoversave(mActivity, islease, saveBean, new DialogCallback<BaseBean>(mActivity) {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                ToastUtils.showShort("发布" + response.body().getMsg());
                finish();
            }
        });
    }

}
