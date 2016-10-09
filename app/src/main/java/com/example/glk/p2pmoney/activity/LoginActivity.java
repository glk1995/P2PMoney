package com.example.glk.p2pmoney.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.glk.p2pmoney.MainActivity;
import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.bean.Login;
import com.example.glk.p2pmoney.common.AppNetConfig;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.example.glk.p2pmoney.util.BitMapUtil;
import com.example.glk.p2pmoney.util.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/8.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.log_ed_mob)
    EditText logEdMob;
    @BindView(R.id.about_com)
    RelativeLayout aboutCom;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.log_ed_pad)
    EditText logEdPad;
    @BindView(R.id.log_log_btn)
    Button logLogBtn;

    @Override
    protected void initData() {

    }

    @OnClick(R.id.log_log_btn)
    public void clickLogin(View view){
        login();
    }

    private void login() {
        String username = logEdMob.getText().toString();
        String password = logEdPad.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            RequestParams params = new RequestParams();
            //根据接口文档来
            params.put("username",username);
            //直接传入password肯定失败，因为这边是MD5加密的,使用MD5工具类加密
            params.put("password", MD5Utils.MD5(password));
            client.post(AppNetConfig.LOGIN,params,new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    JSONObject jsonObject = JSON.parseObject(content);
                    if(jsonObject.getBoolean("success")){
                        //登录成功
                        String data = jsonObject.getString("data");
                        Login login = JSON.parseObject(data,Login.class);
                        //保存到SharedPreference中
                        saveLogin(login);
                        //跳转到首页
                        gotoActivity(MainActivity.class,null);
                    }
                }

                @Override
                protected void sendFailureMessage(Throwable e, String responseBody) {
                    super.sendFailureMessage(e, responseBody);
                }
            });
        }
    }

    @Override
    protected void initTitle() {
        titleTv.setText("用户登录");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick(R.id.title_left)
    public void back(View view){
        closeCurrent();
    }


}
