package com.app.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.R;
import com.app.adapter.StoryTopicAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateFragment extends Fragment {
    RecyclerView recyclerView;
    StoryTopicAdapter storyTopicAdapter;
    ProgressBar pb_loading;
    boolean isLoading = false;
    int page = 1;
    int limit = 15;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update, container, false);
        pb_loading = root.findViewById(R.id.pb_loading);
        setRecyclerView(root);
        return root;
    }

    public void setRecyclerView(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = root.findViewById(R.id.recyle_story_topic);
        recyclerView.setLayoutManager(linearLayoutManager);
        int idTopic = getArguments().getInt("idTopic");
        Call<List<Story>> call = ApiClient.getApiService().getNewStoriesByTopicOnPage(idTopic, limit, page);
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
        lazyLoading(idTopic);
    }

    public void lazyLoading(int idTopic) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Kiểm tra xem RecyclerView đã cuộn đến cuối danh sách và không có tác vụ tải dữ liệu đang chạy
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    page += 1;
                    prepareData(idTopic, page, limit);
                }
            }
        });
    }

    private void prepareData(int idTopic, int page, int count) {
        pb_loading.setVisibility(View.VISIBLE);

        Call<List<Story>> call = ApiClient.getApiService().getNewStoriesByTopicOnPage(idTopic, count, page);

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> newDataList = response.body();
                    storyTopicAdapter.addNewData(newDataList);
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

}