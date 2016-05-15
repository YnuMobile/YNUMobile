package com.imooc.YnuMobile.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imooc.YnuMobile.Adapter.GridViewAdapter;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.View.GridView;

/**
 * Created by 江树金 on 2016/5/11.
 */
public class TabOne extends Fragment {

    View view;
    GridView gridView;
    Context context;
    SwipeRefreshLayout refreshLayout;
    /*
    * 图片轮播.参*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tabone,container,false);
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
                new Thread(new Run()).start();
            }
        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Toast.makeText(context, "刷新马上就好，请稍等哒~", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        }
    };

    private class Run implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=Message.obtain();
            msg.what=1;
            handler.sendMessage(msg);
        }
    }

}
