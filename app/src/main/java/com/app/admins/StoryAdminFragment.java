package com.app.admins;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.service.ApiClient;
import com.app.userdashboard.ChartCustom;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryAdminFragment extends Fragment {
    BarChart barChartTopic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story_admin, container, false);

        barChartTopic = view.findViewById(R.id.bar_chart_topic);

        callApiToCountStoryByTopic();

        return view;
    }

    public void callApiToCountStoryByTopic() {


        Call<JsonArray> call = ApiClient.getApiService().getCountStoryByTopic();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> listTopic = new ArrayList<>();
                int i = 0;
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    entries.add(new BarEntry(i, jsonObject.get("countStory").getAsInt()));
                    listTopic.add(jsonObject.get("topic").getAsString());
                    i++;
                }
                ChartCustom.setUpBarChart(barChartTopic, entries, listTopic);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

    }


}