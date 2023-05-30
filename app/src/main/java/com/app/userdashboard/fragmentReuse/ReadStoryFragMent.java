package com.app.userdashboard.fragmentReuse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.R;
import com.app.model.Story;
import com.app.model.support.MonthStatistical;
import com.app.service.ApiClient;
import com.app.userdashboard.ChartCustom;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadStoryFragMent extends Fragment {

    PieChart pieChart;
    Story story;
    LineChart lineChart;
    Spinner spinner;
    List<Integer> listYear;
    boolean isFirstItemSelected = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listYear = Arrays.asList(2023, 2022);

        story = (Story) getArguments().getSerializable("story");

        View view = inflater.inflate(R.layout.fragment_statiscal, container, false);

        lineChart = view.findViewById(R.id.line_chart);
        pieChart = view.findViewById(R.id.pie_chart);
        spinner = view.findViewById(R.id.spinner_year);

        ArrayAdapter adapter = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, listYear);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener());

        callApiToGeneratePieChart();
        callApiToGenerateLineChart();

        return view;
    }


    public void callApiToGenerateLineChart() {
        Call<JsonArray> call = ApiClient.getApiService().getViewInMonthOfYearOfStory(2023, story.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<MonthStatistical> monthStatisticals = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("views").getAsInt()));
                }
                ChartCustom.setUpLineChart(lineChart, monthStatisticals);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    public void callApiToGeneratePieChart() {
        Call<JsonArray> call = ApiClient.getApiService().getViewOfYearOfStory(story.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<PieEntry> entries = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    entries.add(new PieEntry(jsonObject.get("views").getAsInt(), Integer.toString(jsonObject.get("year").getAsInt())));
                }
                ChartCustom.setUpPieChart(pieChart, entries);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    public AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (isFirstItemSelected) {
                    isFirstItemSelected = false;
                    return;
                }
                Toast.makeText(getActivity().getApplication(), "Biểu đồ thống kê năm: " + listYear.get(arg2), Toast.LENGTH_LONG).show();
                Call<JsonArray> call = ApiClient.getApiService().getViewInMonthOfYearOfStory(listYear.get(arg2), story.getId());
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        JsonArray jsonArray = response.body();

                        List<MonthStatistical> monthStatisticals = new ArrayList<>();
                        for (JsonElement element : jsonArray) {
                            JsonObject jsonObject = element.getAsJsonObject();
                            monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("views").getAsInt()));
                        }
                        ChartCustom.updateLineChart(lineChart, monthStatisticals);
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        };
    }
}