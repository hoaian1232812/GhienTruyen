package com.app.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.R;
import com.app.model.Topic;

public class TopicDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Topic topic = (Topic) getIntent().getBundleExtra("data").getSerializable("topic");
        setTitle(topic.getName());
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