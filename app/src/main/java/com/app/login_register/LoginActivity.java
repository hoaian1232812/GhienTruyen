package com.app.login_register;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView btnRegister  = findViewById(R.id.go_to_register);
        btnRegister.setOnClickListener(onGoToRegisterClicked());

        TextView btnForgotPass = findViewById(R.id.go_to_forgot_pass);
        btnForgotPass.setOnClickListener(onGoToForgotPassClicked());
    }

    private View.OnClickListener onGoToRegisterClicked() {
        return view -> {
            startActivity(new Intent(view.getContext(), RegisterActivity.class));
        };
    }
    private View.OnClickListener onGoToForgotPassClicked() {
        return view -> {
            startActivity(new Intent(view.getContext(), ForgotPassActivity.class));
        };
    }
}