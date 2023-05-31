package com.app.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.R;
import com.app.model.Chapter;

public class ChapterDetailActivity extends AppCompatActivity {
    Chapter chapter;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chapter = (Chapter) getIntent().getBundleExtra("data").getSerializable("chapter");
        setTitle(chapter.getName());
        setContentView(R.layout.activity_chapter_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        content = findViewById(R.id.content);
        content.setText(chapter.getContent());
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
