package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;
import com.app.adapter.TopicStoryDetailAdapter;
import com.app.model.Chapter;
import com.app.model.Story;
import com.app.model.TimeStory;
import com.app.model.User;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StoryDetail extends AppCompatActivity {
    Story story;
    TextView title, author, status, like, chapter, view, time, intro;
    ImageView img;
    RecyclerView recyclerView;
    TopicStoryDetailAdapter adapter;
    LinearLayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        story = (Story) getIntent().getBundleExtra("data").get("story");
        setUpView();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.topic);
        recyclerView.setLayoutManager(layout);
        adapter = new TopicStoryDetailAdapter(story.getTopics());
        recyclerView.setAdapter(adapter);
    }

    private void setUpView() {
        title = findViewById(R.id.title);
        title.setText(story.getTitle());
        author = findViewById(R.id.author);
        CompletableFuture<User> futureName = story.getNameAuthor();
        futureName.thenAccept(user -> {
            author.setText(user.getName());
        }).exceptionally(e -> {
            return null;
        });
        time = findViewById(R.id.time);
        CompletableFuture<TimeStory> futureTime = story.getTime();
        futureTime.thenAccept(timeStory -> {
            time.setText(timeStory.showDateTime());
        }).exceptionally(e -> {
            return null;
        });
        chapter = findViewById(R.id.chapter);
        CompletableFuture<List<Chapter>> future = story.getAllChapter();
        future.thenAccept(chapters -> {
            chapter.setText(chapters.size() + " Chapter");
        }).exceptionally(e -> {
            return null;
        });
        status = findViewById(R.id.status);
        status.setText(story.getStatus());
        like = findViewById(R.id.like);
        like.setText("" + story.getLikes());
        view = findViewById(R.id.eye);
        view.setText("" + story.getViews());
        img = findViewById(R.id.img_story);
        Glide.with(img.getContext())
                .load("http://139.180.129.238:8080/Untitled1.jpg")
                .into(img);
        intro = findViewById(R.id.introduce);
        intro.setText(story.getIntroduce());
    }
    public void setClickAuthor(){
        author.setOnClickListener(view ->{
            Intent intent = new Intent();
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