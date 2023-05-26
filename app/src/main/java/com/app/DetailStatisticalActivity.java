package com.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.adapter.MenuPagerAdapter;
import com.app.adapter.MenuStatisticalAdapter;
import com.app.model.Topic;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailStatisticalActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 vPager;
    MenuStatisticalAdapter menuPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Thống kê chung");
        setUpViewPager();
    }

    public void setUpViewPager() {
        tabLayout = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.viewPager);
        menuPagerAdapter = new MenuStatisticalAdapter(this);
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