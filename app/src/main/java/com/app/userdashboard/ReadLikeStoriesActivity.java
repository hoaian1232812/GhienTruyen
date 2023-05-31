package com.app.userdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.adapter.StoryGridAdapter;
import com.app.adapter.StoryTopicAdapter;
import com.app.model.Story;
import com.app.model.User;
import com.app.service.ApiClient;
import com.app.user.TrendingStoryHomeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadLikeStoriesActivity extends AppCompatActivity {
    StoryTopicAdapter storyAdapter;
    RecyclerView recyclerViewReadStories;

    boolean isLoading = false, emptyData = false;

    ProgressBar pb_loading;
    Bundle bundle;

    int limit = 15;

    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stories);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pb_loading = findViewById(R.id.pb_loading_read_user);
        bundle = getIntent().getBundleExtra("data");
        setUpReadStories();
    }

    public void setUpReadStories() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReadStories = findViewById(R.id.recycleViewReadStories);
        recyclerViewReadStories.setLayoutManager(linearLayoutManager);
        setTitle(bundle.getString("title"));
        List<Story> list = new Gson().fromJson((String) bundle.getString("listData"), new TypeToken<List<Story>>() {
        }.getType());
        storyAdapter = new StoryTopicAdapter(list);
        recyclerViewReadStories.setAdapter(storyAdapter);
        lazyLoading();
    }


    public void lazyLoading() {
        recyclerViewReadStories.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        storyAdapter.addNewData(response.body());
//                        Toast.makeText(ReadLikeStoriesActivity.this, "" + page, Toast.LENGTH_SHORT).show();
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