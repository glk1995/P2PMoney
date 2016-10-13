package com.example.glk.p2pmoney.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.example.glk.p2pmoney.util.UIUtils;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/11.
 */

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.account_zhifubao)
    TextView accountZhifubao;
    @BindView(R.id.select_bank)
    RelativeLayout selectBank;
    @BindView(R.id.chongzhi_text)
    TextView chongzhiText;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.input_money)
    EditText inputMoney;
    @BindView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.btn_tixian)
    Button btnTixian;



    @Override
    protected void initData() {
    }

    @Override
    protected void initTitle() {
        titleTv.setText("提现");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.title_left)
    public void back(View view){
        closeCurrent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tixian;
    }

    @OnClick(R.id.btn_tixian)
    public void clicktx(View view){
        if(!TextUtils.isEmpty(inputMoney.getText().toString())){
            Toast.makeText(this,"提现请求已被成功受理...审核通过后,24小时内到账...",Toast.LENGTH_SHORT).show();
            UIUtils.gethandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeCurrent();
                }
            },2000);
        }
    }

}
