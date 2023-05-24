package com.app.user;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.model.Story;
import com.app.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    StoryAdapter storyAdapter;
    List<Story> storyListNewStory, storyListNewUpdate;
    RecyclerView recyclerViewNewStory, recyclerViewRead;

    RecyclerView recyclerViewNewUpdate;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        setUpNewStory();
        setUpNewUpdate();
        setUpRead();

        return root;
    }

    public void setUpNewStory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewStory = root.findViewById(R.id.recyleViewNewStory);
        recyclerViewNewStory.setLayoutManager(linearLayoutManager);
        storyListNewStory = new ArrayList<>();
        storyAdapter = new StoryAdapter(storyListNewStory);
        recyclerViewNewStory.setAdapter(storyAdapter);
    }

    public void setUpRead() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRead = root.findViewById(R.id.recyleViewRead);
        recyclerViewRead.setLayoutManager(linearLayoutManager);
        storyListNewStory = new ArrayList<>();
        storyAdapter = new StoryAdapter(storyListNewStory);
        recyclerViewRead.setAdapter(storyAdapter);
    }

    public void setUpNewUpdate() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewUpdate = root.findViewById(R.id.recyleViewNewUpdate);
        recyclerViewNewUpdate.setLayoutManager(linearLayoutManager);
        Call<List<Story>> call = ApiClient.getApiService().getAllStoryNewUpdate();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    storyAdapter = new StoryAdapter(response.body());
                    recyclerViewNewUpdate.setAdapter(storyAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {

            }
        });

    }
}