package com.imooc.YnuMobile.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.YnuMobile.R;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class User extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view=inflater.inflate(R.layout.user,container,false);
        return view;
    }
}
