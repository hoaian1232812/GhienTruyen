package com.app.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.R;
import com.app.login_register.LoginActivity;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        Button btnGoToLogin = root.findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(onGoToLoginButtonClicked());
        return root;
    }

    public View.OnClickListener onGoToLoginButtonClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        };
    }

}