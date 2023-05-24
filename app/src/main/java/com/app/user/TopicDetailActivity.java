package com.app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.app.R;
import com.app.adapter.MenuPagerAdapter;
import com.app.model.Topic;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TopicDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 vPager;
    MenuPagerAdapter menuPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Topic topic = (Topic) getIntent().getBundleExtra("data").getSerializable("topic");
        setTitle(topic.getName());
        setUpViewPagger(topic.getId());
    }

    public void setUpViewPagger(int idTopic) {
        tabLayout = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.viewPager);
        menuPagerAdapter = new MenuPagerAdapter(this);
        Bundle bundle = new Bundle();
        bundle.putInt("idTopic", idTopic);
        menuPagerAdapter.setBundle(bundle);
        vPager.setAdapter(menuPagerAdapter);

        new TabLayoutMediator(tabLayout, vPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Mới Cập Nhật");
                    break;
                case 1:
                    tab.setText("Hoàn Thành");
                    break;
                case 2:
                    tab.setText("Xem Nhiều");
                    break;
                case 3:
                    tab.setText("Yêu Thích");
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