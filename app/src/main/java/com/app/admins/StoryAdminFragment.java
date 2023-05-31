package com.app.admins;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.login_register.LoginActivity;
import com.app.model.support.MonthStatistical;
import com.app.service.ApiClient;
import com.app.userdashboard.ChartCustom;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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
    CardView btnListStory, btnListTopic;
    LineChart lineChartChapter;
    PieChart pieChartChapter, pieChartStory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story_admin, container, false);

        barChartTopic = view.findViewById(R.id.bar_chart_topic);

        btnListStory = view.findViewById(R.id.btn_list_story);
        btnListStory.setOnClickListener(onGoToListStory());

        lineChartChapter = view.findViewById(R.id.line_chart_chapter);
        pieChartChapter = view.findViewById(R.id.pie_chart_chapter);
        pieChartStory = view.findViewById(R.id.pie_chart_story);


        callApiToCountStoryByTopic();

        callApiToGenerateCountChapterInMonthOfYear();
        callApiToGetCountChapterOfYear();
        callApiToGetCountStoriesOfYear();


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

    public void callApiToGenerateCountChapterInMonthOfYear() {
        Call<JsonArray> call = ApiClient.getApiService().getCountChapterInMonthOfYear(2023);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<MonthStatistical> monthStatisticals = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("countChapter").getAsInt()));
                }
                ChartCustom.setUpLineChart(lineChartChapter, monthStatisticals);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    public void callApiToGetCountChapterOfYear() {
        Call<JsonArray> call = ApiClient.getApiService().getCountChapterOfYear();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<PieEntry> entries = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    entries.add(new PieEntry(jsonObject.get("countChapter").getAsInt(), Integer.toString(jsonObject.get("year").getAsInt())));
                }
                ChartCustom.setUpPieChart(pieChartStory, entries);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    public void callApiToGetCountStoriesOfYear() {
        Call<JsonArray> call = ApiClient.getApiService().getCountStoryOfYear();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<PieEntry> entries = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    entries.add(new PieEntry(jsonObject.get("countStory").getAsInt(), Integer.toString(jsonObject.get("year").getAsInt())));
                }
                ChartCustom.setUpPieChart(pieChartChapter, entries);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    private View.OnClickListener onGoToListStory() {
        return view -> {
            Intent intent = new Intent(getActivity(), ListStoriesAdminActivity.class);
            startActivity(intent);
        };
    }


}