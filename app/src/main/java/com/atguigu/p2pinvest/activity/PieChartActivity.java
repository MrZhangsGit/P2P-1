package com.atguigu.p2pinvest.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class PieChartActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.chart_pieChart)
    PieChart chartPieChart;

    private Typeface mTf;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");

        // apply styling
        chartPieChart.setDescription("饼状图信息描述");
        chartPieChart.setHoleRadius(52f);
        chartPieChart.setTransparentCircleRadius(57f);
        chartPieChart.setCenterText("MPChart\nAndroid");//饼状图中间园内的文字信息
        chartPieChart.setCenterTextTypeface(mTf);
        chartPieChart.setCenterTextSize(18f);//中间文字大小
        chartPieChart.setUsePercentValues(true);

        generateDataPie().setValueFormatter(new PercentFormatter());
        generateDataPie().setValueTypeface(mTf);
        generateDataPie().setValueTextSize(11f);
        generateDataPie().setValueTextColor(Color.WHITE);
        // set data
        chartPieChart.setData(generateDataPie());

        Legend l = chartPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // do not forget to refresh the chart
        // chartPieChart.invalidate();
        chartPieChart.animateXY(900, 900);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("1st Quarter");
        q.add("2nd Quarter");
        q.add("3rd Quarter");
        q.add("4th Quarter");

        return q;
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopName.setText("饼状图Demo");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    //返回按钮的点击---返回
    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        removeCurrentActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

}
