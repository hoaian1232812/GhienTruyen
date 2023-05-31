package com.app.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.service.ApiClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nhận Xét");

        LinearLayout linearLayout = findViewById(R.id.review_start);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ImageView img = (ImageView) linearLayout.getChildAt(i);
            img.setOnClickListener(view -> {
                rating = Integer.parseInt((String) img.getTag()) + 1;
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                        imageView.setImageDrawable(getDrawable(R.drawable.baseline_star_24));
                    } else {
                        ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                        imageView.setImageDrawable(getDrawable(R.drawable.outline_star_rate_24_blue));
                    }
                }
            });
        }
        Bundle bundle = getIntent().getBundleExtra("data");
        EditText text = findViewById(R.id.content_review);
        TextView textView = findViewById(R.id.gui);
        textView.setOnClickListener(view -> {
            Call<JsonObject> call = ApiClient.getApiService().review(bundle.getInt("idStory"), bundle.getInt("idUser"), text.getText().toString(), rating);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ReviewActivity.this, response.body().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
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