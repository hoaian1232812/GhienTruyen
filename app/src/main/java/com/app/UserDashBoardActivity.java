package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserDashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_dash_board);
    }
}