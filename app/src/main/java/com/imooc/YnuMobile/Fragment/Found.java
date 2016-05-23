package com.imooc.YnuMobile.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.YnuMobile.Adapter.GridViewAdapter;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.View.GridView;

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
    GridView gridView;
    Context context;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.found,container,false);
        initView();
        return view;
    }

    private void initView() {
        gridView=(GridView) view.findViewById(R.id.gridview);
        context=this.getActivity();
        gridView.setAdapter(new GridViewAdapter(context));
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.id_refresh);
        refreshLayout.setColorSchemeResources(R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout);
        refreshLayout.setProgressViewEndTarget(true, 100);
        refreshLayout.setProgressBackgroundColor(R.color.bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new TaskHandler().execute();
            }
        });
        mDialog=new ProgressDialog(getActivity());
        mDialog.setMessage("数据加载中...");
        mDialog.setCancelable(false);
    }

    public class TaskHandler extends AsyncTask<Void,String,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
            refreshLayout.setRefreshing(false);
        }
    }
}
