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
<<<<<<< HEAD

    private void login(String email, View view) {
        Call<User> call = ApiClient.getApiService().login(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String passNoHash = inputPasswordLogin.getText().toString();

                if (response.isSuccessful()) {
                    user = response.body();
                    Log.i("z", user.toString());
                    if (user.getId() == -1) {
                        error.setText("Sai email hoặc mật khẩu...");
                        return;
                    }
                    boolean checkpw = BCrypt.checkpw(passNoHash, user.getPassword());
//                    boolean checkpw = passNoHash.equals(user.getPassword());
                    Log.e("z", BCrypt.hashpw(passNoHash, BCrypt.gensalt()));
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


=======
>>>>>>> parent of 215e6d2 (dang nhap)
}