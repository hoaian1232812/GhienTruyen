package com.app.userdashboard.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryListBoardAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStoryDashBoard extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    StoryListBoardAdapter adapter;
    RecyclerView recyclerView;
    Bundle bundle;
    boolean isLoading = false;
    ProgressBar pb_loading;
    private int limit = 15;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_story_dash_board);
        setTitle("Truyện của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyle_list_story_dash_board);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<List<Story>> call = ApiClient.getApiService().getAllStoryOfAuthor(1, limit, page);

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        adapter = new StoryListBoardAdapter(response.body());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {

            }
        });
        lazyLoading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_add:
                //code xử lý khi bấm menu1
                break;
            case R.id.menu_edit:
                //code xử lý khi bấm menu2
                break;
            case R.id.menu_remove:
                //code xử lý khi bấm menu3
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
                    Toast.makeText(getApplicationContext(), "Đã load", Toast.LENGTH_SHORT).show();
                    prepareData(page, limit);
                }
            }
        });
    }

    private void prepareData(int page, int limit) {
        pb_loading.setVisibility(View.VISIBLE);
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryOfAuthor(1, limit, page);

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