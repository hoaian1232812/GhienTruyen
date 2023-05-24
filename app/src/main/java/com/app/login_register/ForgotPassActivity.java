package com.app.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.R;

public class ForgotPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        TextView btnLogin  = findViewById(R.id.go_to_login);
        btnLogin.setOnClickListener(onGoToRegisterClicked());
    }

    private View.OnClickListener onGoToRegisterClicked() {
        return view -> {
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        };
    }
}