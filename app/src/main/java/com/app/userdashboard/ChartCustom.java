package com.app.userdashboard;

import android.graphics.Color;

import com.app.model.support.MonthStatistical;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartCustom {


    public static void updateLineChart(LineChart lineChart, List<MonthStatistical> monthStatisticalList) {
        ArrayList<Entry> newEntries = new ArrayList<>();
        for (MonthStatistical monthStatistical : monthStatisticalList) {
            newEntries.add(new Entry(monthStatistical.getMonth() - 1, monthStatistical.getCount()));
        }
        LineData lineData = lineChart.getLineData();
        List<ILineDataSet> dataSets = lineData.getDataSets();
        if (dataSets.size() > 0) {
            LineDataSet dataSet = (LineDataSet) dataSets.get(0);
            dataSet.setValues(newEntries);
        }
        lineChart.animateY(800);
        lineChart.invalidate();
    }

    public static void setUpLineChart(LineChart lineChart, List<MonthStatistical> monthStatisticalList) {
        List<String> months = new ArrayList<>();

        ArrayList<Entry> entries = new ArrayList<>();
        for (MonthStatistical monthStatistical : monthStatisticalList) {
            entries.add(new Entry(monthStatistical.getMonth() - 1, monthStatistical.getCount()));
            months.add("Tháng " + (monthStatistical.getMonth()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleRadius(5f);
        dataSet.setCircleHoleRadius(3f);
        dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setLineWidth(2);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(80);
        dataSet.setFillColor(Color.rgb(140, 234, 255));

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // Thiết lập một số thuộc tính cho dãy dữ liệu
        dataSet.setColor(Color.rgb(140, 234, 255));
        dataSet.setValueTextColor(Color.rgb(252, 177, 3));
        dataSet.setValueTextSize(13);

        // Tạo một đối tượng LineData từ dãy dữ liệu
        LineData lineData = new LineData(dataSet);

        // Thiết lập dữ liệu cho biểu đồ
        lineChart.setData(lineData);

        // Thiết lập các thuộc tính cho trục x và trục y
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setAxisMinimum(0);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        lineChart.invalidate();
        lineChart.animateY(800);
    }

    public static void setUpPieChart(PieChart pieChart, List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(13);
        dataSet.setFormSize(10);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setCenterText("Biểu đồ các năm");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.getLegend().setEnabled(false);


        pieChart.invalidate();
        pieChart.animate();
    }
}
