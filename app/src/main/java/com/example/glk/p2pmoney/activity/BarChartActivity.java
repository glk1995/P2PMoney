package com.example.glk.p2pmoney.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/13.
 */

public class BarChartActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.chart)
    BarChart chart;

    private Typeface mTf;

    @Override
    protected void initData() {

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //设置网格描述关闭，可以调用setDescription()设置名字
        chart.getDescription().setEnabled(false);
        //设置网格背景
        chart.setDrawGridBackground(false);
        //阴影
        chart.setDrawBarShadow(false);

        XAxis xAxis = chart.getXAxis();
        //设置X轴在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        //设置水平网格线
        xAxis.setDrawGridLines(false);
        //基准线
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5,false);
        //Y轴方向的最高的柱长度，距离顶部的距离
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); //this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        //设置数据
        BarData barData = generateDataBar();
        barData.setValueTypeface(mTf);

        // set data
        chart.setData(barData);
        chart.setFitBars(true);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateY(700);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "投资管理");
        //颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //高亮的透明度，值为255点击的时候显示黑色，0的时候无任何效果
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    @OnClick(R.id.title_left)
    public void close(View view){
        closeCurrent();
    }

    @Override
    protected void initTitle() {
        titleTv.setText("投资管理");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_barchart;
    }

}
