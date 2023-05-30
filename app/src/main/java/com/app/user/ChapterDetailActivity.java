package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.R;
import com.app.adapter.ChapterPaperAdapter;
import com.app.model.Chapter;
import com.app.model.Story;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ChapterDetailActivity extends AppCompatActivity {
    private boolean isTouchDetected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager_chapter);
        List<Chapter> chapters = new Gson().fromJson(getIntent().getBundleExtra("data").getString("chapters"), new TypeToken<List<Chapter>>() {
        }.getType());
        ChapterPaperAdapter adapter = new ChapterPaperAdapter(this, chapters);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(getIntent().getBundleExtra("data").getInt("position"));
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
