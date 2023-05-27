package com.app.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.google.gson.Gson;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRead.setLayoutManager(linearLayoutManager);
        storyListRead = new ArrayList<>();
        String deviceId = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String uniqueName = "user_preferences_" + deviceId;
        SharedPreferences userPreferences = root.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
        Map<String, ?> map = userPreferences.getAll();
        Gson gson = new Gson();
        for (String s : map.keySet()) {
            Object value = map.get(s);
            if (value instanceof String && s.startsWith("story_") && s.endsWith("_read")) {
                storyListRead.add(gson.fromJson((String) value, Story.class));
            }
        }
        storyAdapter = new StoryAdapter(storyListRead);
        recyclerViewRead.setAdapter(storyAdapter);
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
