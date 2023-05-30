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
import com.app.adapter.CommentAdapter;
import com.app.model.Comment;
import com.app.model.Story;
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
    boolean isLoading = false;
    ProgressBar pb_loading;
    private int limit = 15;
    private int page = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        bundle = getIntent().getBundleExtra("data");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recycle_comment);
        recyclerView.setLayoutManager(linearLayoutManager);
        Call<List<Comment>> call = ApiClient.getApiService().getAllCommentByStoryOnPage(bundle.getInt("idStory"), limit, page);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
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
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    page += 1;
                    Toast.makeText(CommentActivity.this, "" + page, Toast.LENGTH_SHORT).show();
                    prepareData(page, limit);
                }
            }
        });
    }

    private void prepareData(int page, int limit) {
        pb_loading.setVisibility(View.VISIBLE);
        Call<List<Comment>> call =  ApiClient.getApiService().getAllCommentByStoryOnPage(bundle.getInt("idStory"), limit, page);;

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    commentAdapter.addNewData(response.body());
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                } else {
                    isLoading = false;
                    pb_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
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