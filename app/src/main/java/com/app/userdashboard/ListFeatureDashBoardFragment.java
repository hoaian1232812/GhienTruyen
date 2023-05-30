package com.app.userdashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.DetailStatisticalActivity;
import com.app.R;
import com.app.login_register.LoginActivity;
import com.app.login_register.RegisterActivity;
import com.app.user.UserFragment;

public class ListFeatureDashBoardFragment extends Fragment {
    CardView btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_feature, container, false);
        CardView btn_go_to_read_stories = view.findViewById(R.id.go_to_list_read_story);
        btn_go_to_read_stories.setOnClickListener(onGoToReadStoriesClicked());

        btnLogout = view.findViewById(R.id.btn_log_out);
        btnLogout.setOnClickListener(logOut());
        return view;
    }

    private View.OnClickListener logOut() {
        return view -> {
            Log.e("z", "logout");
            clearUserFromSharedPreferences();
            startActivity(new Intent(getActivity(), LoginActivity.class));
//            getActivity().finish();
            Toast.makeText(getContext(), "You have been logged out", Toast.LENGTH_SHORT).show();
        };
    }

    private void clearUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private View.OnClickListener onGoToReadStoriesClicked() {
        return view -> {
            startActivity(new Intent(getActivity(), ReadStoriesActivity.class));
        };
    }


}