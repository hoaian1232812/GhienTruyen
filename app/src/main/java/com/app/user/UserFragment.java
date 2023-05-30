package com.app.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.R;
import com.app.UserDashBoardActivity;
import com.app.login_register.LoginActivity;
import com.app.model.User;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        Button btnGoToLogin = root.findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(onGoToLoginButtonClicked());

        Button btnGoToUserDashBoard = root.findViewById(R.id.btnGoToUserDashBoard);
        btnGoToUserDashBoard.setOnClickListener(onGoToUserDashBoardButtonClicked());


        return root;
    }

    public View.OnClickListener onGoToLoginButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener onGoToUserDashBoardButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), UserDashBoardActivity.class);
            startActivity(intent);
        };
    }

}