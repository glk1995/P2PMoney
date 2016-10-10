package com.example.glk.p2pmoney.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/10/10.
 */

public class ChongzhiActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.chongzhi_text)
    TextView chongzhiText;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.chongzhi_et)
    EditText chongzhiEt;
    @BindView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @BindView(R.id.yue_tv)
    TextView yueTv;
    @BindView(R.id.chongzhi_btn)
    Button chongzhiBtn;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        titleTv.setText("充值");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chongzhi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



}
