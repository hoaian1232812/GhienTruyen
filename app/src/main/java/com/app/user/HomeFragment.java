package com.app.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    StoryAdapter storyAdapter;
    List<Story> storyListNewStory, storyListNewUpdate, storyListRead;
    RecyclerView recyclerViewNewStory, recyclerViewRead;

    RecyclerView recyclerViewNewUpdate;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewNewStory = root.findViewById(R.id.recyleViewNewStory);
        recyclerViewNewUpdate = root.findViewById(R.id.recyleViewNewUpdate);
        recyclerViewRead = root.findViewById(R.id.recyleViewRead);

        setUpNewStory();
        setUpNewUpdate();
        setUpRead();

        return root;
    }

    private void updateAdapters() {
        if (storyListNewUpdate != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewNewUpdate.setLayoutManager(linearLayoutManager);
            storyAdapter = new StoryAdapter(storyListNewUpdate);
            recyclerViewNewUpdate.setAdapter(storyAdapter);
        }
        if (storyListRead != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRead.setLayoutManager(linearLayoutManager);
            storyAdapter = new StoryAdapter(storyListRead);
            recyclerViewRead.setAdapter(storyAdapter);
        }
        if (storyListNewStory != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewNewStory.setLayoutManager(linearLayoutManager);
            storyAdapter = new StoryAdapter(storyListNewStory);
            recyclerViewNewStory.setAdapter(storyAdapter);
        }
    }

    public void setUpNewStory() {
        Call<List<Story>> call = ApiClient.getApiService().getAllStory();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    storyListNewStory = response.body();
                    updateAdapters();
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void setUpRead() {
        String deviceId = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String uniqueName = "user_preferences_" + deviceId;
        SharedPreferences userPreferences = root.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
        CompletableFuture.runAsync(() -> {
            Map<String, ?> map = userPreferences.getAll();
            List<CompletableFuture<Story>> futures = new ArrayList<>();

            for (String s : map.keySet()) {
                if (s.startsWith("story_") && s.endsWith("_read")) {
                    int idStory = (int) map.get(s);
                    CompletableFuture<Story> future = getStoryByIdAsync(idStory);
                    futures.add(future);
                }
            }

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.thenApply(v -> futures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
            ).thenAccept(storyListNewStory -> {
                // Thực hiện các thao tác tiếp theo với storyListNewStory, ví dụ:
                storyAdapter = new StoryAdapter(storyListNewStory);
                recyclerViewRead.setAdapter(storyAdapter);
            });
        });
    }

    private CompletableFuture<Story> getStoryByIdAsync(int idStory) {
        // Gửi yêu cầu API và trả về danh sách câu chuyện cập nhật mới
        // Ví dụ:
        CompletableFuture<Story> future = new CompletableFuture<>();
        Call<Story> call = ApiClient.getApiService().getStoryById(idStory);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    public void setUpNewUpdate() {
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryNewUpdate();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    storyListNewUpdate = response.body();
                    updateAdapters();
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
