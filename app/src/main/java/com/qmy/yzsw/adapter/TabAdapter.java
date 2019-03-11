package com.qmy.yzsw.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qmy.yzsw.activity.base.BaseFragment;



public class TabAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private BaseFragment[] mFragments;
    private String[] mTitle;
    private final FragmentManager mFm;

    public TabAdapter(Context context, FragmentManager fm, String[] title, BaseFragment[] fragments) {
        super(fm);
        mFm = fm;
        mTitle = title;
        mFragments = fragments;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position % mTitle.length];
    }
}
