package com.app.userdashboard.fragmentReuse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.R;
import com.github.mikephil.charting.charts.LineChart;

public class RateFragment extends Fragment {
    LineChart lineChartMonth, lineChartYear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        lineChartMonth = view.findViewById(R.id.line_chart_quantity);
        lineChartYear = view.findViewById(R.id.line_chart_rate);

        callApiToGenerateLineChartYear();
        callApiToGeneratePieChartMonth();

        return view;
    }

    private void callApiToGeneratePieChartMonth() {
    }

    private void callApiToGenerateLineChartYear() {
    }

}
