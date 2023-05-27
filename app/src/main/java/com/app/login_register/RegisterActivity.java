package com.app.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView btnLogin  = findViewById(R.id.go_to_login);
        btnLogin.setOnClickListener(onGoToLoginClicked());
    }

    private View.OnClickListener onGoToLoginClicked() {
        return view -> {
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        };
    }
}