package com.imooc.YnuMobile.JsonAnalysis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.YnuMobile.R;

public class NewsDetail extends Activity {

    TextView detailTitle,detailBody,detailTime;
    ImageView detailImg;
    String title,time,body;
    int url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        getSend();
    }

    private void getSend() {
        title=RequestApplication.getTitle();
        time=RequestApplication.getTime();
        body=RequestApplication.getBody();
        //url=RequestApplication.getImg();
        //设置内容块
        detailBody.setText(body);
        detailTime.setText("来自云大新闻网   "+time);
        detailTitle.setText(title);
        //detailImg.setImageDrawable(R.drawable.testfive);
        //detailImg.setImageResource(url);
        //detailImg.setImageResource(url);
    }

    private void initView() {
        detailBody= (TextView) findViewById(R.id.detailBody);
        detailTitle= (TextView) findViewById(R.id.detailTitle);
        detailTime= (TextView) findViewById(R.id.detailTime);
        detailImg= (ImageView) findViewById(R.id.detailImg);
    }
}
