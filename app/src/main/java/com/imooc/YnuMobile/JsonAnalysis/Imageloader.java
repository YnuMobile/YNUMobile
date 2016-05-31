package com.imooc.YnuMobile.JsonAnalysis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 江树金 on 2016/4/23.
 */
public class Imageloader {
    //注：异步加载图片可以使用①多线程加载 或者 ②AsyncTask去加载。[个人感觉多线程的加载速度大于AsyncTask]
    private ImageView mimageView;
    private String mUrl;
    //创建cache
    private LruCache<String,Bitmap> mCache;


    public void showImagerByThread(ImageView imageView, final String url){
        mimageView=imageView;
        mUrl=url;
        new Thread(new Runnable() {//多线程加载
            @Override
            public void run() {
                Bitmap bitmap=getBitmapFromUrl(url);
                Message msg=Message.obtain();
                msg.obj=bitmap;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在handler中处理UI
           //避免缓存的图片对正确图片的影响，viewholder会重新加载设定图片
            if (mimageView.getTag().equals(mUrl)){
                mimageView.setImageBitmap((Bitmap) msg.obj);//避免影响之后再去加载bitmap
            }
        }
    };

    /*
    * 公用方法 获取URL并存为bitMap类型 进行加载时可选择多线程或者AsyncTask进行加载
    * */
    public Bitmap getBitmapFromUrl(String UrlString){
        Bitmap bitmap;
        InputStream is;
        try {
            URL url=new URL(UrlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());//获取InputStream对象
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;//拿到资源
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        //从缓存中取出对应的图片
        Bitmap bitmap=getBitmapFromCache(url);
        if (bitmap==null){
            //说明内存中没有该图片 只能从网络中获取图片
            new newAsyncTask(imageView,url).execute(url);//将url传递到newAsyncTask中处理。
        }else {
            //如果有就直接使用该图片
            imageView.setImageBitmap(bitmap);
        }
    }

    private class newAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private ImageView mImageView;
        private String murl;

        @Override
        protected Bitmap doInBackground(String... params) {//完成异步加载任务
            //先进行下载，在判断缓存中有没有
            String url=params[0];
            Bitmap bitmap=getBitmapFromUrl(url);
            if (bitmap!=null){
                //加入缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        public newAsyncTask(ImageView imageView,String url){
            mImageView=imageView;
            murl=url;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView.getTag().equals(murl)){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    public Imageloader(){
        /*
        *设定一部分内存转化为我们的缓存空间.Lru算法
        * */
        int MaxMemory= (int) Runtime.getRuntime().maxMemory();
        int cacheSize=MaxMemory/4;
        mCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //返回实际大小
                return value.getByteCount();//在每次存入缓存的时候调用该方法，告诉当前系统存入的对象有多大
            }
        };
    }

    /*
    * 将url增加到缓存
    * 在增加之前校验缓存是否存在
    * */
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromCache(url)==null){
            mCache.put(url,bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url){//通过url去返回指定的cache
        return mCache.get(url);//LruCache 本质上就是map  可以直接获得
    }
}
