package com.example.glk.p2pmoney.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.bean.Login;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.example.glk.p2pmoney.common.BaseFragment;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import com.example.glk.p2pmoney.activity.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zgqdg on 2016/9/21.
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.icon_time)
    RelativeLayout iconTime;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.chongzhi)
    ImageView chongzhi;
    @BindView(R.id.tixian)
    ImageView tixian;
    @BindView(R.id.ll_touzi)
    TextView llTouzi;
    @BindView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @BindView(R.id.ll_zichang)
    TextView llZichang;
    @BindView(R.id.ll_zhanquan)
    TextView llZhanquan;

    @Override
    protected RequestParams getParams() {

        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initTitle() {
        titleTv.setText("我的资产");
        titleLeft.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData(String content) {
        isLogin();
    }

    private void isLogin() {
        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uf_acc = sp.getString("UF_ACC", "");
        if(TextUtils.isEmpty(uf_acc)){
            //未登录
            showLoginDialog();
        }else {
            //已登录---处理信息
            doUserInfo();
        }
    }

    private void doUserInfo() {
        //强转获取Login信息
        Login login = ((BaseActivity) getActivity()).getLogin();
        //设置用户名
        textView11.setText(login.UF_ACC);
        //设置图片
        Picasso.with(getActivity()).load(login.UF_AVATAR_URL).into(imageView1);
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("登录");
        builder.setMessage("必须先登录...");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((BaseActivity)getActivity()).gotoActivity(LoginActivity.class,null);
            }
        });
        //强制登录，设置为不可消失
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
