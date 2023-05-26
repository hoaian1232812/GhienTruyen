package com.app.userdashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.DetailStatisticalActivity;
import com.app.R;
import com.app.login_register.RegisterActivity;

public class ListFeatureDashBoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_feature, container, false);
        CardView btn_go_to_read_stories = view.findViewById(R.id.go_to_list_read_story);
        btn_go_to_read_stories.setOnClickListener(onGoToReadStoriesClicked());
        return view;
    }

    private View.OnClickListener onGoToReadStoriesClicked() {
        return view -> {
            startActivity(new Intent(getActivity(), ReadStoriesActivity.class));
        };
    }

}