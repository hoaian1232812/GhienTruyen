package com.app.userdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.R;
import com.app.adapter.MenuStatisticalAdapter;
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
        String statisticalName = getIntent().getBundleExtra("data").getString("statisticalName");
        int statisticalId = getIntent().getBundleExtra("data").getInt("statisticalId");
        setTitle(statisticalName);
        setUpViewPager(statisticalId);
    }

    public void setUpViewPager(int statisticalId) {
        tabLayout = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.viewPager);
        menuPagerAdapter = new MenuStatisticalAdapter(this);
        Bundle bundle = new Bundle();
        bundle.putInt("statisticalId", statisticalId);
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