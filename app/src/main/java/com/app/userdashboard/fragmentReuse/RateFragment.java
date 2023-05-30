package com.app.userdashboard.fragmentReuse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.R;
import com.app.model.support.MonthStatistical;
import com.app.service.ApiClient;
import com.app.userdashboard.ChartCustom;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateFragment extends Fragment {
    LineChart lineChartCount, lineChartAvg;
    List<Integer> listYear;
    Spinner spinnerCount, spinnerAvg;

    boolean isFirstItemSelectedCount, isFirstItemSelectedAvg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listYear = Arrays.asList(2023, 2022);
        View view = inflater.inflate(R.layout.fragment_rate, container, false);

        lineChartCount = view.findViewById(R.id.line_chart_count);
        lineChartAvg = view.findViewById(R.id.line_chart_avg);

        spinnerCount = view.findViewById(R.id.spinner_count);
        spinnerAvg = view.findViewById(R.id.spinner_avg);

        ArrayAdapter adapter = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, listYear);

        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerCount.setAdapter(adapter);
        spinnerCount.setOnItemSelectedListener(onItemSelectedListenerCount());

        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerAvg.setAdapter(adapter);
        spinnerAvg.setOnItemSelectedListener(onItemSelectedListenerAvg());

        callApiToGenerateLineChartCount();
        callApiToGenerateLineChartAvg();

        return view;
    }

    private void callApiToGenerateLineChartCount() {
        Call<JsonArray> call = ApiClient.getApiService().getCountRatingInMonthOfYear(2023, 1);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<MonthStatistical> monthStatisticals = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    Log.e("z", jsonObject.toString());
                    monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("countRating").getAsInt()));
                }
                ChartCustom.setUpLineChart(lineChartCount, monthStatisticals);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

    }

    private void callApiToGenerateLineChartAvg() {
        Call<JsonArray> call = ApiClient.getApiService().getAvgRatingInMonthOfYear(2023, 1);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();

                List<MonthStatistical> monthStatisticals = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("avgRating").getAsInt()));
                }
                ChartCustom.setUpLineChart(lineChartAvg, monthStatisticals);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

    }

    public AdapterView.OnItemSelectedListener onItemSelectedListenerAvg() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (isFirstItemSelectedAvg) {
                    isFirstItemSelectedAvg = false;
                    return;
                }
                Toast.makeText(getActivity().getApplication(), "Biểu đồ thống kê năm: " + listYear.get(arg2), Toast.LENGTH_SHORT).show();
                Call<JsonArray> call = ApiClient.getApiService().getAvgRatingInMonthOfYear(listYear.get(arg2), 1);
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        JsonArray jsonArray = response.body();

                        List<MonthStatistical> monthStatisticals = new ArrayList<>();
                        for (JsonElement element : jsonArray) {
                            JsonObject jsonObject = element.getAsJsonObject();
                            monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("avgRating").getAsInt()));
                        }
                        ChartCustom.updateLineChart(lineChartAvg, monthStatisticals);
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

    public AdapterView.OnItemSelectedListener onItemSelectedListenerCount() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (isFirstItemSelectedCount) {
                    isFirstItemSelectedCount = false;
                    return;
                }
                Toast.makeText(getActivity().getApplication(), "Biểu đồ thống kê năm: " + listYear.get(arg2), Toast.LENGTH_SHORT).show();
                Call<JsonArray> call = ApiClient.getApiService().getCountRatingInMonthOfYear(listYear.get(arg2), 1);
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        JsonArray jsonArray = response.body();

                        List<MonthStatistical> monthStatisticals = new ArrayList<>();
                        for (JsonElement element : jsonArray) {
                            JsonObject jsonObject = element.getAsJsonObject();
                            monthStatisticals.add(new MonthStatistical(jsonObject.get("month").getAsInt(), jsonObject.get("countRating").getAsInt()));
                        }
                        ChartCustom.updateLineChart(lineChartCount, monthStatisticals);
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
