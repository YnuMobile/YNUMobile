package com.imooc.YnuMobile.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.imooc.YnuMobile.Login.UserLogin;
import com.imooc.YnuMobile.R;

/**
 * Created by 江树金 on 2016/5/4.
 */
public class User extends Fragment {

    private Button login;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.user,container,false);
        initView();
        return view;
    }

    private void initView() {
        login= (Button) view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserLogin.class));
            }
        });
    }



}
