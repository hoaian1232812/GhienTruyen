package com.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.login_register.LoginActivity;

public class UserFragment extends Fragment {
    RelativeLayout goToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        RelativeLayout btnGoToLogin = root.findViewById(R.id.go_to_login);
        btnGoToLogin.setOnClickListener(onGoToLoginClicked());

        return root;
    }

    private View.OnClickListener onGoToLoginClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        };
    }

}