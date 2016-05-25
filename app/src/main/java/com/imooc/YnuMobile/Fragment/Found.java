package com.imooc.YnuMobile.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.YnuMobile.JavaBean.Actor;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.View.FullyLinearLayoutManager;
import com.imooc.YnuMobile.View.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class Found extends Fragment {

    View view;
    /*CustomTabPageIndicator mTabPager;
    ViewPager mViewpager;
    private static String[] titles=new String[]{
            "社区板块","生活服务"
    };*/
    ProgressDialog mDialog;
    /*
	* CardView+RecyclerView
	* */
    RecyclerView recyclerView;
    List<Actor> lists;
    MyRecyclerView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.found,container,false);
        initView();
        return view;
    }

    private void initView() {
        mDialog=new ProgressDialog(getActivity());
        mDialog.setMessage("数据加载中...");
        mDialog.setCancelable(false);

        FullyLinearLayoutManager layoutManager=new FullyLinearLayoutManager(getActivity());
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setFocusable(false);
        initDatas();
        adapter=new MyRecyclerView(getActivity(),lists);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initDatas() {
		/*
		* CardView数据获取
		* */
        lists=new ArrayList<>();
        lists.add(new Actor("水电费+一卡通查询","还在为不知道何时停电而烦恼？",R.drawable.card_view_one));
        lists.add(new Actor("百度地图云大通","初来乍到，点我和你一起你走遍云大，吃遍云大。",R.drawable.card_view_two));
        lists.add(new Actor("校内通讯录","期末快来了，需要联系哪位老师，进来看看",R.drawable.card_view_three));
        lists.add(new Actor("校历","现在第几周了？点我查看",R.drawable.newtop1));
        lists.add(new Actor("待定项","---------------------------",R.drawable.bg_login));
    }
}
