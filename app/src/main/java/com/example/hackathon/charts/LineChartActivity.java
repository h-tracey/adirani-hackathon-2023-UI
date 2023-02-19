package com.example.hackathon.charts;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.axis.VerticalAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.daivd.chart.provider.component.point.Point;
import com.daivd.chart.provider.component.tip.MultiLineBubbleTip;
import com.daivd.chart.utils.DensityUtils;
import com.example.hackathon.R;
import com.example.hackathon.gson.TouchSealed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

//    private LineChart lineChart;
//    private TouchSealed data;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_line);
//        String dataString = getIntent().getExtras().getString("data");
//        data = new Gson().fromJson(dataString, TouchSealed.class);
//        lineChart = (LineChart) findViewById(R.id.lineChart);
//        Resources res = getResources();
//        FontStyle.setDefaultTextSpSize(this, 12);
//        List<String> chartYDataList = new ArrayList<>();
//        for (int i = 0; i < data.getArray().size(); i++) {
//            chartYDataList.add(String.valueOf(data.getArray().get(i).getNum_touchpoints()));
//        }
//        List<LineData> ColumnDatas = new ArrayList<>();
//        ArrayList<Double> conversionList1 = new ArrayList<>();
//        for (int i = 0; i < data.getArray().size(); i++) {
//            conversionList1.add(data.getArray().get(i).getNum_conversions());
//        }
//        final LineData columnData1 = new LineData("conversion", "", IAxis.AxisDirection.LEFT, getResources().getColor(R.color.arc3), conversionList1);
//        ColumnDatas.add(columnData1);
//        ChartData<LineData> chartData2 = new ChartData<>("Data", chartYDataList, ColumnDatas);
//        lineChart.setLineModel(LineChart.CURVE_MODEL);
//        BaseAxis verticalAxis = lineChart.getLeftVerticalAxis();
//        BaseAxis horizontalAxis = lineChart.getHorizontalAxis();
//        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
//        verticalAxis.setDrawGrid(true);
//        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
//        horizontalAxis.setDrawGrid(true);
//        verticalAxis.getAxisStyle().setWidth(this, 1);
//        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
//        verticalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
//        horizontalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
//        VerticalCross cross = new VerticalCross();
//        LineStyle crossStyle = cross.getCrossStyle();
//        crossStyle.setWidth(this, 1);
//        crossStyle.setColor(res.getColor(R.color.arc21));
//        lineChart.getProvider().setCross(cross);
//        lineChart.setZoom(true);
//        lineChart.getProvider().setOpenCross(true);
//        lineChart.getProvider().setOpenMark(false);
//        lineChart.getProvider().setMarkView(new CustomMarkView(this));
//        Point point = new Point();
//        point.getPointStyle().setShape(PointStyle.CIRCLE);
//        lineChart.getProvider().setPoint(point);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setTextSize(DensityUtils.sp2px(this, 13));
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        MultiLineBubbleTip tip = new MultiLineBubbleTip<LineData>(this,
//                R.mipmap.round_rect, R.mipmap.triangle, paint) {
//            @Override
//            public boolean isShowTip(LineData lineData, int position) {
//                return position == 2;
//            }
//
//            @Override
//            public String[] format(LineData lineData, int position) {
//                String title = lineData.getName();
//                String value = lineData.getChartYDataList().get(position) + lineData.getUnit();
//                return new String[]{title, value};
//            }
//        };
//        tip.setColorFilter(Color.parseColor("#FA8072"));
//        tip.setAlpha(0.8f);
//        lineChart.getProvider().setTip(tip);
//        lineChart.setShowChartName(false);
//        lineChart.getMatrixHelper().setWidthMultiple(3);
//        lineChart.getChartTitle().setDirection(IComponent.TOP);
//        lineChart.getChartTitle().setPercent(0.2f);
//        FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
//        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
//        fontStyle.setTextSpSize(this, 15);
//
//        LevelLine levelLine = new LevelLine(20);
//        DashPathEffect effects2 = new DashPathEffect(new float[]{1, 2, 2, 4}, 1);
//        levelLine.getLineStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
//        levelLine.getLineStyle().setEffect(effects2);
//        lineChart.getProvider().addLevelLine(levelLine);
//        lineChart.getLegend().setDirection(IComponent.BOTTOM);
//        LegendPoint legendPoint = (LegendPoint) lineChart.getLegend().getPoint();
//        PointStyle style = legendPoint.getPointStyle();
//        style.setShape(PointStyle.RECT);
//        lineChart.getLegend().setPercent(0.2f);
//        lineChart.getHorizontalAxis().setRotateAngle(-45);
//        lineChart.setFirstAnim(false);
//        lineChart.setChartData(chartData2);
//        lineChart.startChartAnim(1000);
//        lineChart.setOnClickColumnListener(new OnClickColumnListener<LineData>() {
//            @Override
//            public void onClickColumn(LineData lineData, int position) {
//                //  Toast.makeText(LineChartActivity.this,lineData.getChartYDataList().get(position)+lineData.getUnit(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}
