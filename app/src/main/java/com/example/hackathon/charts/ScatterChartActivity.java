/*Copyright 2017 BakerJ.

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at following link.

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitat*/

package com.example.hackathon.charts;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.DoubleDriCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.daivd.chart.provider.component.point.Point;
import com.example.hackathon.R;
import com.example.hackathon.gson.TableDataSealed;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ScatterChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private TableDataSealed data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        data = new Gson().fromJson(getIntent().getExtras().getString("data"), TableDataSealed.class);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this, 12);

        ArrayList<String> chartYDataList = new ArrayList<>();

        for (int i = 0; i < data.getArray().size(); i++) {
            chartYDataList.add(data.getArray().get(i).getPtype());
        }
        ArrayList<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> conversion_rate = new ArrayList<>();
        for (int i = 0; i < data.getArray().size(); i++) {
            conversion_rate.add(data.getArray().get(i).getConversion_rate_per_user());
        }
        LineData columnData1 = new LineData("ConversionRatePerUser ", "%", IAxis.AxisDirection.RIGHT, getResources().getColor(R.color.arc3), conversion_rate);
        ArrayList<Double> views = new ArrayList<>();
        for (int i = 0; i < data.getArray().size(); i++) {
            views.add(data.getArray().get(i).getViews_per_user());
        }
        LineData columnData2 = new LineData("ViewsPerUser ", "", IAxis.AxisDirection.LEFT, getResources().getColor(R.color.arc2), views);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("Data", chartYDataList, ColumnDatas);

        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis = lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis = lineChart.getHorizontalAxis();
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        verticalAxis.setDrawGrid(true);
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        DoubleDriCross cross = new DoubleDriCross();
        LineStyle crossStyle = cross.getCrossStyle();
        crossStyle.setWidth(this, 1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        lineChart.getProvider().setCross(cross);
        lineChart.setZoom(true);
        lineChart.getProvider().setOpenCross(true);
        lineChart.getProvider().setOpenMark(true);
        lineChart.getProvider().setMarkView(new BubbleMarkView(this));
        Point point = new Point();
        point.getPointStyle().setShape(PointStyle.CIRCLE);
        lineChart.getProvider().setPoint(point);

        lineChart.setShowChartName(false);
        lineChart.getChartTitle().setDirection(IComponent.BOTTOM);
        lineChart.getChartTitle().setPercent(0.2f);
        FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this, 15);
        LevelLine levelLine = new LevelLine(20);
        DashPathEffect effects2 = new DashPathEffect(new float[]{1, 2, 2, 4}, 1);
        levelLine.getLineStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        LegendPoint legendPoint = (LegendPoint) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.CIRCLE);
        lineChart.getLegend().setPercent(0.2f);
        lineChart.getHorizontalAxis().setRotateAngle(45);
        lineChart.getHorizontalAxis().setAxisDirection(IAxis.AxisDirection.TOP);
        lineChart.getProvider().setDrawLine(false);
        lineChart.getHorizontalAxis().setFormat(new IFormat<String>() {
            @Override
            public String format(String s) {
                return s; //测试
            }
        });
        lineChart.getProvider().setArea(false);
        lineChart.getLegend().setSelectColumn(true);
        lineChart.getProvider().setShowText(true);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(1000);
    }


}
