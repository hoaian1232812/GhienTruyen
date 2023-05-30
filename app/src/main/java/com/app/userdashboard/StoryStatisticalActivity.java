package com.app.userdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.R;
import com.app.adapter.MenuStatisticalAdapter;
import com.app.adapter.MenuStoryStatisticalAdapter;
import com.app.model.Story;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StoryStatisticalActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 vPager;
    Bundle bundle;
    Story story;
    MenuStoryStatisticalAdapter menuPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_statistical);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getBundleExtra("data");
        story = (Story) bundle.getSerializable("story");

        setTitle(story.getTitle());
        setUpViewPager();
    }

    public void setUpViewPager() {
        tabLayout = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.viewPager);
        menuPagerAdapter = new MenuStoryStatisticalAdapter(this);
        menuPagerAdapter.setBundle(bundle);
        vPager.setAdapter(menuPagerAdapter);

        new TabLayoutMediator(tabLayout, vPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Lượt thích");
                    break;
                case 1:
                    tab.setText("Lượt đọc");
                    break;
                case 2:
                    tab.setText("Lượt đánh giá");
                    break;
            }
        }).attach();
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