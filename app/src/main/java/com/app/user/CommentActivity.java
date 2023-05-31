package com.app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.CommentAdapter;
import com.app.model.Comment;
import com.app.model.Story;
import com.app.model.User;
import com.app.service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    CommentAdapter commentAdapter;
    RecyclerView recyclerView;
    Bundle bundle;
    boolean isLoading = false, empty = false;
    TextView totle;
    ProgressBar pb_loading;
    private int limit = 15;
    private int page = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pb_loading = findViewById(R.id.pb_loading_comment);
        bundle = getIntent().getBundleExtra("data");
        setTitle(bundle.getString("title"));
        int rate = (int) Math.round(bundle.getDouble("rate"));
        totle = findViewById(R.id.start_totle);
        totle.setText("" + rate);
        User user = User.getUserFromSharedPreferences(this);
        if (user != null) {
            TextView review = findViewById(R.id.review);
            review.setOnClickListener(view -> {
                Intent intent = new Intent(this, ReviewActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("idUser", user.getId());
                bundle1.putInt("idStory", bundle.getInt("idStory"));
                intent.putExtra("data", bundle1);
                startActivity(intent);
            });
        }
        LinearLayout linearLayout = findViewById(R.id.star);
        for (int i = 0; i < 5; i++) {
            ImageView img = new ImageView(this);
            if (i < rate) {
                img.setImageDrawable(getDrawable(R.drawable.baseline_star_24));
            } else {
                img.setImageDrawable(getDrawable(R.drawable.outline_star_rate_24_blue));
            }
            linearLayout.addView(img);
        }
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recycle_comment);
        recyclerView.setLayoutManager(linearLayoutManager);
        Call<List<Comment>> call = ApiClient.getApiService().getAllCommentByStoryOnPage(bundle.getInt("idStory"), limit, page);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    commentAdapter = new CommentAdapter(response.body());
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
        lazyLoading();
    }

    public void lazyLoading() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Kiểm tra xem RecyclerView đã cuộn đến cuối danh sách và không có tác vụ tải dữ liệu đang chạy
                if (!recyclerView.canScrollVertically(1) && !isLoading && !empty) {
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    page += 1;
                    prepareData();
                }
            }
        });
    }

    private void prepareData() {
        if (empty) {
            isLoading = false;
            pb_loading.setVisibility(View.GONE);
            return;
        }
        pb_loading.setVisibility(View.VISIBLE);
        Call<List<Comment>> call = ApiClient.getApiService().getAllCommentByStoryOnPage(bundle.getInt("idStory"), limit, page);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        empty = true;
                        page -= 1;
                    } else {
                        commentAdapter.addNewData(response.body());
                        Toast.makeText(CommentActivity.this, "" + page, Toast.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                } else {
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                page -= 1;
                isLoading = false;
                empty = false;
                pb_loading.setVisibility(View.GONE);
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