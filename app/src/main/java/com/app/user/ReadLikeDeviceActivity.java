package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.adapter.StoryTopicAdapter;
import com.app.model.Story;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ReadLikeDeviceActivity extends AppCompatActivity {
    StoryTopicAdapter storyAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layout;
    Bundle bundle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_like_device);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getBundleExtra("data");
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.device_recy);
        recyclerView.setLayoutManager(layout);
        setTitle(bundle.getString("title"));
        List<Story> list = new Gson().fromJson((String) bundle.getString("listData"), new TypeToken<List<Story>>() {
        }.getType());
        storyAdapter = new StoryTopicAdapter(list);
        recyclerView.setAdapter(storyAdapter);
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