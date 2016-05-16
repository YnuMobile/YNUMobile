package com.imooc.YnuMobile.JsonAnalysis;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.YnuMobile.R;

import java.util.List;

/**
 * Created by 江树金 on 2016/5/16.
 */
public class ListAdapter extends BaseAdapter {

    private List<JsonBean> mlist;//JsonBean的list
    private LayoutInflater mInflater;
    private Imageloader mImageloader;
    Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    //将数据映射过来
    public ListAdapter(Context context, List<JsonBean> data){
        mlist=data;
        mInflater= LayoutInflater.from(context);
        mImageloader=new Imageloader();
        this.context=context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.newslist,null);
            viewHolder.img= (ImageView) convertView.findViewById(R.id.img);
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("========点击了头像========", "点击事件启动-------------- ");
                }
            });
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.body= (TextView) convertView.findViewById(R.id.body);
            viewHolder.time= (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String url=mlist.get(position).url;
        viewHolder.img.setTag(url);//进行绑定，为了在imageLoader中的语句，
        /*
        * @二选一进行图片加载
        * */
        /*mImageloader.showImagerByThread(viewHolder.img,mlist.get(position).url);//使用多线程的方法加载图片*/
        mImageloader.showImageByAsyncTask(viewHolder.img,url);//使用AsyncTask的方式加载
        viewHolder.title.setText(mlist.get(position).title);//从JsonBean中取出元素设置给viewholder的元素
        viewHolder.body.setText(mlist.get(position).body);
        viewHolder.time.setText("发布时间 : "+mlist.get(position).time);
        return  convertView;
    }

    class ViewHolder{
        public TextView title;
        public TextView body;
        public TextView time;
        public ImageView img;
    }
}

