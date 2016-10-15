package com.example.glk.p2pmoney.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.example.glk.p2pmoney.ui.GestureEditActivity;
import com.example.glk.p2pmoney.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/13.
 */

public class ToggleActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.tg)
    ToggleButton tg;

    @Override
    protected void initData() {
        if(getFlag()){
            tg.setChecked(true);
        }

        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //密码已经开启
                    UIUtils.Toast("手势密码已经开启",false);
                    gotoActivity(GestureEditActivity.class,null);
                }else {
                    UIUtils.Toast("手势密码已经关闭",false);
                    saveGesture(null);
                }
            }
        });
    }


    /**
     * 获取登录手势密码开启状态
     * @return
     */
    public boolean getFlag(){
        SharedPreferences sp = getSharedPreferences("gesture", MODE_PRIVATE);
        String flag= sp.getString("g_boolean","");
        boolean b = Boolean.parseBoolean(flag);

        return b;
    }


    /**
     * 保存手势密码信息
     * @param inputCode
     */
    public void saveGesture(String inputCode){
        SharedPreferences sp = getSharedPreferences("gesture", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("g_boolean","false");
        edit.putString("g_password",inputCode);
        edit.commit();
    }

    @Override
    protected void initTitle() {
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
        titleTv.setText("安全管理");
    }

    @OnClick(R.id.title_left)
    public void close() {
        closeCurrent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toggle;
    }

}
