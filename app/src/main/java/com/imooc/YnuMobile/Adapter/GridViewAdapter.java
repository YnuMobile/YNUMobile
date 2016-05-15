package com.imooc.YnuMobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.View.BaseViewHolder;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;

    public String[] img_text = { "云大新闻", "云通讯录", "云大校历", "云大地图", "数字云大", "公共服务",
            "90校庆", "校巴时刻表", "关于我们", };
    public int[] imgs = { R.drawable.app_icon, R.drawable.gv_2,
            R.drawable.gv_3, R.drawable.gv_4,
            R.drawable.gv_5, R.drawable.gv_6,
            R.drawable.gv_7, R.drawable.gv_8, R.drawable.gv_9 };

    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.gridview_item, null);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }
}
