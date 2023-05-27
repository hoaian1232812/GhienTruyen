package com.app.userdashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.adapter.StoryTopicAdapter;
import com.app.model.Story;
import com.app.model.support.MonthStatistical;
import com.app.service.ApiClient;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeFragment extends Fragment {
    PieChart pieChart;
    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        lineChart = view.findViewById(R.id.line_chart);
        pieChart = view.findViewById(R.id.pie_chart);

        callApiToGenerateLineChart();
        callApiToGeneratePieChart();

        return view;
    }


    public void callApiToGeneratePieChart() {
        Call<JsonArray> call = ApiClient.getApiService().getStatisticalLikeOfYear(1);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<PieEntry> entries = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    entries.add(new PieEntry(jsonObject.get("likes").getAsInt(), Integer.toString(jsonObject.get("year").getAsInt())));
                }
                ChartCustome.setUpPieChart(pieChart, entries);
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    public void callApiToGenerateLineChart() {
        Call<List<MonthStatistical>> call = ApiClient.getApiService().getMonthStatisticalLikeInYear(2023, 1);
        call.enqueue(new Callback<List<MonthStatistical>>() {
            @Override
            public void onResponse(Call<List<MonthStatistical>> call, Response<List<MonthStatistical>> response) {
                List<MonthStatistical> monthStatisticals = response.body();
                ChartCustome.setUpLineChart(lineChart, monthStatisticals);
            }
            @Override
            public void onFailure(Call<List<MonthStatistical>> call, Throwable t) {

            }
        });
    }


}