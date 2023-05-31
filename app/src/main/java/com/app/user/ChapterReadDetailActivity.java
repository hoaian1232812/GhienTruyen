package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.app.R;
import com.app.adapter.ChapterPaperAdapter;
import com.app.model.Chapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ChapterReadDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_chapter_detail_read);
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            // Ẩn lại thanh trạng thái (status bar) và thanh điều hướng (navigation bar)
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
