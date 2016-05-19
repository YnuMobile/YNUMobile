package com.imooc.YnuMobile.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.imooc.YnuMobile.MainActivity;
import com.imooc.YnuMobile.R;
import com.imooc.YnuMobile.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserLogin extends Activity {
    private EditText useridEditText;
    private EditText passwdEditText;
    private EditText capchaEditText;
    private NetworkImageView captchaImage;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private ImageButton login;

    private String tempguid;
    private String userid;
    private String passwd;
    private String captcha;
    private String imgGuid;
    private String tempGuid;
    private String captchaUrl="http://202.203.209.96/v5api/api/GetLoginCaptchaInfo/null";
    private String loginUrl="http://202.203.209.96/v5api/OAuth/Token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        requestQueue.start();
        imageLoader = VolleySingleton.getInstance(this).getImageLoader();
        init();
    }
    public void init(){
        useridEditText = (EditText) findViewById(R.id.login_username);
        passwdEditText = (EditText) findViewById(R.id.login_password);
        capchaEditText = (EditText) findViewById(R.id.identifying);
        captchaImage = (NetworkImageView) findViewById(R.id.captcha_image);
        login = (ImageButton) findViewById(R.id.login_btn);


        final JsonObjectRequest firstCaptcha = new JsonObjectRequest(captchaUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());
                try {
                    tempGuid= (String) response.get("TempGuid");
                    imgGuid= (String) response.get("ImgGuid");
                    captchaImage.setImageUrl("http://202.203.209.96/vimgs/"+imgGuid+".png",imageLoader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(firstCaptcha);


        captchaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(firstCaptcha);
                Log.d("YNUTAG",">>>>>>1");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid=String.valueOf(useridEditText.getText());
                passwd=String.valueOf(passwdEditText.getText());
                captcha=String.valueOf(capchaEditText.getText());
                Map<String,String> value=new HashMap<String, String>();
                value.put("grant_type", "password");
                value.put("username", userid);
                value.put("password", passwd + "|" + captcha + "*" + tempGuid);
                value.put("client_id", "ynumisSite");


                JSONObject jsonObject=new JSONObject(value);
                Log.d("TAG", jsonObject.toString());

                MyPostRequest login=new MyPostRequest(loginUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG",response.toString());
                        try {

                            VolleySingleton.setMyCookie("Bearer "+response.getString("access_token"));
                            Log.d("TAG",VolleySingleton.getMyCookie());
                            Intent intent = new Intent(UserLogin.this,MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }, value);
                requestQueue.add(login);
            }
        });
    }

    private class MyPostRequest extends Request {

        private Map mMap;
        private Response.Listener mListener;
        public MyPostRequest(String url, Response.Listener listener,Response.ErrorListener errorListener, Map map){
            super(Method.POST,url,errorListener) ;
            mMap=map;
            mListener=listener;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mMap;
        }

        @Override
        protected Response parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(Object response) {
            mListener.onResponse(response);
        }

    }

}
