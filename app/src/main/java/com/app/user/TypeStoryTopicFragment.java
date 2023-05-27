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

public class TypeStoryTopicFragment extends Fragment {
    RecyclerView recyclerView;
    StoryTopicAdapter storyTopicAdapter;
    Bundle bundle;
    Call<List<Story>> call;
    ProgressBar pb_loading;
    boolean isLoading = false;
    int page = 1;
    int limit = 15;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full, container, false);
        bundle = getArguments();
        pb_loading = root.findViewById(R.id.pb_loading_full);
        setRecyclerView(root);
        return root;
    }

    public void setCallApi() {
        int id = bundle.getInt("idTopic");
        switch (bundle.getInt("type")) {
            case 0:
                call = ApiClient.getApiService().getNewStoriesByTopicOnPage(id, limit, page);
                break;
            case 1:
                call = ApiClient.getApiService().getStoriesCompletedByTopicOnPage(id, limit, page);
                break;
            case 2:
                call = ApiClient.getApiService().getStoriesMostViewedByTopicOnPage(id, limit, page);
                break;
            case 3:
                call = ApiClient.getApiService().getStoriesLikedByTopicOnPage(id, limit, page);
                break;
        }
    }

    public void setRecyclerView(View root) {
        setCallApi();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = root.findViewById(R.id.recyle_story_topic_full);
        recyclerView.setLayoutManager(linearLayoutManager);
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

    private void prepareData(int page, int count) {
        pb_loading.setVisibility(View.VISIBLE);
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