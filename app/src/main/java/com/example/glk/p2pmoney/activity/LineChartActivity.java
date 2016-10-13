package com.example.glk.p2pmoney.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zgqdg on 2016/10/12.
 */

public class LineChartActivity extends BaseActivity {
    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.chart)
    LineChart chart;

    private Typeface mTf;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //如果调用setDescrption，就是设置右下角文字信息
        chart.getDescription().setEnabled(false);
        //设置绘制网格背景
        chart.setDrawGridBackground(false);

        //绘制图标的X轴
        XAxis xAxis = chart.getXAxis();
        //设置X轴在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        //绘制x轴网格线
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        //设置Y轴右边不显示
        chart.getAxisRight().setEnabled(false);
        //绘制图标Y轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        //设置10个刻度区间,第二个参数设置为true，导致可能出现不平衡的值
        leftAxis.setLabelCount(10, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(10);

        // set data
        chart.setData(generateDataLine());

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateX(750);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {

        //Y轴方向的数据
        ArrayList<Entry> e1 = new ArrayList<Entry>();

        //这边是随机的数据
        for (int i = 0; i < 7; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 5) + 2));
        }

        LineDataSet d1 = new LineDataSet(e1, "近7日年化利率" );
        //指定数据集合绘制的时候的属性:线宽、数据节点圆形的大小、颜色、数据是显示
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);


        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);

        LineData cd = new LineData(sets);
        return cd;
    }


    @Override
    protected void initTitle() {
        titleTv.setText("近7日年化利率");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.title_left)
    public void back(View view){
        closeCurrent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_linechart;
    }

}
