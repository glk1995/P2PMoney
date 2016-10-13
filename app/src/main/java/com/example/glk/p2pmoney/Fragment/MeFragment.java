package com.example.glk.p2pmoney.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.activity.BarChartActivity;
import com.example.glk.p2pmoney.activity.ChongzhiActivity;
import com.example.glk.p2pmoney.activity.LineChartActivity;
import com.example.glk.p2pmoney.activity.PieChartActivity;
import com.example.glk.p2pmoney.activity.TiXianActivity;
import com.example.glk.p2pmoney.activity.UserInfoActivity;
import com.example.glk.p2pmoney.bean.Login;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.example.glk.p2pmoney.common.BaseFragment;
import com.example.glk.p2pmoney.util.BitMapUtil;
import com.example.glk.p2pmoney.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import com.example.glk.p2pmoney.activity.LoginActivity;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.OnClick;


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
        titleRight.setVisibility(View.VISIBLE);
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
        Picasso.with(getActivity()).load(login.UF_AVATAR_URL).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap zoom = BitMapUtil.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                Bitmap circleBitmap = BitMapUtil.circleBitmap(zoom);
                //这边需要注意两点
                //1:transform当中处理完图片之后，需要调用recycle方法会受
                source.recycle();
                return circleBitmap;
            }

            @Override
            public String key() {
                //2:重写key方法的返回值，不能是null
                return "";
            }
        }).into(imageView1);
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

    @OnClick(R.id.title_right)
    public void clickUserSetting(View view){
        ((BaseActivity) getActivity()).gotoActivity(UserInfoActivity.class,null);
    }

    @OnClick(R.id.chongzhi)
    public void cz(View view){
        ((BaseActivity) getActivity()).gotoActivity(ChongzhiActivity.class,null);
    }

    @OnClick(R.id.tixian)
    public void tx(View view){
        ((BaseActivity) getActivity()).gotoActivity(TiXianActivity.class,null);
    }

    @OnClick(R.id.ll_touzi)
    public void line(View view){
        ((BaseActivity) getActivity()).gotoActivity(LineChartActivity.class,null);
    }

    @OnClick(R.id.ll_touzi_zhiguan)
    public void bar(View view){
        ((BaseActivity)getActivity()).gotoActivity(BarChartActivity.class,null);
    }

    @OnClick(R.id.ll_zichang)
    public void pie(View view){
        ((BaseActivity) getActivity()).gotoActivity(PieChartActivity.class,null);
    }

}
