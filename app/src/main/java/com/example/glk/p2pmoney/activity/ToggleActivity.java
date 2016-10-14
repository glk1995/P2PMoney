package com.example.glk.p2pmoney.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
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
        tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //密码已经开启
                    UIUtils.Toast("手势密码已经开启",false);
                }else {
                    UIUtils.Toast("手势密码已经关闭",false);
                }
            }
        });
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
