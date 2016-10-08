package com.example.glk.p2pmoney.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.glk.p2pmoney.bean.Login;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/10/8.
 */

public abstract class BaseActivity extends FragmentActivity{

    protected AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        initTitle();
        initData();
    }

    protected abstract void initData();

    protected abstract void initTitle();

    public abstract int getLayoutId();

    /**
     * 关闭当前activity
     */
    public void closeCurrent(){
        AppManager.getInstance().removeCurrent();
    }

    /**
     * 保存登录信息
     * @param login
     */
    public void saveLogin(Login login){
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("UF_ACC",login.UF_ACC);
        edit.putString("UF_AVATAR_URL",login.UF_AVATAR_URL);
        edit.putString("UF_IS_CERT",login.UF_IS_CERT);
        edit.putString("UF_PHONE",login.UF_PHONE);
        edit.commit();
    }

    /**
     * 获取登录信息
     * @return
     */
    public Login getLogin(){
        Login login = new Login();
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        login.UF_ACC = sp.getString("UF_ACC","");
        login.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL","");
        login.UF_IS_CERT = sp.getString("UF_IS_CERT","");
        login.UF_PHONE = sp.getString("UF_PHONE","");
        return login;
    }

    /**
     * 跳转到目标Activity
     * @param clazz
     * @param bundle
     */
    public void gotoActivity(Class clazz,Bundle bundle){
        Intent it = new Intent(this,clazz);
        if(bundle != null){
            it.putExtra("param",bundle);
        }
        startActivity(it);
    }
}
