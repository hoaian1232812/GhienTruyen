package com.app.userdashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        LineChart lineChart = view.findViewById(R.id.line_chart);
        setUpLineChart(lineChart);

        PieChart pieChart = view.findViewById(R.id.pie_chart);
        setUpPieChart(pieChart);

        return view;
    }

    public void setUpPieChart(PieChart pieChart) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f, "2020"));
        entries.add(new PieEntry(30f, "2021"));
        entries.add(new PieEntry(15f, "2022"));
        entries.add(new PieEntry(10f, "2023"));

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

        pieChart.animate();
    }

    public void setUpLineChart(LineChart lineChart) {
        List<String> months = Arrays.asList("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng `12");

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 300));
        entries.add(new Entry(1, 1234));
        entries.add(new Entry(2, 450));
        entries.add(new Entry(3, 456));
        entries.add(new Entry(4, 1324));
        entries.add(new Entry(5, 300));
        entries.add(new Entry(6, 84));
        entries.add(new Entry(7, 84));
        entries.add(new Entry(8, 84));
        entries.add(new Entry(9, 84));
        entries.add(new Entry(10, 84));
        entries.add(new Entry(11, 84));


        LineDataSet dataSet = new LineDataSet(entries, "2023");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
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
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false);

        lineChart.invalidate();
        lineChart.animateY(800);
    }

}