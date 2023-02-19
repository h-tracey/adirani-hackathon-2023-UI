package com.example.hackathon.charts;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.BarChart;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.example.hackathon.R;
import com.example.hackathon.gson.ConversionSealed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private ConversionSealed data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        String dataString = getIntent().getExtras().getString("data");
        data = new Gson().fromJson(dataString, ConversionSealed.class);
        barChart = (BarChart) findViewById(R.id.columnChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this, 8);

        List<String> chartYDataList = new ArrayList<>();
        for (int i = 0; i < data.getArray().size(); i++) {
            chartYDataList.add(data.getArray().get(i).getDefault_channel_group());
        }

        ArrayList<Double> conversionsList = new ArrayList<>();
        for (int i = 0; i < data.getArray().size(); i++) {
            conversionsList.add(data.getArray().get(i).getConversions());
        }

        BarData columnData1 = new BarData("ConversionsList", "%", getResources().getColor(R.color.arc3), conversionsList);

        List<BarData> ColumnDatas = new ArrayList<>();
        ColumnDatas.add(columnData1);

        ChartData<BarData> chartData = new ChartData<>("bar chart", chartYDataList, ColumnDatas);
        barChart.setChartData(chartData);
        barChart.startChartAnim(1000);
        barChart.setZoom(true);
        barChart.setShowChartName(false);
        FontStyle fontStyle = barChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc23));
        fontStyle.setTextSpSize(this, 15);
        barChart.getProvider().setOpenMark(true);
        barChart.getProvider().setOpenCross(true);
        LevelLine levelLine = new LevelLine(20);
        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 2, 1}, 1);
        levelLine.getLineStyle().setWidth(this, 2).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        barChart.getProvider().addLevelLine(levelLine);
        barChart.getLeftVerticalAxis().getGridStyle().setEffect(effects);
        barChart.getProvider().setMarkView(new BubbleMarkView(this));
        LegendPoint legendPoint = (LegendPoint) barChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.CIRCLE);
        BaseAxis vaxis = barChart.getLeftVerticalAxis();
        vaxis.setDrawGrid(true);
        vaxis.getGridStyle().setColor(R.color.arc_inteval);
        barChart.getLegend().setDirection(IComponent.TOP);

    }
}
