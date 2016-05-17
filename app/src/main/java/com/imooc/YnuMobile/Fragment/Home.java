package com.imooc.YnuMobile.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.YnuMobile.ClassRewrite.CommentListView;
import com.imooc.YnuMobile.JsonAnalysis.JsonBean;
import com.imooc.YnuMobile.JsonAnalysis.ListAdapter;
import com.imooc.YnuMobile.JsonAnalysis.RequestApplication;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.RollViewpager.RollViewPager2;
import com.imooc.YnuMobile.View.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class Home extends Fragment{

    View view;
    Context context;
    RefreshLayout refreshLayout;
    SweetAlertDialog dialog;
    /*RollViewpager→轮播图*/
    private String[] titles;
    private ArrayList<View> dots;
    private TextView title;
    private RollViewPager2 rollViewPager2;
    private ArrayList<String> uriList;
    /*NewsList→新闻列表*/
    private CommentListView lv;
    private List<Map<String, Object>> list;
    /*JSON*/
    private String url="http://115.28.202.236:5000/news";
    ListAdapter listAdapter;
    List<JsonBean> jsonList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.home,container,false);
        initView();
        initUI();
        initListener();
        initData();
        //new asyncTask().execute(url);
        new Thread(new Task()).start();
        return view;
    }

    private void initView() {
        context=this.getActivity();
        refreshLayout= (RefreshLayout) view.findViewById(R.id.id_refresh);
        refreshLayout.setColorSchemeResources(R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout,
                R.color.swipeRefreshLayout);
        refreshLayout.setProgressBackgroundColor(R.color.bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dialog.show();
                new Thread(new Run()).start();
            }
        });
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                Toast.makeText(getActivity(), "正在操作上拉加载步骤！", Toast.LENGTH_SHORT).show();
            }
        });
        dialog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("正在加载中...");
        dialog.setCancelable(true);
        lv= (CommentListView) view.findViewById(R.id.id_listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //列表点击时间→获取数值
                String img=jsonList.get(position).url;
                String title=jsonList.get(position).title;
                String body=jsonList.get(position).body;
                String time=jsonList.get(position).time;
                /*
                * 队列传值
                * */
                //RequestApplication.setImg(img);
                RequestApplication.setTitle(title);
                RequestApplication.setBody(body);
                RequestApplication.setTime(time);
                startActivity(new Intent(getActivity(), NewsDetail.class));//新闻详情页
            }
        });
    }

    /*
    * 实现网络的异步访问
    * */
    class  asyncTask extends AsyncTask<String,Void,List<JsonBean>> {

        @Override
        protected List<JsonBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<JsonBean> jsonBeen) {
            super.onPostExecute(jsonBeen);
            listAdapter=new ListAdapter(getActivity(),jsonBeen);
            lv.setAdapter(listAdapter);
            Toast.makeText(context, "加载完成，久等啦~", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    /*
    * 将Url对应的json格式数据转化为我们所封装的JsonBean的对象
    * */
    private List<JsonBean> getJsonData(String url) {//通过url获取data
        jsonList=new ArrayList<>();
        try {
            String jsonString=readStream(new URL(url).openStream());//打开json的字符串接收
            JSONObject jsonObject;
            JSONArray jsonArray;
            JsonBean jsonBean;
            jsonArray=new JSONArray(jsonString);
            //jsonObject=new JSONObject(jsonString);//将获取到的json数据传入jsonObject;
            //JSONArray jsonArray=jsonObject.getJSONArray("answers");
            for (int i=0;i<jsonArray.length();i++){//循环取出
                jsonObject=jsonArray.getJSONObject(i);
                jsonBean=new JsonBean();
                Log.i("TAG", URLDecoder.decode(jsonObject.getString("imageurl"),"utf-8"));
                jsonBean.url=URLDecoder.decode(jsonObject.getString("imageurl"),"utf-8");
                jsonBean.title=jsonObject.getString("title");
                jsonBean.body=jsonObject.getString("content");
                jsonBean.time=jsonObject.getString("time");
                jsonList.add(jsonBean);//将输入传入list
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonList;//返回list
    }

    /*
    * 从InputStream解析url返回的数组
    * */
    private String readStream(InputStream is){//从inputStream中读取数据
        InputStreamReader isr;
        String result="";
        try {
            String line="";
            isr=new InputStreamReader(is,"utf-8");//字节流转换为字符流
            BufferedReader br=new BufferedReader(isr);//将字符流转换为buffered类型
            while ((line=br.readLine())!=null){
                result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                new asyncTask().execute(url);
                refreshLayout.setRefreshing(false);
            }else if (msg.what==2){
                new asyncTask().execute(url);
                refreshLayout.setRefreshing(false);
            }
        }
    };

    private class Run implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
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
        titles = new String[] { "云南大学启动K栈正式启动", "学长学姐的银色光辉毕业季开始了", "陈豪省长到校调研一带一路线战略研究院..."
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

    private class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg1=Message.obtain();
            msg1.what=2;
            handler.sendMessage(msg1);
        }
    }
}
