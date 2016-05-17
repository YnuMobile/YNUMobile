package com.imooc.YnuMobile.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.imooc.YnuMobile.ClassRewrite.CommentListView;
import com.imooc.YnuMobile.Login.UserLogin;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.View.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class User extends Fragment {

    Button login;
    View view;
    CommentListView lv;
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.user,container,false);
        initView();
        return view;
    }

    private void initView() {
        /*
        * 登陆→相关
        * */
        login= (Button) view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserLogin.class));
            }
        });
        /*
        * listView→内容
        * */
        lv = (CommentListView) view.findViewById(R.id.lv);
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.listview_item,
                new String[]{"img", "title"},
                new int[]{R.id.itemimg, R.id.itemtitle});      //配置适配器
        lv.setAdapter(adapter);
    }

    /*
    * 数据的获取
    * */
    private List<? extends Map<String, ?>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "个人信息");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "成绩查询");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "我的课程表");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "奖助学金查询");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "选课系统");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "辅助功能");
        map.put("img", R.drawable.right);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "退出登录");
        map.put("img", R.drawable.right);
        list.add(map);

        return list;
    }
}
