package com.app.userdashboard.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.R;
import com.app.adapter.StoryTopicAdapter;

public class ListStoryDashBoard extends AppCompatActivity {
    RecyclerView recyclerView;
    StoryTopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_story_dash_board);
        recyclerView = findViewById(R.id.recyle_list_story_dash_board);
    }
}