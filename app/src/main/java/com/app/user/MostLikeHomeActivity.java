package com.app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.app.R;
import com.app.adapter.StoryGridAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostLikeHomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    StoryGridAdapter adapter;
    boolean isLoading = false;
    ProgressBar pb_loading;
    int limit = 21;
    int page = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_like_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pb_loading = findViewById(R.id.pb_loading_like_home);
        recyclerView = findViewById(R.id.recyle_story_home_like);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryLiked(limit, page);
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    adapter = new StoryGridAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {

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
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    page += 1;
                    prepareData(page, limit);
                }
            }
        });
    }

    private void prepareData(int page, int limit) {
        pb_loading.setVisibility(View.VISIBLE);

        Call<List<Story>> call = ApiClient.getApiService().getAllStoryLiked(limit, page);

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    adapter.addNewData(response.body());
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                } else {
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                isLoading = false;
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