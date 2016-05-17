package com.imooc.YnuMobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 江树金 on 2016/5/17.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mTabs;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> mTabs ) {
        super(fm);
        this.mTabs=mTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mTabs.size() ;
    }
}
