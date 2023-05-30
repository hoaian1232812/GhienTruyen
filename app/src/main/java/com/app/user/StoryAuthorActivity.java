package com.app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryTopicAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryAuthorActivity extends AppCompatActivity {
    LinearLayoutManager layout;
    RecyclerView recyclerView;
    StoryTopicAdapter storyTopicAdapter;
    ProgressBar pb_loading;
    boolean isLoading = false;
    int page = 1;
    int limit = 15;
    Bundle bundle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_author);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pb_loading = findViewById(R.id.pb_loading_author);
        bundle = getIntent().getBundleExtra("data");
        setTitle(bundle.getString("name"));
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recy_author);
        recyclerView.setLayoutManager(layout);
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryAuthor(bundle.getInt("id"), limit, page);
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    storyTopicAdapter = new StoryTopicAdapter(response.body());
                    recyclerView.setAdapter(storyTopicAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {

            }
        });
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
                    Toast.makeText(StoryAuthorActivity.this, "" + page, Toast.LENGTH_SHORT).show();
                    prepareData(page, limit);
                }
            }
        });
    }

    private void prepareData(int page, int limit) {
        pb_loading.setVisibility(View.VISIBLE);
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryAuthor(bundle.getInt("id"), limit, page);
        ;

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    storyTopicAdapter.addNewData(response.body());
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