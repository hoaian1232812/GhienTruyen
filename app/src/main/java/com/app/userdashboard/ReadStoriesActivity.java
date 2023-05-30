package com.app.userdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.model.Story;

import java.util.ArrayList;
import java.util.List;

public class ReadStoriesActivity extends AppCompatActivity {
    StoryAdapter storyAdapter;
    RecyclerView recyclerViewReadStories;
    List<Story> readStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stories);
        setUpReadStories();
    }

    public void setUpReadStories() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReadStories = findViewById(R.id.recycleViewReadStories);
        recyclerViewReadStories.setLayoutManager(linearLayoutManager);
        readStories = new ArrayList<>();
        storyAdapter = new StoryAdapter(readStories);
        recyclerViewReadStories.setAdapter(storyAdapter);
    }
}