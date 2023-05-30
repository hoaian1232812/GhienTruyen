package com.app.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.MainActivity;
import com.app.R;
import com.app.model.User;
import com.app.service.ApiClient;
import com.app.user.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    User user;
    TextView btnRegister, btnForgotPass, error;
    Button btnLogin;
    TextInputEditText inputEmailLogin, inputPasswordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.go_to_register);
        btnRegister.setOnClickListener(onGoToRegisterClicked());

        btnForgotPass = findViewById(R.id.go_to_forgot_pass);
        btnForgotPass.setOnClickListener(onGoToForgotPassClicked());

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(onLoginClicked());

        inputEmailLogin = findViewById(R.id.input_email_login);
        inputPasswordLogin = findViewById(R.id.input_pass_login);

        error = findViewById(R.id.error);

    }

    private View.OnClickListener onLoginClicked() {
        return view -> {
            String email = inputEmailLogin.getText().toString();
            String pass = inputPasswordLogin.getText().toString();
            if (email.isBlank()) {
                error.setText("null email");
                return;
            } else if (pass.isBlank()) {
                error.setText("null password");
                return;
            }
            login(email, view);
        };
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

    private void login(String email, View view) {
        Call<User> call = ApiClient.getApiService().login(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String passNoHash = inputPasswordLogin.getText().toString();

                if (response.isSuccessful()) {
                    user = response.body();
                    if (user.getId() == -1) {
                        error.setText("Sai email hoặc mật khẩu...");
                        return;
                    }
                    boolean checkpw = BCrypt.checkpw(passNoHash, user.getPassword());
                    if (!checkpw) {
                        error.setText("Sai email hoặc mật khẩu");
                        return;
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    user.putToSharedPreferences(sharedPreferences);
                    startActivity(new Intent(view.getContext(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}