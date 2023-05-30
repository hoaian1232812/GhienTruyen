package com.app.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MainActivity;
import com.app.R;
import com.app.model.User;
import com.app.service.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    boolean isNull, correctPassword;
    boolean checkExistEmail;
    Button btnRegister;
    User user;

    TextView btnLogin;
    TextInputEditText inputEmail, inputName, inputPassword, inputRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnLogin = findViewById(R.id.go_to_login);
        btnLogin.setOnClickListener(onGoToLoginClicked());

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(onGoToRegisterBtnClicked());

        inputEmail = findViewById(R.id.input_email_register);
        inputName = findViewById(R.id.input_name_register);
        inputPassword = findViewById(R.id.input_pass_register);
        inputRetypePassword = findViewById(R.id.input_pass_retype_register);
    }

    private View.OnClickListener onGoToRegisterBtnClicked() {
        return view -> {

            checkNull();
            if (isNull) return;

            checkCorrectPassword();
            if (!correctPassword) return;

            checkExistEmail(inputEmail.getText().toString());
            if (checkExistEmail) {
                Toast.makeText(this, "Email này đã được sử dụng", Toast.LENGTH_SHORT).show();
                return;
            }
            checkExistEmail = false;
            String p = BCrypt.hashpw(inputPassword.getText().toString(), BCrypt.gensalt());
            register(inputEmail.getText().toString(), inputName.getText().toString(), p);
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

        };

    }

    private void checkNull() {
        if (inputEmail.getText().toString().isBlank()) {
            isNull = true;
            Toast.makeText(this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputName.getText().toString().isBlank()) {
            isNull = true;
            Toast.makeText(this, "Bạn chưa nhập họ tên", Toast.LENGTH_SHORT).show();

            return;
        } else if (inputPassword.getText().toString().isBlank()) {
            isNull = true;
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();

            return;
        } else if (inputRetypePassword.getText().toString().isBlank()) {
            isNull = true;
            Toast.makeText(this, "Bạn chưa nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        isNull = false;
    }

    private void checkCorrectPassword() {
        if (!inputPassword.getText().toString().equals(inputRetypePassword.getText().toString())) {
            correctPassword = false;
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        correctPassword = true;
    }

    private View.OnClickListener onGoToLoginClicked() {
        return view -> {
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        };
    }

    public void checkExistEmail(String email) {
        Call<User> call = ApiClient.getApiService().checkExistEmail(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Log.i("z", user.toString());
                    if (user.getId() == -1) {
                        checkExistEmail = false;
                        return;
                    }
                    checkExistEmail = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public void register(String email, String name, String password) {
        Call<String> call = ApiClient.getApiService().register(email, name, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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