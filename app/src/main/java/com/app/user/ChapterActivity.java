package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.R;
import com.app.adapter.ChapterAdapter;
import com.app.model.Story;

import java.util.concurrent.CompletableFuture;

public class ChapterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layout;
    ChapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getBundleExtra("data");
        Story story = (Story) bundle.getSerializable("story");
        recyclerView = findViewById(R.id.chapter_user);
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        story.getAllChapter().thenAccept(chapters -> {
            adapter = new ChapterAdapter(chapters);
            recyclerView.setAdapter(adapter);
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