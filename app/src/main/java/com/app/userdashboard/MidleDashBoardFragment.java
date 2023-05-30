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

public class MidleDashBoardFragment extends Fragment {
    CardView btn_all;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_midle_dash_board, container, false);
        btn_all = view.findViewById(R.id.btn_statistical_all);
        btn_all.setOnClickListener(onGoToStatisticalButtonClicked());
        return view;
    }

    public View.OnClickListener onGoToStatisticalButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), DetailStatisticalActivity.class);
            startActivity(intent);
        };
    }
}