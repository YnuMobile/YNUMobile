package com.imooc.YnuMobile.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.ViewpagerIndicator.CustomTabPageIndicator;
import com.imooc.YnuMobile.ViewpagerIndicator.ItemFragment;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class Found extends Fragment {

    View view;
    CustomTabPageIndicator mTabPager;
    ViewPager mViewpager;
    private static String[] titles=new String[]{
            "首页","发现","关于","更多"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.found,container,false);
        initView();
        return view;
    }

    private void initView() {
        mTabPager= (CustomTabPageIndicator) view.findViewById(R.id.tabPager);
        mViewpager= (ViewPager) view.findViewById(R.id.viewPager);
        FragmentPagerAdapter adapter=new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
        mViewpager.setAdapter(adapter);
        mTabPager.setViewPager(mViewpager);
        mTabPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//切换页面的时候
                Toast.makeText(getActivity().getApplicationContext(), titles[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * ViewPager适配器
     * @author JSJ
     *
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new TabOne();
            Bundle args = new Bundle();
            args.putString("arg", titles[position]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
