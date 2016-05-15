package com.imooc.YnuMobile.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.YnuMobile.Adapter.GridViewAdapter;
import com.imooc.YnuMobile.ClassRewrite.CommentListView;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.RollViewpager.RollViewPager2;
import com.imooc.YnuMobile.View.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class Home extends Fragment {

    View view;
    Context context;
    SwipeRefreshLayout refreshLayout;
    SweetAlertDialog dialog;
    /*RollViewpager→轮播图*/
    private String[] titles;
    private ArrayList<View> dots;
    private TextView title;
    private RollViewPager2 rollViewPager2;
    private ArrayList<String> uriList;
    /*NewsList→新闻列表*/
    private CommentListView lv;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.home,container,false);
        initView();
        initUI();
        initListener();
        initData();
        return view;
    }

    private void initView() {
        context=this.getActivity();
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
                dialog.show();
            }
        });
        dialog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("正在加载中...");
        dialog.setCancelable(true);
        lv= (CommentListView) view.findViewById(R.id.id_listView);
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.newslist,
                new String[]{"img", "title", "body"},
                new int[]{R.id.itemimg, R.id.itemtitle, R.id.itembody});      //配置适配器，并获取对应Item中的ID
        lv.setAdapter(adapter);
    }

    //数据的获取@！
    private List<? extends Map<String, ?>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//将需要的值传入map中
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "云南大学众创空间正式启动");
        map.put("body", "为认真贯彻落实“创新是引领发展的第一动力”的理念，扎实推进“大众创业、万众创新”");
        map.put("img", R.drawable.testone);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "云大“院士林”：经年之后将长成参天大树");
        map.put("body", "为不断培育和彰显科学精神，以承办中国科学院“材料科学进展”和“工程科技进展”技术科学论坛为契机");
        map.put("img", R.drawable.testotwo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "陈豪省长到校调研一带一路战略研究院 顾秉林院士受聘云南省政府顾问");
        map.put("body", "为主动融入和服务国家“一带一路”战略，助力云南省成为中国民族团结进步示范区、生态文明建设排头兵、面向南亚东南亚辐射中心");
        map.put("img", R.drawable.testthree);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "省部共建云南生物资源保护与利用国家重点实验室挂牌运行");
        map.put("body", "4月16日，云南省人民政府和科技部依托云南大学和云南农业大学共建的云南生物资源保护与利用国家重点实验室建设运行启动会暨第一届第一次学术委员");
        map.put("img", R.drawable.testfour);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "省委副书记钟勉考察并高度评价云大启迪K栈众创空间");
        map.put("body", "在云南大学党委书记杨林，校长林文勋，党委副书记张昌山、李建宇，副校长王建华，校长助理郝淑美以及相关职能部门负责人陪同下");
        map.put("img", R.drawable.testfive);
        list.add(map);

        return list;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                dialog.dismiss();
                Toast.makeText(context, "页面马上完善，请稍等哒~", Toast.LENGTH_SHORT).show();
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

    private void initUI() {
        title = (TextView) view.findViewById(R.id.title);
        rollViewPager2 = (RollViewPager2) view.findViewById(R.id.viewpager);
    }

    private void initListener() {
    }

    private void initData() {
        titles = new String[] { "云南大学启动K栈正式启动", "银色光辉下的毕业季开始", "陈豪省长到校调研一带一路线战略研究院..."
                , "省部共建云南生物资源保护与利用国家重点实验室..",
                "云大“院士林”：经年之后将长成参天大树..." };

        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.dot_0));
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
        dots.add(view.findViewById(R.id.dot_3));
        dots.add(view.findViewById(R.id.dot_4));

        uriList = new ArrayList<String>();
        uriList.add("http://www.news.ynu.edu.cn/picture/images/201603/1458381567742.jpg");
        uriList.add("http://www.ynu.edu.cn/images/content/2013-12/20131022162236810671.jpg");
        uriList.add("http://www.news.ynu.edu.cn/picture/images/201604/1461732606920.jpg");
        uriList.add("http://www.news.ynu.edu.cn/picture/images/201604/1460800559770.JPG");
        uriList.add("http://www.news.ynu.edu.cn/picture/images/201604/1461679299319.jpg");

        rollViewPager2.setUriList(uriList);

        rollViewPager2.setDot(dots, R.drawable.dot_focus, R.drawable.dot_normal);

        rollViewPager2.setTitle(title, titles);
        rollViewPager2.startRoll();


    }

}
