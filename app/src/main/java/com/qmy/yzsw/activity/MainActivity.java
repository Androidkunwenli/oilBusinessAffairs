package com.qmy.yzsw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.LoginBean;
import com.qmy.yzsw.fragment.HomeFragment;
import com.qmy.yzsw.fragment.MyFragment;
import com.qmy.yzsw.fragment.OilStationFragment;
import com.youth.xframe.base.XFragment;
import com.youth.xframe.common.XActivityStack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.fragment_home_image)
    Button fragmentHomeImage;
    @BindView(R.id.fragment_home_text)
    TextView fragmentHomeText;
    @BindView(R.id.fragment_home)
    FrameLayout fragmentHome;
    @BindView(R.id.fragment_my_image)
    Button fragmentMyImage;
    @BindView(R.id.fragment_my_text)
    TextView fragmentMyText;
    @BindView(R.id.fragment_my)
    FrameLayout fragmentMy;
    @BindView(R.id.ll_home_tow_button)
    LinearLayout llHomeTowButton;
    @BindView(R.id.tv_my_administration)
    TextView tvMyAdministration;
    @BindView(R.id.fragment_administration)
    LinearLayout fragmentAdministration;
    @BindView(R.id.rl_home_tow_layout)
    FrameLayout rlHomeTowLayout;
    @BindView(R.id.bt_administration)
    Button btAdministration;
    private XFragment[] mFragments;
    private MyFragment mMyFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        HomeFragment homeFragment = new HomeFragment();
        OilStationFragment oilStationFragment = new OilStationFragment();
        mMyFragment = new MyFragment();
        mFragments = new XFragment[]{homeFragment, oilStationFragment, mMyFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, homeFragment)
                .add(R.id.fl_main, oilStationFragment).hide(oilStationFragment).show(homeFragment)
                .commit();
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLoginBean(LoginBean loginBean) {
        new AlertDialog.Builder(mActivity).setMessage("您的账号在其他设备登陆")
                .setCancelable(false) // 设置点击Dialog其他区域不隐藏对话框，默认是true
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.start(mActivity);
                        XActivityStack.getInstance().finishAllActivity();
                        finish();}
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private int index;
    private int currentTabIndex;

    @OnClick({R.id.fragment_home, R.id.fragment_home_image, R.id.fragment_home_text,
            R.id.fragment_my, R.id.fragment_my_image, R.id.fragment_my_text,
            R.id.fragment_administration, R.id.bt_administration, R.id.tv_my_administration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_image://首页
            case R.id.fragment_home://首页
            case R.id.fragment_home_text://首页
                index = 0;
                fragmentHomeImage.setBackgroundResource(R.mipmap.ic_home);
                fragmentHomeText.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvMyAdministration.setTextColor(getResources().getColor(R.color.text));

                fragmentMyImage.setBackgroundResource(R.mipmap.ic_my);
                fragmentMyText.setTextColor(getResources().getColor(R.color.text));
                break;
            case R.id.fragment_administration://管理
            case R.id.bt_administration://管理
            case R.id.tv_my_administration://管理
                index = 1;
                fragmentHomeImage.setBackgroundResource(R.mipmap.ic_un_home);
                fragmentHomeText.setTextColor(getResources().getColor(R.color.text));

                tvMyAdministration.setTextColor(getResources().getColor(R.color.colorPrimary));

                fragmentMyImage.setBackgroundResource(R.mipmap.ic_my);
                fragmentMyText.setTextColor(getResources().getColor(R.color.text));
                break;
            case R.id.fragment_my://我的
            case R.id.fragment_my_image://我的
            case R.id.fragment_my_text://我的
                index = 2;

                fragmentHomeImage.setBackgroundResource(R.mipmap.ic_un_home);
                fragmentHomeText.setTextColor(getResources().getColor(R.color.text));

                tvMyAdministration.setTextColor(getResources().getColor(R.color.text));

                fragmentMyImage.setBackgroundResource(R.mipmap.ic_un_my);
                fragmentMyText.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fl_main, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
        }
        currentTabIndex = index;
    }


    /**
     * 第三种方法
     */
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }

    public static void start(Activity loginActivity) {
        loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mMyFragment != null) {
            mMyFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
