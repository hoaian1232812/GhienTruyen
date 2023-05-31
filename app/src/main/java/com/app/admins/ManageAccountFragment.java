package com.app.admins;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.model.support.MonthStatistical;
import com.app.service.ApiClient;
import com.app.userdashboard.ChartCustom;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManageAccountFragment extends Fragment {
    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        lineChart = view.findViewById(R.id.line_chart);
        callApiToGenerate();
        return view;
    }

    public void callApiToGenerate() {
        Call<JsonArray> call = ApiClient.getApiService().getCountUserByMonthOfYear(2023);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<MonthStatistical> monthStatisticals = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("countUser").getAsInt()));
                }
                ChartCustom.setUpLineChart(lineChart, monthStatisticals);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }
}