package com.app.userdashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.userdashboard.detail.ListStoryDashBoard;

public class MidleDashBoardFragment extends Fragment {
    CardView btnAll, btnStory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_midle_dash_board, container, false);
        btnAll = view.findViewById(R.id.btn_statistical_all);
        btnAll.setOnClickListener(onGoToStatisticalAllButtonClicked());

        btnStory = view.findViewById(R.id.btn_statistical_story);
        btnStory.setOnClickListener(onGoToStatisticalStoryButtonClicked());
        return view;
    }

    public View.OnClickListener onGoToStatisticalAllButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), DetailStatisticalActivity.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener onGoToStatisticalStoryButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), ListStoryDashBoard.class);
            startActivity(intent);
        };
    }
}