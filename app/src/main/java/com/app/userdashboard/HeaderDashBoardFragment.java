package com.app.userdashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.R;
import com.app.model.User;

public class HeaderDashBoardFragment extends Fragment {
    TextView myName;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_header_dash_board, container, false);
        myName = root.findViewById(R.id.my_name);
        user = User.getUserFromSharedPreferences(getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE));
        myName.setText(user.getName());

        return root;
    }


}