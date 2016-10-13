package com.example.glk.p2pmoney.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/13.
 */

public class PieChartActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.chart)
    PieChart chart;

    private Typeface mTf;
    private SpannableString mCenterText;

    @Override
    protected void initData() {

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mCenterText = generateCenterText();

        //右下角饼图的描述关闭
        chart.getDescription().setEnabled(false);
        //绘制内部圆的半径
        chart.setHoleRadius(52f);
        //绘制包裹最内层圆的外层圆的半径
        chart.setTransparentCircleRadius(57f);
        //内部圆的文字
        chart.setCenterText(mCenterText);
        chart.setCenterTextTypeface(mTf);
        chart.setCenterTextSize(9f);
        //是否显示百分比值
        chart.setUsePercentValues(true);
        chart.setExtraOffsets(5, 10, 50, 10);

        PieData mChartData = generateDataPie();

        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.WHITE);



        // set data
        chart.setData(mChartData);

        //显示饼图指示器一些信息在右上边
        Legend l = chart.getLegend();
        //设置位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        //设置饼图指示器Y中的空间
        l.setYEntrySpace(10f);
        //Y轴位置的偏移量，用于小幅调整位置
        l.setYOffset(0f);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateY(900);
    }

    @Override
    protected void initTitle() {
        titleTv.setText("资产管理");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_piechart;
    }

    @OnClick(R.id.title_left)
    public void back(){
        closeCurrent();
    }

    private PieData generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), "理财产品 " + (i+1)));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("资产分配");
        s.setSpan(new RelativeSizeSpan(2.3f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
        return s;
    }

}
