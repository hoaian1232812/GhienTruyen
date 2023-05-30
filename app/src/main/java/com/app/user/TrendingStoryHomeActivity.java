package com.app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryGridAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingStoryHomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    StoryGridAdapter adapter;
    boolean isLoading = false, emptyData = false;
    ProgressBar pb_loading;
    int limit = 21;
    int page = 1;
    Bundle bundle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_like_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getBundleExtra("data");
        setTitle(bundle.getString("title"));
        List<Story> list = new Gson().fromJson((String) bundle.getString("listData"), new TypeToken<List<Story>>() {
        }.getType());
        Log.e("Like", list.size() + "");
        pb_loading = findViewById(R.id.pb_loading_like_home);
        recyclerView = findViewById(R.id.recyle_story_home_like);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new StoryGridAdapter(list);
        recyclerView.setAdapter(adapter);
        lazyLoading();
    }

    public void lazyLoading() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Kiểm tra xem RecyclerView đã cuộn đến cuối danh sách và không có tác vụ tải dữ liệu đang chạy
                if (!recyclerView.canScrollVertically(1) && !isLoading && !emptyData) {
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    page += 1;
                    prepareData();
                }
            }
        });
    }

    private void prepareData() {
        pb_loading.setVisibility(View.VISIBLE);
        Call<List<Story>> call = null;
        switch (bundle.getInt("api")) {
            case 0:
                call = ApiClient.getApiService().getAllStoryAppreciation(limit, page);
                break;
            case 1:
                call = ApiClient.getApiService().getAllStoryLiked(limit, page);
                break;
            case 2:
                call = ApiClient.getApiService().getAllStoryViewed(limit, page);
                break;
        }

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        emptyData = true;
                        page -= 1;
                    } else {
                        adapter.addNewData(response.body());
                        Toast.makeText(TrendingStoryHomeActivity.this, "" + page, Toast.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                } else {
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                page -= 1;
                emptyData = false;
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