package com.imooc.YnuMobile.JsonAnalysis;

import android.app.Application;

/**
 * Created by 江树金 on 2016/5/16.
 */
public class RequestApplication extends Application {
    public static int img;
    public static String title;
    public static String body;
    public static String time;

    public static int getImg() {
        return img;
    }

    public static void setImg(int img) {
        RequestApplication.img = img;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        RequestApplication.title = title;
    }

    public static String getBody() {
        return body;
    }

    public static void setBody(String body) {
        RequestApplication.body = body;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        RequestApplication.time = time;
    }
}
